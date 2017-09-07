package com.yssh.waffle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Created SungHyun 2017-09-07
 * https://github.com/sunghyunpark/waffle
 * 1. git init
 * 2. git status
 * 3. git commit -am "commit"
 * 4. git push origin master / git push origin nts
 * 5. git merge nts
 * 6. git push
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
