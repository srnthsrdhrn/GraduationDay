package io.iqube.kctgrad;

import android.app.Application;
import android.support.multidex.MultiDexApplication;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.firebase.messaging.FirebaseMessaging;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by raja sudhan on 5/2/2016.
 */
public class KCTApplication  extends MultiDexApplication{

    public static String PREF_NAME="name",PREF_DEPT="dept",PREF_ROLL="roll",PREF_SESSION="session",SHARED_PREF_NAME="user";

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        Fresco.initialize(this);
        RealmConfiguration config = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(config);
        FirebaseMessaging.getInstance().subscribeToTopic("global");

    }
}
