package io.iqube.kctgrad;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import io.realm.internal.Context;

/**
 * Created by Black Panther on 28-04-2016.
 */
public class GCMRegistrationIntentService extends IntentService {
    public static final String REGISTRATION_SUCCESS = "RegistrationSuccess";
    public static final String REGISTRATION_ERROR = "RegistrationError";

    public GCMRegistrationIntentService() {
        super("");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        registerGCM();
    }

    private void registerGCM() {
            Intent registrationComplete = null;
            String token = null;

        SharedPreferences gcm = getSharedPreferences("gcm", getApplicationContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = gcm.edit();

            try {
                InstanceID instanceId = InstanceID.getInstance(getApplicationContext());
                token = instanceId.getToken(getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                Log.w("GCMRegIntentService", "token:" + token);
                GcmPubSub pubSub = GcmPubSub.getInstance(this);
                String topic = "kct_grad";
                pubSub.subscribe(token, "/topics/" + topic, null);
                registrationComplete = new Intent(REGISTRATION_SUCCESS);
                registrationComplete.putExtra("token", token);
                registrationComplete.putExtra("topic", topic);
                editor.putString("reg", "yes");
                editor.apply();
            } catch (Exception e) {
                Log.w("GCMRegIntentService", "Registration error");
                registrationComplete = new Intent(REGISTRATION_ERROR);

            }
            LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
        }

    }



