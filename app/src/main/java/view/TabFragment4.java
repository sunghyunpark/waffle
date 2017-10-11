package view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yssh.waffle.R;

import api.ApiClient;
import api.ApiInterface;
import api.response.MyFavoriteCntResponse;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import model.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabFragment4 extends Fragment {

    View v;
    @BindView(R.id.my_favorite_cafe_img) ImageView my_favorite_cafe_iv;
    @BindView(R.id.my_comment_cafe_img) ImageView my_comment_cafe_iv;
    @BindView(R.id.like_cnt_txt) TextView my_favorite_cnt_tv;
    @BindView(R.id.comment_cnt_txt) TextView my_comment_cnt_tv;
    @BindString(R.string.network_error_txt) String networkErrorStr;

    public TabFragment4() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_tab_fragment4, container, false);
        // Inflate the layout for this fragment
        ButterKnife.bind(this, v);


        //Glide Options
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.mipmap.not_cafe_img);
        requestOptions.error(R.mipmap.not_cafe_img);
        requestOptions.centerCrop();

        Glide.with(getActivity())
                .setDefaultRequestOptions(requestOptions)
                .load(R.drawable.like_cafe_img)
                .into(my_favorite_cafe_iv);

        Glide.with(getActivity())
                .setDefaultRequestOptions(requestOptions)
                .load(R.drawable.comment_cafe_img)
                .into(my_comment_cafe_iv);

        LoadData(UserModel.getInstance().getUid());

        return v;
    }

    private void LoadData(String uid){
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<MyFavoriteCntResponse> call = apiService.GetMyFavoriteCnt("my_favorite_cafe_cnt", uid);
        call.enqueue(new Callback<MyFavoriteCntResponse>() {
            @Override
            public void onResponse(Call<MyFavoriteCntResponse> call, Response<MyFavoriteCntResponse> response) {
                MyFavoriteCntResponse myFavoriteCntResponse = response.body();
                if(!myFavoriteCntResponse.isError()){
                    my_favorite_cnt_tv.setText(myFavoriteCntResponse.getMyFavoriteCafeCnt());
                    my_comment_cnt_tv.setText(myFavoriteCntResponse.getMyCommentCafeCnt());
                }else{
                    Toast.makeText(getActivity(), myFavoriteCntResponse.getError_msg(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<MyFavoriteCntResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("tag", t.toString());
                Toast.makeText(getActivity(), networkErrorStr,Toast.LENGTH_SHORT).show();
            }
        });
    }

}
