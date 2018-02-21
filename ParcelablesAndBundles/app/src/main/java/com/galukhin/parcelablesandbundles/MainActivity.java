package com.galukhin.parcelablesandbundles;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    private final String TAG = "Blya, " + MainActivity.class.getSimpleName();

    final static String STRING_DATA = "string";
    final static String INT_DATA = "integer";
    final static String OBJECT_DATA = "object";


    Button btnSimpleData; // Для пересылки примитивных данных
    Button btnObject; // Для пересылки объектов

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*Пересылка примитивных данных между операциями*/
        btnSimpleData = findViewById(R.id.btn_simple_data);
        btnSimpleData.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SecondaryActivity.class);

            /* Можно создать Bundle и в него ложить простые данные */
            Bundle bundle = new Bundle();
            bundle.putInt(INT_DATA, 111);
            intent.putExtras(bundle); // !!! Не забыть добавить Bundle в Intent

            /* Или можно ложить простые данные сразу в Intent - Bundle создается и добавляется
             * автоматически */
            intent.putExtra(STRING_DATA, "Простая строка");

            startActivity(intent);
        });


        /*Пересылка объекта между операциями*/
        btnObject = findViewById(R.id.btn_object);
        btnObject.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SecondaryActivity.class);

            /* Передаваемый объект должен имплементировать интерфейс Parcelable*/
            MyParcelable myParcelable = new MyParcelable();
            intent.putExtra(OBJECT_DATA, myParcelable);

            startActivity(intent);
        });


        // TODO: 020 20 Feb 18 Пересылка данных между процессами
    }
}


/* ДЛЯ ЧЕГО НУЖНО ПЕРЕДАВАТЬ ДАННЫЕ
 * - для преодоления границ процессов (напр. с IPC/Binder транзакциями)
 * - для передачи данных между активностями и интентами
 * - для сохранения состояния перехода при изменениях конфигурации*/


/*ПЕРЕСЫЛКА ДАННЫХ МЕЖДУ ОПЕРАЦИЯМИ
* - для простых данных - класс Bundle или метод putExtra() у Intent
*
* - для объектов - должен имплементировать Parcelable
*
* - при передаче данных через интент ограничивать их до нескольких кб, иначе
* TransactionTooLargeException
*
* - до Android 7.0 (API level 24) TransactionTooLargeException - только в логкэте*/


/*ПЕРЕСЫЛКА ДАННЫХ МЕЖДУ ПРОЦЕССАМИ
* - не рекомендуется использовать кастомный Parcelable - нужно удостовериться, что в обоих аппах та
* же версия кастомного класса
*
* - система не поймет кастомный класс - ей не слать (напр. в AlarmManager)
*
* - лимит буфера транзакций Binder 1MB - на все транзакции для процесса, который выполняется - иначе
* TransactionTooLargeException
*
* - до Android 7.0 (API level 24) TransactionTooLargeException - только в логкэте. */


/* BUNDLE
* - extends BaseBundle implements Cloneable, Parcelable
* - Простая привязка строк к разным Parcelable значениям
* - высокооптимизирован для преобразования передаваемых данных в пакетах*/


/* ОСНОВНЫЕ МЕТОДЫ BUNDLE
* - clear(): удаляет все элементы из маппинга этого Bundle
*
* - clone(): клонирует текущий Bundle
*
* - get...(String key, ... defaultValue): возвращает значение, ассоциированное с указанным ключом,
* либо дефолтное значение, если желаемого типа по указанному ключу не существует
*
* - put...(String key, ... value): Вставляет значение в маппинг этого Bundle, заменяя существующее
* по указанному ключу
*
* - putAll(Bundle bundle): вставляет все маппинги с указанного Bundle в данный
*
* - remove(String key): удаляет запись по указанному ключу из маппинга для этого Bundle*/


/* Parcel и Parcelable в классе MyParcelable*/