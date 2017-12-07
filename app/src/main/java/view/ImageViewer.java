package view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yssh.waffle.AppConfig;
import com.yssh.waffle.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ImageViewer 이미지들을 뷰페이저로 연속해서 보여준다.
 */
public class ImageViewer extends AppCompatActivity {

    @BindView(R.id.img_iv) ImageView img_iv;
    @BindView(R.id.title_txt) TextView title_tv;
    @BindView(R.id.back_btn) ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String imgPath = intent.getExtras().getString("imgPath");
        String cafeName = intent.getExtras().getString("cafeName");

        initView(imgPath, cafeName);
    }

    private void initView(String path, String cafeName){
        title_tv.setText(cafeName);
        //Glide Options
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.centerInside();

        Glide.with(getApplicationContext())
                .setDefaultRequestOptions(requestOptions)
                .load(AppConfig.ServerAddress+path)
                .into(img_iv);
    }

    @OnClick(R.id.back_btn) void goBack(){
        finish();
    }


}
