package com.capedhorse.pocketnotes;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RealmConfig extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name("mynotes.db")
                .schemaVersion(0)
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    public static Realm newRealmInstance() {
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        return Realm.getInstance(configuration);
    }

}
