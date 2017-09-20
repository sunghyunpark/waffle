package api.response;

import java.util.ArrayList;

/**
 * Created by SungHyun on 2017-09-20.
 */

public class CafeFeatureResponse {

    private ArrayList<String> cafe_etc_photo_list;
    private boolean error;
    private String error_msg;


    public ArrayList<String> getCafe_etc_photo_list() {
        return cafe_etc_photo_list;
    }

    public void setCafe_etc_photo_list(ArrayList<String> cafe_etc_photo_list) {
        this.cafe_etc_photo_list = cafe_etc_photo_list;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

}
