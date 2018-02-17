package com.galukhin.pendingintent;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "Blya, " + MainActivity.class.getSimpleName();

    Button btnPending; // Для ожидающего интента

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // TODO: 017 17 Feb 18 Попробовать PendingIntent. Пока что не ясно.
        /*PendingIntent - для запуска */
        btnPending = findViewById(R.id.btn_pending);
        btnPending.setOnClickListener(v -> {
            // Сам интент, который будет выполняться
            Intent intent = new Intent(this, SecondaryActivity.class);
            /*the activity will be started outside of the context of an existing activity, so you must use the Intent.FLAG_ACTIVITY_NEW_TASK launch flag in the Intent*/
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // PendingIntent, в который упаковывается реальный интент
            PendingIntent sender = PendingIntent.getActivity(this, 123, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            try {
                sender.send();
            } catch (PendingIntent.CanceledException e) {
            }
        });

    }
}


/*PENDING INTENT

Вся разница заключается в правах доступа к твоему приложению.

PendingIntent - обертка, которая позволяет стороннему приложению выполнять определенный код (твоего приложения) с правами которые определены для твоего же приложения.

Если в стороннее приложение передать простой Intent то он будет выполняться с правами которые имеет само приложения.
* - является оболочкой, в которую заключается объект Intent
* - в основном для разрешения внешним аппам использовать содержащийся внутри Intent, как если бы он
* исполнялся из процесса моего аппа
* - использование: объявление объекта Intent, который должен исполняться:
*   + когда пользователь выполняет действие с вашим уведомлением (NotificationManager системы
*   Android исполняет Intent)
*
 *  + когда пользователь выполняет действие с вашим виджетом приложения (приложение главного экрана
*   исполняет Intent)
*
*   + в указанное время в будущем (AlarmManager системы Android исполняет Intent)
*
* - при использовании ожидающего объекта Intent ваше приложение не будет исполнять объект Intent
* вызовом, например, startActivity(). Вместо этого нужно объявить требуемый тип компонента при
* создании PendingIntent вызовом соответствующего метода-создателя:
*   + PendingIntent.getActivity() для Intent, который запускает Activity
*   + PendingIntent.getService() для Service
*   + PendingIntent.getBroadcast() для BroadcastReceiver
*   (Если только ваш апп не принимает ожидающие Intent от других аппов, указанные выше методы
*   создания PendingIntent являются единственными методами PendingIntent, которые вам понадобятся.)*/


/*МЕТОДЫ PendingIntent
*
*
*
* */