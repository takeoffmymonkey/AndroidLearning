package com.galukhin.parcelablesandbundles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;


/*- для преодоления границ процессов

- для IPC/Binder транзакций

- для передачи данных между активностями и интентами

- для сохранение состояния перехода при изменениях конфигурации

- Parcel - не механизм сериализации общего назначения - никогда не хранить данные Parcel на диске или отправлять по сети
* */


/*ПЕРЕСЫЛКА ДАННЫХ МЕЖДУ ОПЕРАЦИЯМИ
* - при создании объекта интент - метод putExtra(java.lang.String, java.lang.String)

- класс Bundle высокооптимизирован для преобразования передаваемых данных в пакетах

- при передаче данных через интент ограничивать их до нескольких кб, иначе TransactionTooLargeException

- до Android 7.0 (API level 24) TransactionTooLargeException - только в логкэте

- если нужно передать сложный или составной объект - кастомный класс должен имплементировать Parcelable
*
*
* */


/*ПЕРЕСЫЛКА ДАННЫХ МЕЖДУ ПРОЦЕССАМИ
*
*- не рекомендуется использовать кастомный parcelable - нужно удостовериться, что в обоих аппах та же версия кастомного класса

- система не поймет кастомный класс - ей не слать (напр. в AlarmManager)

- лимит буфера транзакций Binder 1MB - на все транзакции для процесса, который выполняется - иначе TransactionTooLargeException

- до Android 7.0 (API level 24) TransactionTooLargeException - только в логкэте.
*
* */

public class MainActivity extends AppCompatActivity {

    private final String TAG = "Blya, " + MainActivity.class.getSimpleName();

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        button.setOnClickListener(v -> {
            // This is an explicit intent as it has a known target
            Intent intent = new Intent(MainActivity.this, SecondaryActivity.class);

            /*I may create a Bundle by myself, put extras there and add it to intent
            or I may call putExtras and it creates a Bundle and adds extras to it automatically*/
            intent.putExtra("button name", button.toString());
            intent.putExtra("button id", button.getId());
            startActivity(intent);
        });

    }
}
