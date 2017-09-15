package database;

import android.content.Context;

import database.model.UserVO;
import io.realm.Realm;

/**
 * Created by SungHyun on 2017-09-15.
 */

public class RealmUtil {

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
}
