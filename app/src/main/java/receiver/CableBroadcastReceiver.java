package receiver;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.ihm15.project.phonetection.CardViewActivity;
import com.ihm15.project.phonetection.Data;
import com.ihm15.project.phonetection.R;


/**
 * Created by Manon on 07/11/2015.
 */
public class CableBroadcastReceiver extends BroadcastReceiver {

    private String LOG_TAG_BROADCAST_SERVICE = "CableBroadcastReceiver";
    public static final int CHARGER_NOTIFICATION_ID = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        Data.getInstance(context);
        if (intent.getAction().equals("android.intent.action.ACTION_POWER_CONNECTED")) {
            Log.e(LOG_TAG_BROADCAST_SERVICE, "Je suis connecté ! ");
            //Toast.makeText(context, "Je suis connecté !", Toast.LENGTH_SHORT).show();
            activeNotification(context);
        }else if (intent.getAction().equals("android.intent.action.ACTION_POWER_DISCONNECTED")) {
            Log.e(LOG_TAG_BROADCAST_SERVICE,"Je suis déconnecté !");
            //Toast.makeText(context,"Je suis deconnecté ! ", Toast.LENGTH_SHORT).show();
            if (Data.isCableModeActivate()) {
                Intent i = new Intent();
                i.setClassName("com.ihm15.project.phonetection",
                        "com.ihm15.project.phonetection.CardViewActivity");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(Data.EXTRA_WITH_ALARM, true);
                i.putExtra(Data.EXTRA_MODE, Data.CHARGER_MODE);
                Data.setCableMode(false);
                context.startActivity(i);
            }
        }
    }

    private void activeNotification(Context context) {

        Intent showIntent = new Intent(context, CardViewActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context,0, showIntent,0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_phonelink_lock_white_36dp)
                        .setContentTitle(context.getString(R.string.app_name))
                        .setContentText(context.getString(R.string.notification_charger_mode))
                        .setContentIntent(contentIntent);

        Notification notification = mBuilder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_SOUND;

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

// mId allows you to update the notification later on.
        mNotificationManager.notify(CHARGER_NOTIFICATION_ID, notification);
// Creates an explicit intent for an Activity in your app

        /*Notification notification = new Notification(icon, text, time);
        Intent notificationIntent = new Intent(context,CardViewActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context,0,notificationIntent,0);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        notification = builder.setContentIntent(contentIntent)
                .setSmallIcon(icon).setTicker(tickerText).setWhen(when)
                .setAutoCancel(false).setContentTitle(title)
                .setContentText(text).build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        manager.notify(ID_NOTIFICATION, notification);*/
    }
}
