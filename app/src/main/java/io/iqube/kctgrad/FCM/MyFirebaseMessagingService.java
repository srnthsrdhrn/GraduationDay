package io.iqube.kctgrad.FCM;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import io.iqube.kctgrad.Home.DrawerActivity;
import io.iqube.kctgrad.R;

import static io.iqube.kctgrad.KCTApplication.PREF_SESSION;
import static io.iqube.kctgrad.KCTApplication.SHARED_PREF_NAME;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    int session,user_session;
    Map<String,String> data;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if(remoteMessage.getData().size()>0) {

            SharedPreferences sp = getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);

            user_session = sp.getInt(PREF_SESSION,0);

            data=remoteMessage.getData();
            session = Integer.parseInt(data.get("session"));

            if (session == user_session) {
                buildNotification(data.get("title"), data.get("message"));
            }
        }
    }


    public void buildNotification(String title,String message)
    {
        Intent intent = new Intent(this, DrawerActivity.class);
        intent.putExtra("type","notification");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setContentTitle(title);
        notificationBuilder.setContentText(message);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        notificationBuilder.setLargeIcon(BitmapFactory.decodeResource(getApplication().getResources(),
            R.mipmap.ic_launcher));
        notificationBuilder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notificationBuilder.build());
    }
}
