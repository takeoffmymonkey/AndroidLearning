package com.galukhin.pendingintent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/*Этот класс получает Intent из PendingIntent от NotificationManager или AlarmManager*/

public class Receiver extends BroadcastReceiver {

    private final String TAG = "Blya, " + Receiver.class.getSimpleName();

    @Override
    /*Метод вызывается, когда BroadcastReceiver получает трансляцию Intent*/
    public void onReceive(Context context, Intent intent) {
        // Здесь можно получить все экстра из оригинального интента
        // Создаем новый интент для запуска второй операции
        Intent i = new Intent(context, SecondaryActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
