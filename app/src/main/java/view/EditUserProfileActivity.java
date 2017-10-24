package view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yssh.waffle.AppConfig;
import com.yssh.waffle.R;

import api.ApiClient;
import api.ApiInterface;
import api.response.CommonResponse;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import database.RealmUtil;
import model.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditUserProfileActivity extends AppCompatActivity {
    //os6.0 permission
    private static final int REQUEST_PERMISSIONS_READ_EXTERNAL_STORAGE = 10;
    @BindView(R.id.back_btn) ImageButton backBtn;
    @BindView(R.id.save_btn) Button saveBtn;
    @BindView(R.id.user_profile_img) ImageView user_profile_iv;
    @BindView(R.id.user_email_txt) TextView user_email_tv;
    @BindView(R.id.user_nickname_txt) EditText user_nickName_et;
    @BindView(R.id.user_intro_edit_box) EditText user_intro_et;
    @BindString(R.string.permission_error_txt) String permissionStr;
    @BindString(R.string.network_error_txt) String networkErrorStr;
    RealmUtil realmUtil = new RealmUtil();

    @Override
    public void onResume(){
        super.onResume();
        SetUI();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        ButterKnife.bind(this);

    }

    private void SetUI(){
        //Glide Options
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.mipmap.user_profile_img);
        requestOptions.error(R.mipmap.user_profile_img);
        requestOptions.circleCrop();    //circle

        Glide.with(getApplicationContext())
                .setDefaultRequestOptions(requestOptions)
                .load(AppConfig.ServerAddress+ UserModel.getInstance().getProfile_img())
                .into(user_profile_iv);

        user_email_tv.setText(UserModel.getInstance().getEmail());
        user_nickName_et.setText(UserModel.getInstance().getNick_name());
        user_intro_et.setText(UserModel.getInstance().getIntro());
    }

    /**
     * os 6.0 권한
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSIONS_READ_EXTERNAL_STORAGE:
                //권한이 있는 경우
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(getApplicationContext(), ImageCropActivity.class);
                    startActivity(intent);
                }
                //권한이 없는 경우
                else {
                    Toast.makeText(getApplicationContext(), permissionStr, Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    private void PostUserInfo(final String userNickName, final String userIntro){
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<CommonResponse> call = apiService.EditUserProfileInfo("edit_user_info", UserModel.getInstance().getUid(), userNickName, userIntro);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                CommonResponse loginResponse = response.body();
                if(!loginResponse.isError()){
                    Log.d("PostUserInfo", "Success");
                    realmUtil.UpdateUserEtcInfo(getApplicationContext(), UserModel.getInstance().getUid(), userNickName, userIntro);
                    finish();
                }else{
                    Log.d("PostUserInfo", "Fail");
                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("tag", t.toString());
                Toast.makeText(getApplicationContext(), networkErrorStr,Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * user profile info save
     */
    @OnClick(R.id.save_btn) void saveBtn(){
        String nickNameStr = user_nickName_et.getText().toString();
        String introStr = user_intro_et.getText().toString();

        nickNameStr = nickNameStr.trim();
        introStr = introStr.trim();

        PostUserInfo(nickNameStr, introStr);


    }

    @OnClick(R.id.back_btn) void goBack(){
        finish();
    }

    /**
     * user profile img
     */
    @OnClick(R.id.user_profile_img) void goProfile(){
        /**
         * os 6.0 권한체크 및 요청
         */
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) + ContextCompat
                .checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale
                    (this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission
                                .WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS_READ_EXTERNAL_STORAGE);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission
                                .WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS_READ_EXTERNAL_STORAGE);
            }

        } else {
            Intent intent = new Intent(getApplicationContext(), ImageCropActivity.class);
            startActivity(intent);
        }
    }
}
