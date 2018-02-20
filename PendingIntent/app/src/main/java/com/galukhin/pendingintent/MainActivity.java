package com.galukhin.pendingintent;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "Blya, " + MainActivity.class.getSimpleName();

    Button btnPendingActivity; // Для запуска операции напрямую
    Button btnPendingAlarmActivity; // Для запуска операции через AlarmManager
    Button btnPendingAlarmBroadcastActivity; // Для запуска операции через AlarmManager + BroadcaseReciever
    Button btnPendingNotificationActivity; // Для запуска операции через Notification


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Для простого запуска операции внутри аппа. Не знаю зачем :) */
        btnPendingActivity = findViewById(R.id.btn_pending_activity);
        btnPendingActivity.setOnClickListener(v -> {
            // Сам интент, который будет выполняться
            Intent intent = new Intent(this, SecondaryActivity.class);
            // Операция будет запущена вне контекста существующего аппа, поэтому нужен такой флаг
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // PendingIntent, в который упаковывается реальный интент
            /* Request code - это своего рода ключи, чтобы отличать один PendingIntent от других при
            необходимости.
            * FLAG_ONE_SHOT - данный PendingIntent можно использовать только 1 раз*/
            PendingIntent sender = PendingIntent.getActivity(this, 123, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            try {
                sender.send();
            } catch (PendingIntent.CanceledException e) {
                // Вызывается, если PendingIntent больше не позволяет пересылать через него
                // дополнительные intents
                Log.e(TAG, e.toString());
            }
        });


        /* Для запуска операции через AlarmManager*/
        btnPendingAlarmActivity = findViewById(R.id.btn_pending_alarm_activity);
        btnPendingAlarmActivity.setOnClickListener(v -> {
            Intent intent = new Intent(this, SecondaryActivity.class);

            PendingIntent pIntent = PendingIntent.
                    getActivity(this, 0, intent, 0);

            AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
            am.set(AlarmManager.RTC, System.currentTimeMillis() + 2000, pIntent);
        });



        /* Для запуска операции через BroadcastReceiver, который запускается через AlarmManager*/
        btnPendingAlarmBroadcastActivity = findViewById(R.id.btn_pending_alarm_broadcast_activity);
        btnPendingAlarmBroadcastActivity.setOnClickListener(v -> {
            Intent intent = new Intent(this, Receiver.class);

            PendingIntent pIntent = PendingIntent.
                    getActivity(this, 0, intent, 0);

            AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
            am.set(AlarmManager.RTC, System.currentTimeMillis() + 2000, pIntent);
        });


        /* Для запуска операции через уведомление */
        btnPendingNotificationActivity = findViewById(R.id.btn_pending_notification_activity);
        btnPendingNotificationActivity.setOnClickListener(v -> {
            Intent intent = new Intent(this, Receiver.class);

            PendingIntent pIntent = PendingIntent.
                    getBroadcast(this, 0, intent, 0);

            //Создание уведомления
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("My notification")
                            .setContentText("Hello World!");
            mBuilder.setContentIntent(pIntent);

            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            nm.notify(0, mBuilder.build());
        });


        // TODO: 020 20 Feb 18 Запуск службы


        // TODO: 020 20 Feb 18 Работа с виджетом

    }
}

/* public final class PendingIntent extends Object implements Parcelable*/


/* PENDING INTENT
* - является оболочкой, в которую заключается объект Intent
*
* - в основном для разрешения внешним аппам использовать содержащийся внутри Intent, как если бы он
* исполнялся из процесса моего аппа
*
* - т.е. когда я передаю PendingIntent другому аппу, я предоставляю ему возможность и полномочия
* отправлять Intent от моего имени
*
* - eсли процесс с моим аппом будет убит, PendingIntent все равно сохраняется для использования из
* других процессов, которым он был выдан*/


/* ТИПИЧНАЯ ОШИБКА:
* - создание нескольких объектов PendingIntent с Intents, в которых отличаются "extra", и ожидание
* получить разные PendingIntent каждый раз. Этого не произойдет. Если 2 объекта Intent равны по
* методу Intent.filterEquals, который используется для проверки, тогда получается тот же
* PendingIntent для них обоих.
*
* - т.е. filterEquals() не учитывает данные в Extra, поэтому 2 intents могут быть равны с разными
* экстра */


/* ПРИМЕРЫ ИСПОЛЬЗОВАНИЯ (объявление объекта Intent, который должен исполняться):
*   + когда пользователь выполняет действие с вашим уведомлением (NotificationManager системы
*   Android исполняет Intent)
*
*   + когда пользователь выполняет действие с вашим виджетом приложения (приложение главного экрана
*   исполняет Intent)
*
*   + в указанное время в будущем (AlarmManager системы Android исполняет Intent)*/


/* ОСОБЕННОСТИ СОЗДАНИЯ ОБЪЕКТА
* - при использовании ожидающего объекта Intent ваше приложение не будет исполнять объект Intent
* вызовом, например, startActivity(). Вместо этого нужно объявить требуемый тип компонента при
* создании PendingIntent вызовом соответствующего метода-создателя:
*   + PendingIntent.getActivity() для Intent, который запускает Activity
*   + PendingIntent.getService() для Service
*   + PendingIntent.getBroadcast() для BroadcastReceiver
*
* - Если только ваш апп не принимает ожидающие Intent от других аппов, указанные выше методы
* создания PendingIntent являются единственными методами PendingIntent, которые вам понадобятся.)*/


/*ФЛАГИ
* FLAG_CANCEL_CURRENT - если описанный PendingIntent уже существует, то текущий должен быть отменен
* перед созданием нового
*
* FLAG_IMMUTABLE - созданный PendingIntent должен быть неизменяемым
*
* FLAG_NO_CREATE - если описанный PendingIntent еще не существует, то просто вернуть null вместо его
* создания
*
* FLAG_ONE_SHOT - данный PendingIntent можно использовать только 1 раз
*
* FLAG_UPDATE_CURRENT - если описанный PendingIntent уже существует, то оставить его, но заменить
* его экстра данные тем, что в этом новом Intent.*/


/*ОСНОВНЫЕ МЕТОДЫ PENDINGINTENT
* - cancel(): отменить текуший активный PendingIntent
*
* - getActivities(Context context, int requestCode, Intent[] intents, int flags, Bundle options):
* как getActivity(), но позволяет подать на вход массив Intents
*
* - getActivity(Context context, int requestCode, Intent intent, int flags): получить PendingIntent,
* который начнет новую операцию, как вызов Context.startActivity(Intent)
*
* - getBroadcast(Context context, int requestCode, Intent intent, int flags): получить PendingIntent,
 * который выполнит трансляцию broadcast, как вызов Context.sendBroadcast()
 *
* - getForegroundService(Context context, int requestCode, Intent intent, int flags): получить
* PendingIntent, который начнет службу на переднем фоне, как вызов Context.startForegroundService().
*
* - getService(Context context, int requestCode, Intent intent, int flags): получить PendingIntent,
* который начнет службу, как вызов Context.startService()
*
* - send(): выполнить операцию, ассоциированную с PendingIntent.*/