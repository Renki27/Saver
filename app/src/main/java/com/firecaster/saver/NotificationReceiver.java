package com.firecaster.saver;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

public class NotificationReceiver extends BroadcastReceiver {

    private long[] vibrate = {1000, 1000};
    Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    String enter = Integer.toString(R.string.enter);

    @Override
    public void onReceive(Context context, Intent intent) {
        dailyNotification(context, intent);


    }


    public void dailyNotification(Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        Bundle bundle = intent.getExtras();
        int id = bundle.getInt("ID");
        String title = bundle.getString("TITLE");
        String text = bundle.getString("TEXT");
        String enter = bundle.getString("ACTION");

        Intent notificationIntent = new Intent(context, AlarmNotification.class);
        notificationIntent.putExtra("SUBJECT", text);
        notificationIntent.putExtra("ID", id);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent notificationAlarm = PendingIntent.getActivity(context, id, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        //ongoing es para notificacion persistente
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)

                .setSmallIcon(R.drawable.ic_alarm_white)
                .setContentTitle(title)
                .setContentText(text)
                .setVibrate(vibrate)
                .setSound(alarmSound)
                .setLights(1, 1000, 1000)
                .addAction(R.drawable.ic_forward_black, enter, notificationAlarm)
                .setOngoing(true)
                .setAutoCancel(true);


        notificationManager.notify(id, builder.build());

    }

}
