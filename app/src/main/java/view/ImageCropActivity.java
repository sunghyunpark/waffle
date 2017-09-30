package view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.soundcloud.android.crop.Crop;
import com.yssh.waffle.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * https://github.com/jdamcd/android-crop 참고
 */
public class ImageCropActivity extends AppCompatActivity {
    private ImageView resultView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_crop);
        resultView = (ImageView) findViewById(R.id.result_image);
        Crop.pickImage(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(result.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, result);
        }
    }

    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            /**
             * crop 라이브러리 사용 후 비트맵으로 추출 -> 업로드
             */
            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(getContentResolver(), Crop.getOutput(result));
                resultView.setImageBitmap(bm);
                Toast.makeText(getApplicationContext(), "Image Upload", Toast.LENGTH_SHORT).show();
                finish();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
