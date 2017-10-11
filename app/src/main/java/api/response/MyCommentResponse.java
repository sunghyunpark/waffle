package api.response;

import java.util.ArrayList;

/**
 * Created by SungHyun on 2017-10-11.
 */

public class MyCommentResponse {
    private ArrayList<MyComment> commentList;
    private boolean error;
    private String error_msg;

    public ArrayList<MyComment> getCommentList() {
        return commentList;
    }

    public void setCommentList(ArrayList<MyComment> commentList) {
        this.commentList = commentList;
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

    public class MyComment{
        private String cafe_id;
        private String cafe_name;
        private String cafe_thumbnail;
        private String comment_text;
        private String nick_name;
        private String profile_img;
        private String profile_img_thumb;
        private String created_at;

        public String getCafe_id() {
            return cafe_id;
        }

        public void setCafe_id(String cafe_id) {
            this.cafe_id = cafe_id;
        }

        public String getCafe_name() {
            return cafe_name;
        }

        public void setCafe_name(String cafe_name) {
            this.cafe_name = cafe_name;
        }

        public String getCafe_thumbnail() {
            return cafe_thumbnail;
        }

        public void setCafe_thumbnail(String cafe_thumbnail) {
            this.cafe_thumbnail = cafe_thumbnail;
        }

        public String getComment_text() {
            return comment_text;
        }

        public void setComment_text(String comment_text) {
            this.comment_text = comment_text;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getProfile_img() {
            return profile_img;
        }

        public void setProfile_img(String profile_img) {
            this.profile_img = profile_img;
        }

        public String getProfile_img_thumb() {
            return profile_img_thumb;
        }

        public void setProfile_img_thumb(String profile_img_thumb) {
            this.profile_img_thumb = profile_img_thumb;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

    }
}
