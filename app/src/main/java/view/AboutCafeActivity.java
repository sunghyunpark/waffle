package view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yssh.waffle.AppConfig;
import com.yssh.waffle.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import model.CafeModel;

public class AboutCafeActivity extends AppCompatActivity {

    CafeModel cafeModel;
    @BindView(R.id.cafe_img) ImageView cafe_img_iv;
    @BindView(R.id.title_txt) TextView title_tv;
    @BindView(R.id.cafe_name_txt) TextView cafe_name_tv;
    @BindView(R.id.cafe_address_txt) TextView cafe_address_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_cafe);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        cafeModel = new CafeModel();
        cafeModel = (CafeModel)intent.getExtras().getSerializable("CafeModel");

        SetUI();
    }

    /**
     * Init Ui
     */
    private void SetUI(){
        title_tv.setText(cafeModel.getCafeName());
        cafe_name_tv.setText(cafeModel.getCafeName());
        cafe_address_tv.setText(cafeModel.getCafeAddress());

        //Glide Options
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.mipmap.not_cafe_img);
        requestOptions.error(R.mipmap.not_cafe_img);
        requestOptions.centerCrop();

        Glide.with(getApplicationContext())
                .setDefaultRequestOptions(requestOptions)
                .load(AppConfig.ServerAddress+"img/cafe_etc_photo/1_cafe_etc_photo_1.jpg")
                .into(cafe_img_iv);
    }
}
