package database;

import android.content.Context;

import database.model.UserVO;
import io.realm.Realm;
import model.UserModel;

/**
 * Created by SungHyun on 2017-09-15.
 */

public class RealmUtil {

    /**
     * User Data 를 Realm 에 Insert
     * @param context
     * @param uid
     * @param email
     * @param nick_name
     * @param fb_id
     * @param created_at
     * @param profile_img
     * @param profile_img_thumb
     * @param intro
     */
    public void InsertDB(Context context, String uid, String email, String nick_name, String fb_id, String created_at, String profile_img, String profile_img_thumb, String intro){
        Realm mRealm;
        RealmConfig realmConfig;
        realmConfig = new RealmConfig();
        mRealm = Realm.getInstance(realmConfig.UserRealmVersion(context));

        mRealm.beginTransaction();
        UserVO userVO = new UserVO();
        userVO.setNo(1);
        userVO.setUid(uid);
        userVO.setEmail(email);
        userVO.setNick_name(nick_name);
        userVO.setFb_id(fb_id);
        userVO.setCreated_at(created_at);
        userVO.setProfile_img(profile_img);
        userVO.setProfile_img_thumb(profile_img_thumb);
        userVO.setIntro(intro);

        mRealm.copyToRealmOrUpdate(userVO);
        mRealm.commitTransaction();
    }

    public void UpdateUserProfile(Context context, String uid, String profile, String profileThumb){
        Realm mRealm;
        RealmConfig realmConfig;
        realmConfig = new RealmConfig();

        mRealm = Realm.getInstance(realmConfig.UserRealmVersion(context));
        UserVO userVO = mRealm.where(UserVO.class).equalTo("no",1).findFirst();
        try{
            mRealm.beginTransaction();
            userVO.setProfile_img(profile);
            userVO.setProfile_img_thumb(profileThumb);
        }catch (Exception e){

        }finally {
            mRealm.commitTransaction();
            RefreshUserInfo(context, uid);
        }
    }

    /**
     * User Info(nickName, intro)
     * @param context
     * @param nickName
     * @param intro
     */
    public void UpdateUserEtcInfo(Context context, String uid, String nickName, String intro){
        Realm mRealm;
        RealmConfig realmConfig;
        realmConfig = new RealmConfig();

        mRealm = Realm.getInstance(realmConfig.UserRealmVersion(context));
        UserVO userVO = mRealm.where(UserVO.class).equalTo("no",1).findFirst();
        try{
            mRealm.beginTransaction();
            userVO.setNick_name(nickName);
            userVO.setIntro(intro);
        }catch (Exception e){

        }finally {
            mRealm.commitTransaction();
            RefreshUserInfo(context, uid);
        }
    }

    public void RefreshUserInfo(Context context, String uid){
        Realm mRealm;
        RealmConfig realmConfig;
        realmConfig = new RealmConfig();
        mRealm = Realm.getInstance(realmConfig.UserRealmVersion(context));

        UserVO userVO = mRealm.where(UserVO.class).equalTo("uid",uid).findFirst();

        UserModel.getInstance().setUid(userVO.getUid());
        UserModel.getInstance().setEmail(userVO.getEmail());
        UserModel.getInstance().setNick_name(userVO.getNick_name());
        UserModel.getInstance().setFb_id(userVO.getFb_id());
        UserModel.getInstance().setProfile_img(userVO.getProfile_img());
        UserModel.getInstance().setProfile_img_thumb(userVO.getProfile_img_thumb());
        UserModel.getInstance().setIntro(userVO.getIntro());
        UserModel.getInstance().setCreated_at(userVO.getCreated_at());

    }


}
