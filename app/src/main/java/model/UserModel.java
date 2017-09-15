package model;

/**
 * Created by SungHyun on 2017-09-15.
 * User Model
 * - uid
 * - nick_name
 * - email
 * - fb_id
 * - created_at
 * - profile_img
 * - profile_img_thumb
 * - intro
 */

public class UserModel {
    private String uid;
    private String nick_name;
    private String email;
    private String fb_id;
    private String created_at;
    private String profile_img;
    private String profile_img_thumb;
    private String intro;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFb_id() {
        return fb_id;
    }

    public void setFb_id(String fb_id) {
        this.fb_id = fb_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
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

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

}
