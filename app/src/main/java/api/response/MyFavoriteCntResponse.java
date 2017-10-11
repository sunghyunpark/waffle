package api.response;

/**
 * Created by SungHyun on 2017-10-10.
 * tab4 에서 갯수들...
 */

public class MyFavoriteCntResponse {
    private String myFavoriteCafeCnt;
    private String myCommentCafeCnt;
    private boolean error;
    private String error_msg;

    public String getMyFavoriteCafeCnt() {
        return myFavoriteCafeCnt;
    }

    public void setMyFavoriteCafeCnt(String myFavoriteCafeCnt) {
        this.myFavoriteCafeCnt = myFavoriteCafeCnt;
    }

    public String getMyCommentCafeCnt() {
        return myCommentCafeCnt;
    }

    public void setMyCommentCafeCnt(String myCommentCafeCnt) {
        this.myCommentCafeCnt = myCommentCafeCnt;
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
