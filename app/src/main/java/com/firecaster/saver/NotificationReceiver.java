package com.firecaster.saver;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

public class NotificationReceiver extends BroadcastReceiver{


    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Bundle bundle = intent.getExtras();
        int id = bundle.getInt("ID");
        String title = bundle.getString("TITLE");
        String text = bundle.getString("TEXT");


        Intent repeatingIntent = new Intent(context, RepeatingActivity.class);
        repeatingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, repeatingIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_action_schedule)
                .setContentTitle(title)
                .setContentText(text)
                .setAutoCancel(true);


        notificationManager.notify(id, builder.build());

    }
}
