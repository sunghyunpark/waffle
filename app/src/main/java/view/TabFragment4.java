package view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yssh.waffle.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TabFragment4 extends Fragment {

    View v;
    @BindView(R.id.my_favorite_cafe_img) ImageView my_favorite_cafe_iv;
    @BindView(R.id.my_comment_cafe_img) ImageView my_comment_cafe_iv;

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
        return v;
    }

}
