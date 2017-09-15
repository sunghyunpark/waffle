package view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.yssh.waffle.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EmailLoginActivity extends AppCompatActivity {

    @BindView(R.id.back_btn) ImageButton backBtn;
    @BindView(R.id.login_btn) Button loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_login);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.back_btn) void goBack(){
        finish();
    }
    @OnClick(R.id.login_btn) void Login(){
        Toast.makeText(getApplicationContext(), "Login", Toast.LENGTH_SHORT).show();
    }
}
