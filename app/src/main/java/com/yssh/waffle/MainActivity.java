package com.yssh.waffle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import database.RealmConfig;
import database.model.UserVO;
import io.realm.Realm;
import model.UserModel;
import view.IntroActivity;
import view.TabFragment1;
import view.TabFragment2;
import view.TabFragment3;
import view.TabFragment4;
import view.TabFragment5;

/**
 * Created SungHyun 2017-09-07
 * https://github.com/sunghyunpark/waffle
 * 1. git init
 * 2. git status
 * 3. git commit -am "commit"
 * 4. git push origin master / git push origin sunghyun
 * 5. git merge sunghyun
 * 6. git push
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tab_1) ViewGroup tabBtn1;
    @BindView(R.id.tab_2) ViewGroup tabBtn2;
    @BindView(R.id.tab_3) ViewGroup tabBtn3;
    @BindView(R.id.tab_4) ViewGroup tabBtn4;
    @BindView(R.id.tab_5) ViewGroup tabBtn5;
    @BindView(R.id.tab1_img) ImageView tab1_iv;
    @BindView(R.id.tab2_img) ImageView tab2_iv;
    @BindView(R.id.tab3_img) ImageView tab3_iv;
    @BindView(R.id.tab4_img) ImageView tab4_iv;
    @BindView(R.id.tab5_img) ImageView tab5_iv;
    @BindView(R.id.tab1_txt) TextView tab1_tv;
    @BindView(R.id.tab2_txt) TextView tab2_tv;
    @BindView(R.id.tab3_txt) TextView tab3_tv;
    @BindView(R.id.tab4_txt) TextView tab4_tv;
    @BindView(R.id.tab5_txt) TextView tab5_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        SessionManager sessionManager = new SessionManager(getApplicationContext());

        if(sessionManager.isLoggedIn()){
            InitTabIcon(R.id.tab_1);

            android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.main_frame, new TabFragment1());
            fragmentTransaction.commit();

            SetUserDataFromRealm();
        }else{
            startActivity(new Intent(getApplicationContext(), IntroActivity.class));
            finish();
        }
    }

    /**
     * Insert UserModel(Singleton) From RealmData
     */
    private void SetUserDataFromRealm(){
        Realm mRealm;
        RealmConfig realmConfig;
        realmConfig = new RealmConfig();
        mRealm = Realm.getInstance(realmConfig.UserRealmVersion(getApplicationContext()));

        UserVO user = mRealm.where(UserVO.class).equalTo("no",1).findFirst();
        UserModel.getInstance().setUid(user.getUid());
        UserModel.getInstance().setEmail(user.getEmail());
        UserModel.getInstance().setNick_name(user.getNick_name());
        UserModel.getInstance().setFb_id(user.getFb_id());
        UserModel.getInstance().setProfile_img(user.getProfile_img());
        UserModel.getInstance().setProfile_img_thumb(user.getProfile_img_thumb());
        UserModel.getInstance().setIntro(user.getIntro());
        UserModel.getInstance().setCreated_at(user.getCreated_at());
        Log.d("UserInfo", "UserUid : "+user.getUid()+"");
        Log.d("UserInfo", "UserEmail : "+user.getEmail());
        Log.d("UserInfo", "UserName : "+user.getNick_name());
        Log.d("UserInfo", "Created_at : "+user.getCreated_at());
        Log.d("UserInfo", "Profile_img : "+user.getProfile_img());
        Log.d("UserInfo", "Fb_id : "+user.getFb_id());
        Log.d("UserInfo", "Intro : "+user.getIntro());
    }

    /**
     * TabMenu Icon Init Method
     * @param tabId -> Tab Id
     */
    private void InitTabIcon(int tabId){
        tab1_iv.setBackgroundResource(R.mipmap.tab_location_img);
        tab1_tv.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorGray));
        tab2_iv.setBackgroundResource(R.mipmap.tab_hot_img);
        tab2_tv.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorGray));
        tab3_iv.setBackgroundResource(R.mipmap.tab_issue_img);
        tab3_tv.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorGray));
        tab4_iv.setBackgroundResource(R.mipmap.tab_my_favorite_img);
        tab4_tv.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorGray));
        tab5_iv.setBackgroundResource(R.mipmap.tab_more_img);
        tab5_tv.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorGray));
        switch (tabId){
            case R.id.tab_1:
                tab1_iv.setBackgroundResource(R.mipmap.tab_selected_location_img);
                tab1_tv.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorSky));
                break;
            case R.id.tab_2:
                tab2_iv.setBackgroundResource(R.mipmap.tab_selected_hot_img);
                tab2_tv.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorSky));
                break;
            case R.id.tab_3:
                tab3_iv.setBackgroundResource(R.mipmap.tab_selected_issue_img);
                tab3_tv.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorSky));
                break;
            case R.id.tab_4:
                tab4_iv.setBackgroundResource(R.mipmap.tab_selected_my_favorite_img);
                tab4_tv.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorSky));
                break;
            case R.id.tab_5:
                tab5_iv.setBackgroundResource(R.mipmap.tab_selected_more_img);
                tab5_tv.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorSky));
                break;
        }
    }

    @OnClick({R.id.tab_1, R.id.tab_2, R.id.tab_3, R.id.tab_4, R.id.tab_5}) void click(View v){
        Fragment fragment = null;
        Bundle bundle = new Bundle();
        InitTabIcon(v.getId());

        switch (v.getId()){
            case R.id.tab_1:
                fragment = new TabFragment1();
                bundle.putString("KEY_MSG", "replace");
                fragment.setArguments(bundle);
                break;
            case R.id.tab_2:
                fragment = new TabFragment2();
                bundle.putString("KEY_MSG", "replace");
                fragment.setArguments(bundle);
                break;
            case R.id.tab_3:
                fragment = new TabFragment3();
                bundle.putString("KEY_MSG", "replace");
                fragment.setArguments(bundle);
                break;
            case R.id.tab_4:
                fragment = new TabFragment4();
                bundle.putString("KEY_MSG", "replace");
                fragment.setArguments(bundle);
                break;
            case R.id.tab_5:
                fragment = new TabFragment5();
                bundle.putString("KEY_MSG", "replace");
                fragment.setArguments(bundle);
                break;
        }
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }


}
