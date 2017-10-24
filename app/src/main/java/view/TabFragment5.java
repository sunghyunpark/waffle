package view;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yssh.waffle.AppConfig;
import com.yssh.waffle.MainActivity;
import com.yssh.waffle.R;
import com.yssh.waffle.SessionManager;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import database.RealmConfig;
import database.model.UserVO;
import io.realm.Realm;
import model.UserModel;

public class TabFragment5 extends Fragment {
    //os6.0 permission
    private static final int REQUEST_PERMISSIONS_READ_EXTERNAL_STORAGE = 10;
    View v;
    @BindView(R.id.user_profile_img) ImageView user_profile_iv;
    @BindView(R.id.user_name_txt) TextView user_name_tv;
    @BindView(R.id.user_email_txt) TextView user_email_tv;
    @BindView(R.id.logout_btn) Button logoutBtn;
    @BindView(R.id.user_profile_edit_layout) ViewGroup userProfileEditLayout;
    @BindString(R.string.logout_title_txt) String logoutTitleStr;
    @BindString(R.string.logout_sub_txt) String logoutSubStr;
    @BindView(R.id.app_version_txt) TextView app_version_tv;
    @BindString(R.string.permission_error_txt) String permissionStr;


    public TabFragment5() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume(){
        super.onResume();

        SetUI();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_tab_fragment5, container, false);
        ButterKnife.bind(this, v);

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
                .load(AppConfig.ServerAddress+UserModel.getInstance().getProfile_img())
                .into(user_profile_iv);

        user_name_tv.setText(UserModel.getInstance().getNick_name());
        user_email_tv.setText(UserModel.getInstance().getEmail());

        //앱 버전
        String version;
        try {
            PackageInfo i = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
            version = i.versionName;
            app_version_tv.setText("v"+version);
        } catch(PackageManager.NameNotFoundException e) { }

    }

    /**
     * os 6.0 권한
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSIONS_READ_EXTERNAL_STORAGE:
                //권한이 있는 경우
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(getActivity(), ImageCropActivity.class);
                    startActivity(intent);
                }
                //권한이 없는 경우
                else {
                    Toast.makeText(getActivity(), permissionStr, Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    @OnClick(R.id.user_profile_img) void goProfile(){
        /**
         * os 6.0 권한체크 및 요청
         */
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) + ContextCompat
                .checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale
                    (getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission
                                .WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS_READ_EXTERNAL_STORAGE);
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission
                                .WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS_READ_EXTERNAL_STORAGE);
            }

        } else {
            Intent intent = new Intent(getActivity(), ImageCropActivity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.logout_btn) void Logout(){

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle(logoutTitleStr);
        alert.setMessage(logoutSubStr);
        alert.setPositiveButton("예", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                SessionManager mSessionManager = new SessionManager(getActivity());
                mSessionManager.setLogin(false);
                RealmConfig realmConfig = new RealmConfig();
                Realm mRealm = Realm.getInstance(realmConfig.UserRealmVersion(getActivity()));
                UserVO userVO = mRealm.where(UserVO.class).equalTo("no",1).findFirst();
                mRealm.beginTransaction();
                userVO.deleteFromRealm();
                mRealm.commitTransaction();

                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        alert.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.

                    }
                });
        alert.show();
    }

    @OnClick(R.id.user_profile_edit_layout) void goEditProfile(){
        Intent intent = new Intent(getActivity(), EditUserProfileActivity.class);
        startActivity(intent);
    }

}
