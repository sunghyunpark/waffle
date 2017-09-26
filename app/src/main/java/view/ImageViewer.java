package view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yssh.waffle.R;

import util.CropView;

public class ImageViewer extends AppCompatActivity {

    CropView ImageViewer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);

        Intent intent = getIntent();
        String user_article_bg = intent.getExtras().getString("user_article_bg");
        ImageViewer = (CropView)findViewById(R.id.image_view_pic);



    }
}
