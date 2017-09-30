package util;

/**
 * Created by SungHyun on 2017-09-30.
 */

public class CommonUtil {

    /**
     * user profile 이미지 랜덤
     * @return
     */
    public String GetUserProfileName(){
        int randomNum = (int)(Math.random() * 7)+1;
        String ImgName = "user_profile_img"+randomNum;
        return ImgName;
    }
}
