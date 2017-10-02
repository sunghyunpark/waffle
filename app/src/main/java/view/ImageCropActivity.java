package view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.soundcloud.android.crop.Crop;
import com.yssh.waffle.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import api.ApiClient;
import api.ApiInterface;
import api.response.CommonResponse;
import butterknife.BindString;
import butterknife.ButterKnife;
import database.RealmUtil;
import model.UserModel;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import util.CommonUtil;

/**
 * https://github.com/jdamcd/android-crop 참고
 */
public class ImageCropActivity extends AppCompatActivity {

    @BindString(R.string.network_error_txt) String networkErrorStr;
    private String userProfileBasePath = "img/user_profile/";
    private String userProfileName;
    CommonUtil commonUtil = new CommonUtil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_crop);
        ButterKnife.bind(this);
        Crop.pickImage(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(result.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, result);
        }
    }

    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            /**
             * crop 라이브러리 사용 후 비트맵으로 추출 -> 업로드
             */
            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), Crop.getOutput(result));
                userProfileName = commonUtil.MakeImageName(UserModel.getInstance().getUid());
                PostUserProfileImg("profile", UserModel.getInstance().getUid(), "email", userProfileName+".png", getBitmapUploadImg_Path(bm));
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void PostUserProfileImg(String tag, String uid, String loginMethod, String imgName, String imgPath){
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        final File file = new File(imgPath);
        final File file2 = new File(Environment.getExternalStorageDirectory()+"/WAFFLE/"+"resize_before.png");

        final RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        RequestBody Tag_body = RequestBody.create(MediaType.parse("multipart/form-data"), tag);
        RequestBody Uid_body = RequestBody.create(MediaType.parse("multipart/form-data"), uid);
        RequestBody LoginMethod_body = RequestBody.create(MediaType.parse("multipart/form-data"), loginMethod);
        MultipartBody.Part Image_body = MultipartBody.Part.createFormData("uploaded_file", imgName, requestFile);

        Call<CommonResponse> resultCall = apiService.UploadUserProfile(Tag_body, Uid_body, LoginMethod_body, Image_body);

        resultCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                // Response Success or Fail
                if (response.isSuccessful()) {
                    if(!response.body().isError()){
                        Toast.makeText(getApplicationContext(), "Image Uploaded", Toast.LENGTH_SHORT).show();
                        String profilePath = userProfileBasePath+userProfileName+".png";
                        String profileThumbPath = userProfileBasePath+userProfileName+"thumb.png";
                        RealmUtil realmUtil = new RealmUtil();
                        realmUtil.UpdateUserProfile(getApplicationContext(), UserModel.getInstance().getUid(), profilePath, profileThumbPath);
                        finish();
                        if(file.exists()){
                            file.delete();
                        }
                        if(file2.exists()){
                            file2.delete();
                        }

                    }else{
                        Toast.makeText(getApplicationContext(), response.body().getError_msg(), Toast.LENGTH_SHORT).show();
                    }

                } else {

                }

            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), networkErrorStr,Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    /**
     * 비트맵을 로컬에 저장
     */
    private String getBitmapUploadImg_Path(Bitmap image){
        /**
         * 일단 resize_before비트맵을 로컬에 저장한다
         */
        File folder_path = new File(Environment.getExternalStorageDirectory()+"/WAFFLE/");
        if(!folder_path.exists()){
            folder_path.mkdir();
        }

        String resize_before_path = Environment.getExternalStorageDirectory()+"/WAFFLE/"+"resize_before.png";
        //로컬에 저장
        OutputStream outStream = null;
        File file = new File(resize_before_path);

        try{
            outStream = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.PNG,100,outStream);
            outStream.flush();
            outStream.close();
        }catch(FileNotFoundException e){

        }catch(IOException e){

        }

        /**
         * 저장된 resize_before비트맵의 가로/세로 길이를 가지고 크기에 맞게 리사이징 작업
         */
        //1080px이 최대길이, 가로/세로 중에 더 긴 길이를 기준으로 리사이징
        float size_check = 0;
        if( image.getHeight() >= image.getWidth() ) {
            size_check = image.getHeight();
        } else if( image.getHeight() < image.getWidth() ) {
            size_check = image.getWidth();
        }

        //1080px 최대길이를 넘는 이미지는 리사이징, compress 둘다 함
        if( size_check > 1080){

            String file_name = "resize_after.png";

            BitmapFactory.Options options = new BitmapFactory.Options();

            //리사이징 과정에서 단말기 메모리 오류 방지
            if( size_check > 4320 ){
                options.inSampleSize = 4;
            } else if( size_check > 3240 ){
                options.inSampleSize = 3;
            } else if( size_check > 2160 ){
                options.inSampleSize = 2;
            } else {
                options.inSampleSize = 1;
            }
            Bitmap resized_bitmap = BitmapFactory.decodeFile(resize_before_path, options);

            File fileCacheItem = new File(Environment.getExternalStorageDirectory()+"/WAFFLE/" + file_name);
            OutputStream out = null;

            try {
                float per = 1080/size_check/options.inSampleSize;

                float height=resized_bitmap.getHeight();
                float width=resized_bitmap.getWidth();
                fileCacheItem.createNewFile();
                out = new FileOutputStream(fileCacheItem);
                //resized_bitmap = Bitmap.createScaledBitmap(resized_bitmap, (int)(height*per), (int)(width*per), true);
                resized_bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return Environment.getExternalStorageDirectory()+"/WAFFLE/" + file_name;

        } else {
            try{
                outStream = new FileOutputStream(file);
                image.compress(Bitmap.CompressFormat.PNG,100,outStream);
                outStream.flush();
                outStream.close();
            }catch(FileNotFoundException e){

            }catch(IOException e){

            }
            return resize_before_path;

        }

    }
}
