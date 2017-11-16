package view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.yssh.waffle.AppConfig;
import com.yssh.waffle.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IntroActivity extends AppCompatActivity {

    AppConfig appConfig;
    Bitmap resized;
    Bitmap bitmap;
    @BindView(R.id.background_layout) ViewGroup background_layout;
    @BindView(R.id.facebook_btn) Button facebookBtn;
    @BindView(R.id.register_btn) Button registerBtn;
    @BindView(R.id.login_btn) Button loginBtn;

    @Override
    protected void onDestroy(){
        super.onDestroy();
        resized.recycle();
        bitmap.recycle();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        ButterKnife.bind(this);

        appConfig = (AppConfig)getApplicationContext();

        setBackground();
    }

    private void setBackground(){
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.intro_img);
        resized = Bitmap.createScaledBitmap(bitmap, appConfig.getDISPLAY_WIDTH(),
                appConfig.getDISPLAY_HEIGHT(), true);

        Drawable d = new BitmapDrawable(getResources(), resized);
        background_layout.setBackground(d);
    }

    @OnClick(R.id.facebook_btn) void facebookClick(){
        Toast.makeText(getApplicationContext(), "facebook", Toast.LENGTH_SHORT).show();
    }

    /**
     * Email Register Activity 로 이동
     */
    @OnClick(R.id.register_btn) void registerClick(){
        Intent intent = new Intent(getApplicationContext(), EmailRegisterActivity.class);
        startActivity(intent);
    }

    /**
     * Email Login Activity 로 이동
     */
    @OnClick(R.id.login_btn) void loginClick(){
        Intent intent = new Intent(getApplicationContext(), EmailLoginActivity.class);
        startActivity(intent);
    }
}
