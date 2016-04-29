package io.iqube.kctgrad;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by Black Panther on 28-04-2016.
 */
public class GCMTokenRefreshListenerService extends InstanceIDListenerService {
    @Override
    public void onTokenRefresh() {
        Intent intent = new Intent(this,GCMRegistrationIntentService.class);
        startService(intent);
    }
}
