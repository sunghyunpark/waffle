package view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yssh.waffle.AppConfig;
import com.yssh.waffle.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import api.ApiClient;
import api.ApiInterface;
import api.response.CafeEtcInfoResponse;
import api.response.CafeResponse;
import api.response.CommonResponse;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import model.CafeModel;
import model.CommentModel;
import model.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import util.CommonUtil;

public class AboutCafeActivity extends AppCompatActivity {

    CafeModel cafeModel;
    private String cafe_id;
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
    @BindView(R.id.cafe_day_off_txt) TextView about_cafe_day_off_tv;
    @BindView(R.id.cafe_day_off_layout) ViewGroup about_cafe_day_off_layout;
    @BindView(R.id.cafe_like_btn) ImageButton cafeLikeBtn;
    @BindView(R.id.about_cafe_weekdays_open_close_txt) TextView about_cafe_weekdays_open_close_tv;
    @BindView(R.id.about_cafe_weekend_open_close_txt) TextView about_cafe_weekend_open_close_tv;
    @BindView(R.id.comment_btn_layout) ViewGroup commentBtn;    //상단 리뷰쓰기 버튼
    @BindView(R.id.empty_comment_layout) ViewGroup emptyCommentLayout;
    @BindView(R.id.about_cafe_intro_layout) ViewGroup cafeIntroLayout;
    @BindView(R.id.about_cafe_intro_txt) TextView about_cafe_intro_tv;
    @BindView(R.id.go_all_comment_btn) Button goAllCommentBtn;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.cafe_etc_photo_btn) ImageView cafeEtcPhotoBtn;
    @BindString(R.string.network_error_txt) String networkErrorStr;
    private ArrayList<String> cafePhotoList;
    //RecyclerView
    RecyclerAdapter adapter;
    private ArrayList<CommentModel> commentModelArrayList;
    CommonUtil commonUtil = new CommonUtil();

    private boolean cafeLikeState = false;

    @Override
    public void onResume(){
        super.onResume();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_cafe);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        cafeModel = new CafeModel();
        //isData AboutCafeActivity에 접근하기전에 해당 카페에 대한 데이터를 가지고있는지 없는지에 대한 flag
        String isData = intent.getExtras().getString("isData");
        if(isData.equals("Y")){
            //해당 카페에 대한 정보들을 가지고있는 상태에서 접근
            cafeModel = (CafeModel)intent.getExtras().getSerializable("CafeModel");
            SetUI();
        }else{
            //해당 카페에 대한 정보들을 가지고있지 않는 상태에서 접근
            cafe_id = intent.getExtras().getString("cafe_id");
            LoadAboutCafeInfoWithCafeId(cafe_id);
        }

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
        try{
            if(cafeModel.getCafeDayOff().equals("")){
                about_cafe_day_off_layout.setVisibility(View.GONE);
            }else{
                about_cafe_day_off_layout.setVisibility(View.VISIBLE);
                about_cafe_day_off_tv.setText(cafeModel.getCafeDayOff());
            }
        }catch (NullPointerException e){

        }

        if(cafeModel.getCafeIntro().equals("")){
            cafeIntroLayout.setVisibility(View.GONE);
        }else{
            cafeIntroLayout.setVisibility(View.VISIBLE);
            about_cafe_intro_tv.setText(cafeModel.getCafeIntro());
        }

        //recyclerview 초기화
        commentModelArrayList = new ArrayList<CommentModel>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        adapter = new RecyclerAdapter(commentModelArrayList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);

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
     * 데이터가 없는 상태에서 접근 시 cafe_id로 서버에서 데이터를 불러옴
     * @param cafe_id
     */
    private void LoadAboutCafeInfoWithCafeId(String cafe_id){
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<CafeResponse> call = apiService.GetAboutCafeInfo("about_cafe_info_with_cafe_id", cafe_id);
        call.enqueue(new Callback<CafeResponse>() {
            @Override
            public void onResponse(Call<CafeResponse> call, Response<CafeResponse> response) {
                CafeResponse cafeResponse = response.body();
                if(!cafeResponse.isError()){
                    try{
                        /*
                        cafeModel.setCafeId(cafeResponse.getCafeList().get(0).getCafeId());
                        cafeModel.setCafeName(cafeResponse.getCafeList().get(0).getCafeName());
                        cafeModel.setCafeThumbnail(cafeResponse.getCafeList().get(0).getCafeThumbnail());
                        cafeModel.setCafePhoneNum(cafeResponse.getCafeList().get(0).getCafePhoneNum());
                        cafeModel.setCafeAddress(cafeResponse.getCafeList().get(0).getCafeAddress());
                        cafeModel.setCafeWeekendOpenTime(cafeResponse.getCafeList().get(0).getCafeWeekendOpenTime());
                        cafeModel.setCafeIntro(cafeResponse.getCafeList().get(0).getCafeIntro());
                        cafeModel.setCafeWeekDaysCloseTime(cafeResponse.getCafeList().get(0).getCafeWeekDaysCloseTime());
                        cafeModel.setCafeWeekDaysOpenTime(cafeResponse.getCafeList().get(0).getCafeWeekDaysOpenTime());
                        cafeModel.setCafeDayOff(cafeResponse.getCafeList().get(0).getCafeDayOff());
                        cafeModel.setCafeFullTimeState(cafeResponse.getCafeList().get(0).getCafeFullTimeState());
                        cafeModel.setCafeWifiState(cafeResponse.getCafeList().get(0).getCafeWifiState());
                        cafeModel.setCafeLatitude(cafeResponse.getCafeList().get(0).getCafeLatitude());
                        cafeModel.setCafeLongitude(cafeResponse.getCafeList().get(0).getCafeLongitude());
                        cafeModel.setCafeParkingState(cafeResponse.getCafeList().get(0).getCafeParkingState());
                        cafeModel.setCafeWeekendCloseTime(cafeResponse.getCafeList().get(0).getCafeWeekendCloseTime());
                        cafeModel.setCafeSmokeState(cafeResponse.getCafeList().get(0).getCafeSmokeState());
                        */
                        cafeModel = cafeResponse.getCafeList().get(0);
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }finally {
                        SetUI();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), cafeResponse.getError_msg(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<CafeResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("tag", t.toString());
                Toast.makeText(getApplicationContext(), networkErrorStr,Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Load Cafe Etc Info
     * @param cafe_id
     */
    private void LoadCafeEtcInfo(String uid, String cafe_id){
        cafePhotoList = new ArrayList<>();

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<CafeEtcInfoResponse> call = apiService.GetCafeEtcInfo("cafe_etc_info", cafe_id, uid, "N");
        call.enqueue(new Callback<CafeEtcInfoResponse>() {
            @Override
            public void onResponse(Call<CafeEtcInfoResponse> call, Response<CafeEtcInfoResponse> response) {
                CafeEtcInfoResponse cafeEtcInfoResponse = response.body();
                if(!cafeEtcInfoResponse.isError()){
                    String cafePhoto = "";
                    try {
                        int photo_size = cafeEtcInfoResponse.getCafe_etc_photo_list().size();
                        for(int i=0;i<photo_size;i++){
                            cafePhotoList.add(cafeEtcInfoResponse.getCafe_etc_photo_list().get(i));
                        }
                        cafePhoto = AppConfig.ServerAddress+cafeEtcInfoResponse.getCafe_etc_photo_list().get(0);
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }catch (IndexOutOfBoundsException i){
                        cafePhoto = null;
                    } finally {
                        //Glide Options
                        RequestOptions requestOptions = new RequestOptions();
                        requestOptions.centerCrop();

                        Glide.with(getApplicationContext())
                                .setDefaultRequestOptions(requestOptions)
                                .load(cafePhoto)
                                .into(cafe_img_iv);
                    }
                    try{
                        int comment_size = cafeEtcInfoResponse.getComment_text_list().size();
                        if(comment_size > 0){
                            recyclerView.setVisibility(View.VISIBLE);
                            emptyCommentLayout.setVisibility(View.GONE);
                            for(int i=0;i<comment_size;i++){
                                commentModelArrayList.add(cafeEtcInfoResponse.getComment_text_list().get(i));
                            }
                            recyclerView.setAdapter(adapter);
                        }else{
                            recyclerView.setVisibility(View.GONE);
                            emptyCommentLayout.setVisibility(View.VISIBLE);
                        }
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }

                }else{
                    Toast.makeText(getApplicationContext(), cafeEtcInfoResponse.getError_msg(),Toast.LENGTH_SHORT).show();
                    //Glide Options
                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.centerCrop();

                    Glide.with(getApplicationContext())
                            .setDefaultRequestOptions(requestOptions)
                            .load(AppConfig.ServerAddress+cafeModel.getCafeThumbnail())
                            .into(cafe_img_iv);
                }
                //cafe like state init
                cafeLikeState = cafeEtcInfoResponse.isLike_state();
                SetLikeBtn(cafeLikeState);
            }

            @Override
            public void onFailure(Call<CafeEtcInfoResponse> call, Throwable t) {
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

    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private static final int TYPE_ITEM = 0;

        List<CommentModel> listItems;

        private RecyclerAdapter(List<CommentModel> listItems) {
            this.listItems = listItems;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_ITEM) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_comment_item, parent, false);
                return new Comment_VH(v);
            }
            throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
        }

        private CommentModel getItem(int position) {
            return listItems.get(position);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

            if (holder instanceof Comment_VH) {
                final CommentModel currentItem = getItem(position);
                final Comment_VH VHitem = (Comment_VH)holder;

                //Glide Options
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.placeholder(R.mipmap.user_profile_img);
                requestOptions.error(R.mipmap.user_profile_img);
                requestOptions.circleCrop();    //circle

                Glide.with(getApplicationContext())
                        .setDefaultRequestOptions(requestOptions)
                        .load(AppConfig.ServerAddress+currentItem.getProfile_img_thumb())
                        .into(VHitem.userProfile_iv);

                VHitem.userName_tv.setText(currentItem.getNick_name());
                VHitem.comment_tv.setText(currentItem.getComment_text());
                Date to = null;
                try{
                    SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    to = transFormat.parse(currentItem.getCreated_at());
                }catch (ParseException p){
                    p.printStackTrace();
                }
                VHitem.updated_tv.setText(commonUtil.formatTimeString(to));

            }
        }

        /**
         * 리뷰 아이템
         */
        private class Comment_VH extends RecyclerView.ViewHolder{

            ImageView userProfile_iv;
            TextView userName_tv;
            TextView updated_tv;
            TextView comment_tv;

            private Comment_VH(View itemView){
                super(itemView);
                userProfile_iv = (ImageView)itemView.findViewById(R.id.user_profile_img);
                userName_tv = (TextView)itemView.findViewById(R.id.user_name_txt);
                updated_tv = (TextView)itemView.findViewById(R.id.updated_at_txt);
                comment_tv = (TextView)itemView.findViewById(R.id.comment_txt);
            }
        }

        @Override
        public int getItemViewType(int position) {
            return TYPE_ITEM;
        }
        //increasing getItemcount to 1. This will be the row of header.
        @Override
        public int getItemCount() {
            return listItems.size();
        }
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
    @OnClick(R.id.go_all_comment_btn) void goAllComment(){
        Intent intent = new Intent(getApplicationContext(), AboutCafeCommentActivity.class);
        intent.putExtra("cafe_id", cafeModel.getCafeId());
        intent.putExtra("cafe_name", cafeModel.getCafeName());
        startActivity(intent);
    }
    @OnClick(R.id.cafe_etc_photo_btn) void goEtcPhoto(){
        Intent intent = new Intent(getApplicationContext(), PhotoSelectActivity.class);
        intent.putExtra("photoList", cafePhotoList);
        intent.putExtra("cafeName", cafeModel.getCafeName());
        startActivity(intent);
    }
}
