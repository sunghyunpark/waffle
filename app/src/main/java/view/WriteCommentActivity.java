package view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yssh.waffle.AppConfig;
import com.yssh.waffle.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WriteCommentActivity extends AppCompatActivity implements TextWatcher {

    @BindView(R.id.back_btn) ImageButton backBtn;
    @BindView(R.id.user_profile_img) ImageView user_profile_iv;
    @BindView(R.id.comment_edit_box) EditText comment_et;
    @BindView(R.id.comment_length_txt) TextView comment_length_tv;
    private String user_id, cafe_id;
    private String beforeStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_comment);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        user_id = intent.getExtras().getString("user_id");
        cafe_id = intent.getExtras().getString("cafe_id");

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
                .load(AppConfig.ServerAddress)
                .into(user_profile_iv);

        comment_et.addTextChangedListener(this);
    }

    @OnClick(R.id.back_btn) void goBack(){
        finish();
    }

    @Override
    public void afterTextChanged(Editable s) {
        if(s.length() >= 150)
        {
            Toast.makeText(getApplicationContext(), "150자까지 입력 가능합니다.", Toast.LENGTH_SHORT).show();
            comment_et.setText(beforeStr);
        }
        comment_length_tv.setText(s.length() + "/150");
        comment_length_tv.setTextColor(getResources().getColor(R.color.colorTextHintGray));
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
        beforeStr = s.toString();

    }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }
}
