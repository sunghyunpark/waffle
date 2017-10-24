package view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yssh.waffle.AppConfig;
import com.yssh.waffle.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import model.UserModel;

public class EditUserProfileActivity extends AppCompatActivity {

    @BindView(R.id.user_profile_img) ImageView user_profile_iv;
    @BindView(R.id.user_email_txt) TextView user_email_tv;
    @BindView(R.id.user_nickname_txt) EditText user_nickName_tv;
    @BindView(R.id.user_intro_edit_box) EditText user_intro_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        ButterKnife.bind(this);

        SetUI();
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
        user_nickName_tv.setText(UserModel.getInstance().getNick_name());
        user_intro_et.setText(UserModel.getInstance().getIntro());
    }
}
