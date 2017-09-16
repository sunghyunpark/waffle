package view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.yssh.waffle.MainActivity;
import com.yssh.waffle.R;
import com.yssh.waffle.SessionManager;

import api.ApiClient;
import api.ApiInterface;
import api.response.LoginResponse;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import database.RealmUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmailLoginActivity extends AppCompatActivity {

    @BindView(R.id.back_btn) ImageButton backBtn;
    @BindView(R.id.login_btn) Button loginBtn;
    @BindView(R.id.email_edit_box) EditText email_et;
    @BindView(R.id.password_edit_box) EditText pw_et;
    @BindString(R.string.network_error_txt) String networkErrorStr;
    @BindString(R.string.register_error_input_email_txt) String inputEmailErrorStr;
    @BindString(R.string.error_not_exist_input_txt) String notExistErrorStr;
    @BindString(R.string.register_error_input_pw_txt) String inputPwErrorStr;

    RealmUtil realmUtil = new RealmUtil();
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_login);

        ButterKnife.bind(this);
        sessionManager = new SessionManager(getApplicationContext());
    }

    @OnClick(R.id.back_btn) void goBack(){
        finish();
    }
    @OnClick(R.id.login_btn) void Login(){
        String emailStr = email_et.getText().toString().trim();
        String pwStr = pw_et.getText().toString().trim();

        if(emailStr.equals("")){
            Toast.makeText(getApplicationContext(), notExistErrorStr, Toast.LENGTH_SHORT).show();
        }else if(!emailStr.contains("@") || !emailStr.contains(".com")){
            Toast.makeText(getApplicationContext(), inputEmailErrorStr,Toast.LENGTH_SHORT).show();
        }else if(pwStr.length()<6){
            Toast.makeText(getApplicationContext(), inputPwErrorStr, Toast.LENGTH_SHORT).show();
        }else{
            GetUserData(emailStr, pwStr);
        }
    }

    /**
     * Get User Data By Login
     * @param email
     * @param password
     */
    private void GetUserData(String email, String password){
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<LoginResponse> call = apiService.GetUserDataByLogin("login", email, password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                if(!loginResponse.isError()){
                    String uid = loginResponse.getUser().getUid();
                    String email = loginResponse.getUser().getEmail();
                    String nickName = loginResponse.getUser().getNick_name();
                    String fb_id = loginResponse.getUser().getFb_id();
                    String created_at = loginResponse.getUser().getCreated_at();
                    String profile_img = loginResponse.getUser().getProfile_img();
                    String profile_img_thumb = loginResponse.getUser().getProfile_img_thumb();
                    String intro = loginResponse.getUser().getIntro();

                    realmUtil.InsertDB(getApplicationContext(), uid, email, nickName, fb_id, created_at, profile_img, profile_img_thumb, intro);
                    sessionManager.setLogin(true);    //session OK

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }else{

                }
                Toast.makeText(getApplicationContext(), loginResponse.getError_msg(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("tag", t.toString());
                Toast.makeText(getApplicationContext(), networkErrorStr,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
