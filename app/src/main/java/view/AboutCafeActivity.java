package view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yssh.waffle.AppConfig;
import com.yssh.waffle.R;

import java.util.ArrayList;

import api.ApiClient;
import api.ApiInterface;
import api.response.CafeFeatureResponse;
import api.response.CommonResponse;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import model.CafeModel;
import model.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutCafeActivity extends AppCompatActivity {

    CafeModel cafeModel;
    @BindView(R.id.cafe_img) ImageView cafe_img_iv;
    @BindView(R.id.title_txt) TextView title_tv;
    @BindView(R.id.cafe_name_txt) TextView cafe_name_tv;
    @BindView(R.id.cafe_address_txt) TextView cafe_address_tv;
    @BindView(R.id.back_btn) ImageButton backBtn;
    @BindView(R.id.cafe_full_time_state_img) ImageView full_time_state_iv;
    @BindView(R.id.cafe_wifi_state_img) ImageView wifi_state_iv;
    @BindView(R.id.cafe_smoke_state_img) ImageView smoke_state_iv;
    @BindView(R.id.cafe_parking_state_img) ImageView parking_state_iv;
    @BindView(R.id.about_cafe_address_txt) TextView about_cafe_address_tv;
    @BindView(R.id.about_cafe_phone_txt) TextView about_cafe_phone_tv;
    @BindView(R.id.cafe_like_btn) ImageButton cafeLikeBtn;
    @BindView(R.id.about_cafe_weekdays_open_close_txt) TextView about_cafe_weekdays_open_close_tv;
    @BindView(R.id.about_cafe_weekend_open_close_txt) TextView about_cafe_weekend_open_close_tv;
    @BindView(R.id.comment_btn_layout) ViewGroup commentBtn;    //상단 리뷰쓰기 버튼
    @BindString(R.string.network_error_txt) String networkErrorStr;
    private ArrayList<String> cafePhotoList;

    private boolean cafeLikeState = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_cafe);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        cafeModel = new CafeModel();
        cafeModel = (CafeModel)intent.getExtras().getSerializable("CafeModel");

        SetUI();
    }

    /**
     * Init Ui
     */
    private void SetUI(){
        title_tv.setText(cafeModel.getCafeName());
        cafe_name_tv.setText(cafeModel.getCafeName());
        cafe_address_tv.setText(cafeModel.getCafeAddress());
        about_cafe_address_tv.setText(cafeModel.getCafeAddress());
        about_cafe_phone_tv.setText(cafeModel.getCafePhoneNum());
        about_cafe_weekdays_open_close_tv.setText(cafeModel.getCafeWeekDaysOpenTime() + " ~ " + cafeModel.getCafeWeekDaysCloseTime());
        about_cafe_weekend_open_close_tv.setText(cafeModel.getCafeWeekendOpenTime() + " ~ " + cafeModel.getCafeWeekendCloseTime());

        if(cafeModel.getCafeFullTimeState().equals("Y")){
            full_time_state_iv.setBackgroundResource(R.mipmap.about_cafe_full_time_img);
        }else{
            full_time_state_iv.setBackgroundResource(R.mipmap.about_cafe_not_full_time_img);
        }
        if(cafeModel.getCafeWifiState().equals("Y")){
            wifi_state_iv.setBackgroundResource(R.mipmap.about_cafe_wifi_img);
        }else{
            wifi_state_iv.setBackgroundResource(R.mipmap.about_cafe_not_wifi_img);
        }
        if(cafeModel.getCafeSmokeState().equals("Y")){
            smoke_state_iv.setBackgroundResource(R.mipmap.about_cafe_smoke_img);
        }else{
            smoke_state_iv.setBackgroundResource(R.mipmap.about_cafe_not_smoke_img);
        }
        if(cafeModel.getCafeParkingState().equals("Y")){
            parking_state_iv.setBackgroundResource(R.mipmap.about_cafe_parking_img);
        }else{
            parking_state_iv.setBackgroundResource(R.mipmap.about_cafe_not_parking_img);
        }

        LoadCafeEtcInfo(UserModel.getInstance().getUid(), cafeModel.getCafeId());    //Load Cafe Etc Info
    }

    /**
     * Load Cafe Etc Info
     * @param cafe_id
     */
    private void LoadCafeEtcInfo(String uid, String cafe_id){
        cafePhotoList = new ArrayList<>();

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<CafeFeatureResponse> call = apiService.GetCafeEtcInfo("cafe_etc_info", cafe_id, uid);
        call.enqueue(new Callback<CafeFeatureResponse>() {
            @Override
            public void onResponse(Call<CafeFeatureResponse> call, Response<CafeFeatureResponse> response) {
                CafeFeatureResponse cafeFeatureResponse = response.body();
                if(!cafeFeatureResponse.isError()){
                    int size = cafeFeatureResponse.getCafe_etc_photo_list().size();
                    for(int i=0;i<size;i++){
                        cafePhotoList.add(cafeFeatureResponse.getCafe_etc_photo_list().get(i));
                    }

                    //Glide Options
                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.placeholder(R.mipmap.not_cafe_img);
                    requestOptions.error(R.mipmap.not_cafe_img);
                    requestOptions.centerCrop();

                    Glide.with(getApplicationContext())
                            .setDefaultRequestOptions(requestOptions)
                            .load(AppConfig.ServerAddress+cafePhotoList.get(0))
                            .into(cafe_img_iv);

                }else{
                    Toast.makeText(getApplicationContext(), cafeFeatureResponse.getError_msg(),Toast.LENGTH_SHORT).show();
                }
                //cafe like state init
                cafeLikeState = cafeFeatureResponse.isLike_state();
                SetLikeBtn(cafeLikeState);
            }

            @Override
            public void onFailure(Call<CafeFeatureResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("tag", t.toString());
                Toast.makeText(getApplicationContext(), networkErrorStr,Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Set Like Button
     * @param likeState -> Like State
     */
    private void SetLikeBtn(boolean likeState){
        if(likeState){
            //Not Like State -> Like
            cafeLikeState = true;
            cafeLikeBtn.setBackgroundResource(R.mipmap.about_cafe_like_img);
        }else{
            //Like State -> Not Like
            cafeLikeState = false;
            cafeLikeBtn.setBackgroundResource(R.mipmap.about_cafe_not_like_img);
        }
    }

    /**
     * Post Like State
     * @param uid
     * @param cafe_id
     * @param state -> N/Y
     */
    private void PostCafeLike(String uid, String cafe_id, String state){
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<CommonResponse> call = apiService.LikeCafe("like_cafe", uid, cafe_id, state);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                CommonResponse commonResponse = response.body();
                if(!commonResponse.isError()){
                    Toast.makeText(getApplicationContext(), commonResponse.getError_msg(),Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), commonResponse.getError_msg(),Toast.LENGTH_SHORT).show();
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

    @OnClick(R.id.back_btn) void goBack(){
        finish();
    }
    @OnClick(R.id.about_cafe_phone_txt) void goCall(){
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+cafeModel.getCafePhoneNum()));
        startActivity(intent);
    }
    @OnClick(R.id.cafe_like_btn) void cafeLike(){
        cafeLikeState = !cafeLikeState;
        SetLikeBtn(cafeLikeState);
        String state = cafeLikeState ? "N" : "Y";

        PostCafeLike(UserModel.getInstance().getUid(), cafeModel.getCafeId(), state);
    }
    @OnClick({R.id.comment_btn_layout, R.id.go_comment_btn}) void goComment(){
        Intent intent = new Intent(getApplicationContext(), WriteCommentActivity.class);
        intent.putExtra("user_id", UserModel.getInstance().getUid());
        intent.putExtra("cafe_id", cafeModel.getCafeId());
        startActivity(intent);
    }
}
