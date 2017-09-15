package view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.yssh.waffle.R;

import api.ApiClient;
import api.ApiInterface;
import api.response.LoginResponse;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmailRegisterActivity extends AppCompatActivity {

    @BindView(R.id.back_btn) ImageButton backBtn;
    @BindView(R.id.register_btn) Button registerBtn;
    @BindView(R.id.email_edit_box) EditText email_et;
    @BindView(R.id.email_password_edit_box) EditText pw_et;
    @BindView(R.id.name_edit_box) EditText nickName_et;
    @BindString(R.string.network_error_txt) String networkErrorStr;
    @BindString(R.string.register_error_input_email_txt) String inputEmailErrorStr;
    @BindString(R.string.error_not_exist_input_txt) String notExistErrorStr;
    @BindString(R.string.register_error_input_pw_txt) String inputPwErrorStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_register);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.back_btn) void goBack(){
        finish();
    }
    @OnClick(R.id.register_btn) void Register(){
        String emailStr = email_et.getText().toString().trim();
        String pwStr = pw_et.getText().toString().trim();
        String nickNameStr = nickName_et.getText().toString().trim();

        if(emailStr.equals("")){
            Toast.makeText(getApplicationContext(), notExistErrorStr, Toast.LENGTH_SHORT).show();
        }else if(!emailStr.contains("@") || !emailStr.contains(".com")){
            Toast.makeText(getApplicationContext(), inputEmailErrorStr,Toast.LENGTH_SHORT).show();
        }else if(pwStr.length()<6){
            Toast.makeText(getApplicationContext(), inputPwErrorStr, Toast.LENGTH_SHORT).show();
        }else{
            PostUserData(emailStr, nickNameStr, pwStr, "N", "N", "N", "N");
        }
    }

    /**
     * Post User Data
     * @param email
     * @param nick_name
     * @param password
     * @param fb_id
     * @param profile_img
     * @param profile_img_thumb
     * @param intro
     */
    private void PostUserData(String email, String nick_name, String password, String fb_id, String profile_img, String profile_img_thumb, String intro){
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<LoginResponse> call = apiService.EmailRegister("register", nick_name, email, password, fb_id, profile_img, profile_img_thumb, intro);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse cafeResponse = response.body();
                if(!cafeResponse.isError()){

                }else{

                }
                Toast.makeText(getApplicationContext(), cafeResponse.getError_msg(),Toast.LENGTH_SHORT).show();
                finish();
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
