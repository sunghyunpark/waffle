package view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yssh.waffle.AppConfig;
import com.yssh.waffle.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import model.UserModel;

public class TabFragment5 extends Fragment {

    View v;
    @BindView(R.id.user_profile_img) ImageView user_profile_iv;
    @BindView(R.id.user_name_txt) TextView user_name_tv;
    @BindView(R.id.user_email_txt) TextView user_email_tv;
    @BindView(R.id.logout_btn) Button logoutBtn;

    public TabFragment5() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_tab_fragment5, container, false);
        ButterKnife.bind(this, v);

        SetUI();


        return v;
    }

    private void SetUI(){
        //Glide Options
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.mipmap.user_profile_img);
        requestOptions.error(R.mipmap.user_profile_img);
        requestOptions.circleCrop();    //circle

        Glide.with(getActivity())
                .setDefaultRequestOptions(requestOptions)
                .load(AppConfig.ServerAddress)
                .into(user_profile_iv);

        user_name_tv.setText(UserModel.getInstance().getNick_name());
        user_email_tv.setText(UserModel.getInstance().getEmail());
    }

}
