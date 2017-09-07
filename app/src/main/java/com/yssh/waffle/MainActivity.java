package com.yssh.waffle;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

    @BindView(R2.id.tab_1) ViewGroup tabBtn1;
    @BindView(R2.id.tab_2) ViewGroup tabBtn2;
    @BindView(R2.id.tab_3) ViewGroup tabBtn3;
    @BindView(R2.id.tab_4) ViewGroup tabBtn4;
    @BindView(R2.id.tab_5) ViewGroup tabBtn5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, new TabFragment1());
        fragmentTransaction.commit();
    }

    @OnClick({R2.id.tab_1, R2.id.tab_2, R2.id.tab_3, R2.id.tab_4, R2.id.tab_5}) void click(View v){
        Fragment fragment = null;
        Bundle bundle = new Bundle();
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
