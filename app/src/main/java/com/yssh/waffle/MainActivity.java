package com.yssh.waffle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }
}
