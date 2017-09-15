package database;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by SungHyun on 2017-09-15.
 */

public class RealmConfig {
    public RealmConfiguration UserRealmVersion(Context context){

        Realm.init(context);    //realm 초기화
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("User.realm")
                .schemaVersion(0)
                //.deleteRealmIfMigrationNeeded()
                .migration(new Migration())
                .build();

        return config;
    }
}
