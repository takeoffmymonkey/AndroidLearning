package com.galukhin.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "Blya, " + MainActivity.class.getSimpleName();

    Button button0; // Restart this screen
    Button button1; // Go to screen 2
    Button button2; // Go to screen 3
    Button button3; // Change text button
    Button button4; // Call dialog window
    TextView textView;


    @Override
    /* - Выполняется при создании операции
    *
    * - Для базовой логики, которая должна происходить единожды для всего цикла
    * (напр, связать данные со списками, инициализировать фоновые потоки,
    * инициализировать переменные класса, настроить UI)
    *
    * - получает параметр savedInstanceState - объект Bundle с сохраненным состоянием операции
    * (если не было операции ранее - null)*/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate()");
        setContentView(R.layout.activity_main);
        setTitle("Main Activity");

        button0 = findViewById(R.id.button0);
        button0.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, MainActivity.class));
        });

        button1 = findViewById(R.id.button1);
        button1.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, SecondActivity.class));
        });

        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ThirdActivity.class));
        });

        textView = findViewById(R.id.textView);
        button3 = findViewById(R.id.button3);
        button3.setOnClickListener(v -> {
            textView.setText("New text");
        });


        button4 = findViewById(R.id.button4);
        button4.setOnClickListener(v -> {
            Log.i (TAG, "calling test dialog");
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Test message")
                    .setMessage("Just a test for lifecycle")
                    .setCancelable(false)
                    .setNegativeButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        });
    }

    @Override
    /* - НЕ РЕКОМЕНДУЕТСЯ ГАЙДЛАЙНАМИ
    *
    * - Используй ТОЛЬКО, если восстановление активности очень накладное
    *
    * - Здесь происходит самостоятельная обработка изменений конфигурации
    *
    * - Выполняется только, если указано в манифесте android:configChanges="orientation|screenSize"
    *
    * - Другие методы жизненного цикла не вызываются*/
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i(TAG, "onConfigurationChanged()");

        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // напр. сменить картинку
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // что-то другое
        }
    }

    @Override
    /* - Выполняется, если пользователь возвращается в операцию после onStop()
    *
    * - если вызывается новая копия операции, которая сейчас в фоне - onRestart() не вызывается,
    * только onStart() и onResume()*/
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart()");
    }

    @Override
    /* - запускается, когда операция входит в состояние Started
    *
    * - подготавливает операцию для вывода на передний план
    * (напр, инициализация кода UI,
    * регистрация  BroadcastReceiver, который отслеживает изменения, отражаемые в UI)

    * - пользователь еще не может взаимодействовать с операцией*/
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart()");
    }

    @Override
    /* - восстановить состояние активности после уничтожения можно из Bundle
    * - onCreate() и onRestoreInstanceState() получают тот же Bundle
    * - чтобы узнать, было ли сохранение - проверка Bundle на null
    * - если null - система создает новую копию операции вместо воссоздания той, что была уничтожена
    * - onRestoreInstanceState() вызывается только, если было сохранение (не нужна проверка на null)
    * - сначала всегда вызов суперкласса*/
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState()");

        textView.setText(savedInstanceState.getString("textview text"));
    }

    @Override
    /* - запускается, когда операция входит в состояние Resumed и апп выводится на передний план
    *
    * - операция становится в конце стека
    *
    * - в этом состоянии пользователь взаимодействует с аппом, и пока оно в фокусе
    *
    * - если уходит из фокуса (напр, звонок, другая операция) - переход в состояние Paused
    * и запуск onPause()
    *
    * - обратно вызывается когда возвращается в фокус
    *
    * - нужно имплементировать для инициализации компонентов при предшествующем освобождении их
    * при onPause() (напр. анимация)*/
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");
    }

    @Override
    /*- при первых признаках ухода юзера из активности:
    *       + новая операция
    *       + звонок и т.д.
    *       + полупрозрачная операция (диалоговое окно)
    *       + с Android 7.0 (API level 24) многооконный режим для нескольких аппов
    *
    * - для приостановки напр, анимаций, проигрывания звуков, освобождение системных ресурсов
    * (приемники, сенсоры, камера) - для экономии напр. батареи
    *
    * - НЕ для сохранения состояния, обращений по сети или с дб,
    * т.к. слишком быстро выполняется (нужно в onStop())
    *
    * - завершение метода не означает выход из состояния Paused - в нем оно остается
    * до возобновления операции или ее полного исчезновения для юзера
    *
    * - если операция вернулась в состояние Resumed из Paused, она остается в памяти
    * и используется снова (т.е. не нужно реинициализировать компоненты)
    *
    * - здесь можно использовать метод isFinishing(), чтобы узнать завершается ли полностью
    * операция (напр. кто-то вызвал finish() или кто-то запросил завершение)*/
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()");
        Log.i(TAG, "activity is finishing: " + isFinishing());
    }

    @Override
    /* - если нужно сохранить что-то кроме данных о состоянии иерархии объектов View операции
    * (сохраняются автоматически в Bundle) при уничтожении
    *
    * - во время остановки операции вызывается onSaveInstanceState() - для сохранения коллекции пар
    * ключ-значение
    *
    * - имплементировать метод нужно ПОСЛЕ onPause() и ДО onStop().
    * Не имплементировать метод в onPause().
    *
    * - сохранение данных, напр. в дб - когда операция в фоне. Если такой возможности нет,
    * то в onStop().
    *
    * - всегда нужно вызывать суперкласс, чтобы он мог сохранить состояние иерархии view*/
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState()");
        outState.putString("textview text", textView.getText().toString());
    }

    @Override
    /* - когда операция не видна - она входит в состояние Stopped
    * (напр., новая операция, закрывающая весь экран)
    *
    * - необходимо освобождать почти все ресурсы, которые не нужны юзеру, когда он их не использует
    * (напр, unregister BroadcastReceiver для UI)
    *
    * - освобождать ресурсы, которые могут привести к утечке памяти - система может убить процесс,
    * в котором выполняется апп без вызова onDestroy()!
    *
    * - в состоянии Stopped операция остается в памяти, но не прикреплена к менеджеру окон.
    * Система также следит за текущим состоянием каждого объекта View в лейауте - позиция скролла
    * в списке или если юзер ввел текст в EditText, его контент не нужно сохранять и восстанавливать
    *
    * - система может вызвать метод, когда операция перестала выполняться и скоро будет уничтожена
    * для освобождения памяти - состояние объектов View сохраняется в Bundle и восстанавливается,
    * когда юзер возвращается в операцию
    *
    * - если вызывается новая копия операции, которая сейчас в фоне - onRestart() не вызывается,
    * только onStart() и onResume()*/
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop()");
    }

    @Override
    /* - когда операция перестала работать:
    *       + кто-то вызвал finish()
    *       + система временно уничтожает процесс с операцией для освобождения места
    *       + может вызываться системой при смене ориентации, за чем немедленно следует onCreate()
    *       + апп давно не используется
    *       + пользователь нажал Back
    *
    * - узнать каким методом уничтожается - isFinishing()
    *
    * - освобождает все неосвобожденные ресурсы (напр. в onStop())*/
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }

    @Override
    public void onBackPressed() {
        Log.i(TAG, "onBackPressed()");
        super.onBackPressed();
    }

}


/* Каждая операция должна быть зарегистрирована в манифесте
* <activity android:name="com.galukhin.activity.MainActivity">*/


/* После публикации не менять названия активности - поломается, напр, ярлык*/


/* Родительская операция должна иметь те же права, что и подоперация (для запуска подоперации)*/


/* ИЕРАРХИЯ:
* > MainActivity
* > AppCompatActivity - если нужен MaterialDesign (и Target < API 21)
* > FragmentActivity - если нужны вложенные Fragments
* > BaseFragmentActivityApi16 (Honeycomb)
* > BaseFragmentActivityApi14 (Eclair)
* > SupportActivity (Donut)
* > Activity */


/* у 95% устройств API > 15-16 */


/* ЖИЗНЕННЫЙ ЦИКЛ:
* 1. onCreate()
* 2. onStart() // + onRestoreInstanceState() если была ротация
* 3. onResume()
* 4. > НАВИГАЦИЯ В ДРУГУЮ операция
* 5. onPause(), onSaveInstanceState()
* 6. onCreate(), onStart(), onResume() // для другой активности
* 7. onStop()
* 8. > НАЖАЛИ BACK
* 9. onPause(), onSaveInstanceState() // для другой активности
* 10. onRestart()
* 11. onStart()
* 12. onResume()
* 13. onStop() для другой активности
* 14. onDestroy() для другой активности
* 15. > НАЖАЛИ HOMESCREEN ИЛИ СНОВА BACK
* 16. onPause(), onSaveInstanceState(), onStop() // для обоих вариантов
* 17. onDestroy() (только для BACK) */


/*ДРУГАЯ ОПЕРАЦИЯ ИЛИ ДИАЛОГОВОЕ ОКНО (!КАК ОПЕРАЦИЯ) НА ПЕРЕДНЕМ ПЛАНЕ. КНОПКИ OVERVIEW И HOME
* - если частично закрывает - переводит операцию в состояние Paused, потом обратно в onResume()
* - !!! касается только диалога, который является другой операцией
* - !!! т.е. появляется новая операция в стеке
* - если полностью - вызываются onPause() и onStop(), потом обратно onRestart(), onStart(), onResume()
* - если новая копия операции, которая на фоне - onRestart() не вызывается, только onStart() и onResume()
* - кнопки Overview или Home ведут себя, будто текущая операция полностью перекрывается */


/* КНОПКА BACK
* - вызов onPause(), onStop(), onDestroy()
* - кроме уничтожения операции еще и удаление из обратного стека
* - по дефолту onSaveInstanceState() не вызывается
* (Если что - onBackPressed() - для подтверждения выхода + желательно вызов super.onBackPressed()) */


/* СМЕНА КОНФИГУРАЦИИ:
* - при смене:
*       + ориентации экрана
*       + языка
*       + устройства ввода
*
* - при входе в многоэкранный режим (доступен с Android 7.0 (API level 24)) - система сообщает
* работающей операции о смене конфигурации - можно обработать самому или дать системе*/


/* РОТАЦИЯ ЭКРАНА (без самостоятельной обработки)
* Порядок вызовов методов:
* 1. onPause()
* 2. onStop()
* 3. onDestroy()
* 4. onCreate() // частичная реинициализация объектов
* 5. onStart()
* 6. onResume()
*
* Если у всех view есть уникальный ID:
* - переменные реинициализируются
* - TextView и ButtonView реинициализируются
* - EditText, CheckBox, Switch, RadioButton НЕ реинициализируются
*
* Если у всех view нет уникального ID:
* - все реинициализируются */


/* ОБРАБОТКА РОТАЦИИ:
* 1. При помощи onSaveInstanceState и onRestoreInstanceState (можно сразу в onCreate(), но так красивее)
* 2. Вручную при помощи onConfigurationChange() (операция не уничтожается) (НЕ РЕКОМЕНДУЕТСЯ)
*   a) добавить в операцию в манифесте android:configChanges="orientation|screenSize"
*   или android:configChanges="orientation", если таргет ниже API 12
*   b) переопределить onConfigurationChanged()
*   c) никакие методы жизненного цикла НЕ ВЫЗЫВАЮТСЯ - только onConfigurationChanged()
*   d) в onConfigurationChanged() поменять ресурсы, если нужно*/


/*ТАСКИ И ОБРАТНЫЙ СТЕК
* - таск - коллекция операций, с которыми работает пользователь, делая какую-то работу
*
* - таск может уходить в фон - юзер начал новый или ушел на рабочий стол кнопкой Home
*
* - если соберется много фоновых тасков - система может начать уничтожать операции в них для
* восстановления памяти
*
* - операции организованы в обратный стек - в порядке, в котором открывались. предыдущая операция
* останавливается, когда добавляется новая
*
* - порядок операций не меняется - только добавляются в конец или убираются оттуда
*
* - при нажатии Back текущая операция уничтожается, продолжается предыдущая, и так до рабочего стола
*
* - если не осталось активностей - таска больше нет
*
* - с Android 7.0 (API level 24) в многоэкранном режиме - каждый таск управляется системой отдельно.
* У каждого окна могут быть несколько тасков.
*
* - при касании иконки - таск аппа выходит на передний план.
* Если не было до этого таска - создается новый и главная операция открывается корневой в стеке
*
* - если в какую-то операцию можно зайти с разных мест, то по дефолту она будет дублироваться в стеке*/


/*УПРАВЛЕНИЕ ТАСКАМИ
* - напр, надо операцией начать новый таск, или вызвать существующую операцию вместо новой в стеке,
* или очистить стек до корневой позиции, когда юзер выходит из активности, и т.д.
*
* - ключевые аттрибуты <activity>:
*   + taskAffinity: предпочтение определяет к какому таску предпочитает относиться операция. По
*   дефолту все операции предпочитают друг друга, поэтому предпочитают в одном аппе быть в одном
*   таске, но можно менять аттрибутом taskAffinity в манифесте. 2 случая использования:
*      - для интента с флагом FLAG_ACTIVITY_NEW_TASK - если запускается новый таск и нажимается
*      Home - нужно как-то вернуться к нему
*
*      - если у операции аттрибут allowTaskReparenting = true - она может перейти из таска, который
*      запустила, к таску, который предпочитает, когда он выйдет на передний план. For example,
*      suppose that an activity that reports weather conditions in selected cities is defined as
*      part of a travel app. It has the same affinity as other activities in the same app (the
*      default app affinity) and it allows re-parenting with this attribute. When one of your
*      activities starts the weather reporter activity, it initially belongs to the same task as
*      your activity. However, when the travel app's task comes to the foreground, the weather
*      reporter activity is reassigned to that task and displayed within it.
*
*   + launchMode (указать как операция запускается в таске):
*       - standard: система создает новую копию операции в таске, в которой она запускается,
*       и направляет интент к ней. Операция может быть запущена многократно. A-B-C + D + D =
*       A-B-C-D-D
*
*       - singleTop: для существующей копии операции наверху стека, система направляет интент к ней
*       вызовом onNewIntent(). Операция может быть запущена многократно, если она не наверху стека.
*       A-B-C + D + D + B = A-B-C-D-B
*
*       - singleTask - система создает новый таск и запускает операцию в его корне (кнопка Back
*       все равно ведет в предыдущую операцию!). Но если копия активности существует в другом таске,
*       интент направляется к существующей копии вызовом onNewIntent(). Только одна копия операции
*       может существовать единовременно. Должен использоваться только если у активности есть
*       фильтр ACTION_MAIN + CATEGORY_LAUNCHER
*
*       - singleInstance - то же, что и предыдущее, но система не запускает любую другую операцию
*       в таске с копией. Операция всегда одна и единственный член таска. Любая операция, начатая
*       этой, открывается в отдельном таске. Должен использоваться только если у активности есть
*       фильтр ACTION_MAIN + CATEGORY_LAUNCHER
*
*   (!!! если таск покинут на долгое время, система очистит из него все активности, кроме корневой
*   (типа юзер забил, на то, что делал)):
*       + alwaysRetainTaskState: если true для корневой операции таска, то описанное выше дефолтное
*       поведение не происходит. Таск сохраняет все операции продолжительное время
*
*       + clearTaskOnLaunch: если true для корневой операции таска, таск очищается до корневой
*       операции каждый раз, когда юзер покидает его и возвращается к нему. Т.е. противоположность
*       alwaysRetainTaskState
*
*       + finishOnTaskLaunch: как clearTaskOnLaunch, но для одной операции, а не всего таска. Может
*       привести к удалению всех операций, в т.ч. корневой. Операция является частью таска только
*       в ходе текущей сессии
*
* - ключевые флаги при передаче в startActivity():
*   + FLAG_ACTIVITY_NEW_TASK: (то же, что "singleTask") - запуск операции в новом таске. Если
*   операция есть в другом таске, он выходит на передний план и там эта операция вызывается методом
*   onNewIntent()
*
*   + FLAG_ACTIVITY_CLEAR_TOP: (то же, что "singleTop") - если запускаемая операция - текущая,
*   существующая получает вызов onNewIntent() вместо создания новой
*
*   + FLAG_ACTIVITY_SINGLE_TOP: если запускаемая операция уже есть в текущем таске, вместо запуска
*   новой копии операции все другие поверх ее удаляются, а эта возобновляется методом onNewIntent().
*   Чаще всего используется вместе с FLAG_ACTIVITY_NEW_TASK - чтобы найти операцию в другом таске и
*   вывести на позицию, где она может ответить на интент. Если у такой операции launchmode
*   "standard" - она тоже удаляется из стека и на ее месте создается новая копия для обработки
*   входящего интента
*
* - Запуск нового таска:
*   + настроить операцию как входную точку для таска - интент фильтр: действие -
*   "android.intent.action.MAIN", категория - "android.intent.category.LAUNCHER". Т.е. после
*   запуска таска, клик по иконке всегда будет возвращать в этот таск. Поэтому "singleTask" и
*   "singleInstance" должны использоваться только если у активности есть фильтр ACTION_MAIN +
*   CATEGORY_LAUNCHER
*
*   + если не нужно, чтоб юзер возвращался - аттрибут finishOnTaskLaunch
*
* - указание в Intent имеет приоритет над указанием в манифесте
*
* - флаги и аттрибуты не полностью дублируют друг друга*/


/* СОСТОЯНИЕ И УДАЛЕНИЕ ИЗ ПАМЯТИ
* - система удаляет не только операцию, но весь процесс с ней, когда нужна оперативная память
* - вероятность удаления зависит от состояния процесса в момент времени
* - состояние процесса зависит от состояния его операции
* - удалить процесс можно из Application Manager в Settings*/


/*ВЕРОЯТНОСТЬ УДАЛЕНИЯ:
* - Меньшая: Передний план (или подготовка к нему) - состояние операции (Created, Started, Resumed)
* - Большая: Фон (потеря фокуса) - состояние операции (Paused)
* - Еще большая:
*       + Фон (не видно юзеру) - состояние операции (Stopped)
*       + Пустая - состояние операции (Destroyed)*/


/*ИЕРАРХИЯ ВАЖНОСТИ ПРОЦЕССОВ
* 1. Процесс на переднем плане - нужен для текущих действий пользователя.
* Будет убит в самом крайнем случае. Условия, чтобы он считался таковым:
*       + работает для операции на переднем плане (onResume())
*       + в нем есть работающий ресивер (выполняется BroadcastReceiver.onReceive())
*       + в нем есть сервис, выполняющий код в одном из своих колбеков
*       (Service.onCreate(), Service.onStart(), или Service.onDestroy()).
*
* 2. Видимый процесс - выполняет работу, о которой знает юзер.
* Будет убит только ради работы процесса с переднего плана. Считается таковым, если:
*       + работает для видимой операции, но не на переднем плане (onPause())
*       + в нем есть сервис, который работает на переднем плане через Service.startForeground()
*       + в нем есть сервис, который система выполняет для видимой фичи,
*       о которой знает пользователь (напр, обои)
*
* 3. Процесс сервиса, запущенный через startService().
* Может быть понижен в приоритете до кешированного, если работает дольше 30 минут.
*
* 4. Кешированный процесс - сейчас не нужен и система может его убить при необходимости в памяти.
* В нормально работающей системе - это единственные процессы, которые используются для управления
* памятью - система убивает старые по мере необходимости. В самых крайних случаях - убивает все
* кешированные и переходит к некешированным.
* В к. процессах обычно 1 или несколько копий остановленных операций (onStop()) - их смерть
* не приведет к потере, когда юзер к ним вернется - будут воссозданы в новом процессе.*/


/* ВАЖНЫЕ МЕТОДЫ (кроме перечисленных выше)
* - dispatch...Event(...Event ev): обработка разных событий
* - finish(): вызывай, когда с активностью покончено и ее нужно закрыть
* - getActionBar(): получить ссылку на ActionBar этой активности.
* - getApplication(): вернуть application, которое обладает данной операцией
* - getCurrentFocus(): вернуть текущий view в фокусе.
* - getFragmentManager(): вернуть FragmentManager для взаимодействия с фрагментами данной операции
* - getIntent(): вернуть intent, который начал эту операцию
* - getLayoutInflater(): удобство для вызова getLayoutInflater().
* - getLoaderManager(): вернуть LoaderManager для этой активности, создав его, если надо
* - getMenuInflater(): вернуть MenuInflater с данным контекстом
* - getPreferences(int mode): получить SharedPreferences для доступа к настройкам этой операции
* - onBackPressed(): вызывается, когда операция определила нажатие кнопки back пользователем
* - onContextItemSelected(MenuItem item)
* - onCreateOptionsMenu(Menu menu): инициализирует содержимое стандартного меню операции
* - onMenuItemSelected (int featureId, MenuItem item)
* - onNavigateUp(): вызывается, когда пользователь выбирает поднятся вверх по иерархии из action bar
* - onOptionsItemSelected(MenuItem item)
* - openContextMenu(View view): программного открыть контекстное меню для конкретного view
* - openOptionsMenu()
* - recreate(): приводит к пересозданию данной операции в новом объекте
* - registerForContextMenu(View view): регистрирует контекстное меню для показа для данного view
* (несколько views могут показывать контекстное меню).
* - runOnUiThread(Runnable action): данное действие будет выполнено на UI ветке
* - setActionBar(Toolbar toolbar): установить Toolbar для работы как ActionBar для данного окна операции
* - setRequestedOrientation(int requestedOrientation): изменить желаемую ориентацию операции
* - setTitle(CharSequence title): изменить название, ассоциируемое с операцией
* - setVisible(boolean visible): установить видимость главного окна данной операции
* - startActivityForResult(Intent intent, int requestCode, Bundle options): запустить операцию, от
* которой нужно получить результат по завершении*/
