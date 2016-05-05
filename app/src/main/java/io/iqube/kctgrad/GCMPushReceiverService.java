package io.iqube.kctgrad;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GcmListenerService;

import io.realm.Realm;

/**
 * Created by Black Panther on 28-04-2016.
 */
public class GCMPushReceiverService extends GcmListenerService {

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        sendNotification(message);
    }

    private void sendNotification(String message){




        Realm realm=Realm.getDefaultInstance();
        realm.beginTransaction();
        io.iqube.kctgrad.model.Notification not=realm.createObject(io.iqube.kctgrad.model.Notification.class);
        not.setMessage(message);

        realm.commitTransaction();
        realm.close();


            Intent intent = new Intent(this,DrawerActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            int requestCode =0;
            PendingIntent pendingIntent = PendingIntent.getActivity(this,requestCode,intent,PendingIntent.FLAG_ONE_SHOT);

            Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder noBuilder = new NotificationCompat.Builder(this).setTicker("Graduation 2K16")
                    .setSmallIcon(R.mipmap.ic_launcher).setContentTitle("Graduation 2K16:")
                    .setContentText(message).setAutoCancel(true).setContentIntent(pendingIntent).setVisibility(Notification.VISIBILITY_PUBLIC).setSound(sound);

            NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, noBuilder.build());
        }


    }

