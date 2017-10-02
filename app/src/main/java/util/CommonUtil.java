package util;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by SungHyun on 2017-09-30.
 */

public class CommonUtil {

    /**
     * user profile 이미지 랜덤
     * @return
     */
    public String MakeImageName(String uid){
        String imageName = "";
        String timeStamp = "";
        String random_str = "";
        Random random = new Random();

        for(int i=0;i<4;i++){
            random_str += String.valueOf(random.nextInt(10));
        }
        timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        imageName = timeStamp+random_str+uid;
        return imageName;
    }

    public int exifOrientationToDegrees(int exifOrientation)
    {
        if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_90)
        {
            return 90;
        }
        else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_180)
        {
            return 180;
        }
        else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_270)
        {
            return 270;
        }
        return 0;
    }

    public Bitmap rotate(Bitmap bitmap, int degrees)
    {
        if(degrees != 0 && bitmap != null)
        {
            Matrix m = new Matrix();
            m.setRotate(degrees, (float) bitmap.getWidth() / 2,
                    (float) bitmap.getHeight() / 2);

            try
            {
                Bitmap converted = Bitmap.createBitmap(bitmap, 0, 0,
                        bitmap.getWidth(), bitmap.getHeight(), m, true);
                if(bitmap != converted)
                {
                    bitmap.recycle();
                    bitmap = converted;
                }
            }
            catch(OutOfMemoryError ex)
            {
                // 메모리가 부족하여 회전을 시키지 못할 경우 그냥 원본을 반환합니다.
            }
        }
        return bitmap;
    }
}
