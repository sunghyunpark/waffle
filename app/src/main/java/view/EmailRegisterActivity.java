package view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;

import com.yssh.waffle.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EmailRegisterActivity extends AppCompatActivity {

    @BindView(R.id.back_btn) ImageButton backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_register);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.back_btn) void goBack(){
        finish();
    }
}
