package api.response;

import java.util.ArrayList;

import model.RecentCommentModel;

/**
 * Created by SungHyun on 2017-10-30.
 * WAFFLE, NOW
 */

public class RecentAllCommentResponse {
    private ArrayList<RecentCommentModel> recentCommentList;
    private String last_comment_id;
    private boolean error;
    private String error_msg;

    public ArrayList<RecentCommentModel> getRecentCommentList() {
        return recentCommentList;
    }

    public void setRecentCommentList(ArrayList<RecentCommentModel> recentCommentList) {
        this.recentCommentList = recentCommentList;
    }

    public String getLast_comment_id() {
        return last_comment_id;
    }

    public void setLast_comment_id(String last_comment_id) {
        this.last_comment_id = last_comment_id;
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
