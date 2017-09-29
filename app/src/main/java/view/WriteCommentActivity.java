package view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yssh.waffle.R;

public class WriteCommentActivity extends AppCompatActivity {

    private String user_id, cafe_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_comment);

        Intent intent = getIntent();
        user_id = intent.getExtras().getString("user_id");
        cafe_id = intent.getExtras().getString("cafe_id");


    }
}
