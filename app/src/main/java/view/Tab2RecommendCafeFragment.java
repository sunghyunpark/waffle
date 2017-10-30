package view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yssh.waffle.AppConfig;
import com.yssh.waffle.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Tab2RecommendCafeFragment extends Fragment {

    private int position;
    private String cafe_id, cafe_name, cafe_thumbnail, cafe_address;
    @BindView(R.id.recommend_cafe_img) ImageView cafeThumbnail_iv;
    @BindView(R.id.recommend_cafe_address_txt) TextView cafeAddress_tv;
    @BindView(R.id.recommend_cafe_txt) TextView cafeName_tv;
    View v;

    public static Tab2RecommendCafeFragment create(int position, String cafeThumbnail, String cafeId, String cafeName, String cafeAddress) {
        Tab2RecommendCafeFragment fragment = new Tab2RecommendCafeFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putString("cafe_thumbnail", cafeThumbnail);
        args.putString("cafe_id", cafeId);
        args.putString("cafe_name", cafeName);
        args.putString("cafe_address", cafeAddress);

        fragment.setArguments(args);
        return fragment;
    }
    public Tab2RecommendCafeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt("position");
        cafe_id = getArguments().getString("cafe_id");
        cafe_name = getArguments().getString("cafe_name");
        cafe_thumbnail = getArguments().getString("cafe_thumbnail");
        cafe_address = getArguments().getString("cafe_address");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = (ViewGroup)inflater.inflate(R.layout.fragment_tab2_recommend_cafe,container, false);
        ButterKnife.bind(this, v);

        //Glide Options
        RequestOptions requestOptions = new RequestOptions();

        Glide.with(getActivity())
                .setDefaultRequestOptions(requestOptions)
                .load(AppConfig.ServerAddress+cafe_thumbnail)
                .into(cafeThumbnail_iv);

        cafeName_tv.setText(cafe_name);
        cafeAddress_tv.setText(cafe_address);

        return v;
    }

    @OnClick(R.id.recommend_cafe_img) void goAboutCafe(){
        Intent intent = new Intent(getActivity(), AboutCafeActivity.class);
        intent.putExtra("isData", "N");
        intent.putExtra("cafe_id", cafe_id);
        startActivity(intent);
    }

}
