package com.galukhin.intent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "Blya, " + MainActivity.class.getSimpleName();

    Button btnExplicitActivity; // Для явного запуска операции
    Button btnImplicitActivity; // Для неявного интента
    Button btnImplicitActivityAlwaysChoose; // Для неявного интента с постоянным выбором (без дефолта)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*Явный интент (знает куда направляется)*/
        btnExplicitActivity = findViewById(R.id.btn_explicit_activity);
        btnExplicitActivity.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SecondaryActivity.class);

            //Я могу положить данные либо в Bundle либо в интент (тогда он создаст Bundle автоматически)
            intent.putExtra("btnExplicitActivity name", btnExplicitActivity.toString());

            // Запуск активности
            startActivity(intent);
        });


        /*Неявный интент (знает только конкретное действие, но не пункт назначения)*/
        btnImplicitActivity = findViewById(R.id.btn_implicit_activity);
        btnImplicitActivity.setOnClickListener(v -> {
            String textMessage = "Текст для неявного интента";

            Intent sendIntent = new Intent();
            /*Неявный объект Intent указывает действие, которым может быть вызвано любое имеющееся
            на устройстве приложение, способное выполнить это действие*/
            sendIntent.setAction(Intent.ACTION_SEND); // ACTION_SEND: доставить данные кому-то
            sendIntent.putExtra(Intent.EXTRA_TEXT, textMessage);
            /*В этом случае URI не используется, а вместо этого следует объявить тип данных объекта
            Intent, чтобы указать контент, содержащийся в дополнительных данных.*/
            sendIntent.setType("text/plain"); // указать явный тип данных MIME

            // Проверить, есть ли операция, которая сможет разрешить интент (иначе исключение!)
            if (sendIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(sendIntent);
            }
        });


        /*Неявный интент с постоянным выбором варианта (без кнопки "использовать по умолчанию") */
        btnImplicitActivityAlwaysChoose = findViewById(R.id.btn_implicit_activity_always_choose);
        btnImplicitActivityAlwaysChoose.setOnClickListener(v -> {
            String textMessage = "Текст для неявного интента";

            // Можно задавать действие в конструкторе
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, textMessage);
            sendIntent.setType("text/plain");

            // Для UI текста всегда использовать строковый ресурс
            String title = getResources().getString(R.string.chooser_title);
            // Нужно создать отдельный intent для показа диалогового окна выбора
            Intent chooser = Intent.createChooser(sendIntent, title);

            if (sendIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(chooser);
            }
        });


        // TODO: 017 17 Feb 18 Попробовать запуск службы


        // TODO: 017 17 Feb 18 Попробовать рассылку широковещательных сообщений

    }
}

/*Intent extends Object implements Parcelable, Cloneable*/


/* PendingIntent вынесен в отдельный проект */


/*ЗАЧЕМ
* - для запуска операции: передать Intent методу startActivity()
* - для запуска операции и возвращения результата: методом startActivityForResult() - вернет Intent
* при обратном вызове onActivityResult()
* - для запуска службы (напр. загрузить файл): передать Intent методу startService()
* - для рассылки шир. сообщений: передать объект Intent методу sendBroadcast(), sendOrderedBroadcast()
* или sendStickyBroadcast()*/


/* [Activity A] -> startActivity() --intent--> [Android system] --intent--> onCreate() -> [Activity B]*/


/*ТИПЫ
* Явные:
* - для запуска компонента по имени класса
* - обычно для запуска компонентов своего аппа (т.к. известно имя)
* - чтобы случайно не запустить службу другого аппа, используй явные объекты Intent
* - любые фильтры компонента игнорируются и intent доставляется в любом случае
*
* Неявные:
* - без имени конкретного компонента, только действие в целом
* - может выполняться другим аппом: А ищет компонент по фильтрам Intent в манифесте
* - если подходит 1 фильтр - сразу запускается подходящее действие
* - если подходит несколько фильтров - диалоговое окно с выбором
* - если ни один - аварийное завершение. Проверить: resolveActivity()
* - можно сделать так, чтобы не выбирался дефолтный вариант
* - не запускать свои службы неявным интентом*/


/* Запуск служб с помощью неявных объектов Intent является рискованным с точки зрения безопасности,
* поскольку нельзя быть на абсолютно уверенным, какая служба отреагирует на такой объект Intent,
* а пользователь не может видеть, какая служба запускается
*
* Начиная с Android 5.0 (уровень API 21) система вызывает исключение при вызове метода bindService()
* с помощью неявного объекта Intent*/


/*СОЗДАНИЕ INTENT
*
* [Имя компонента]:
* - имя компонента для запуска
* - является необязательным (тогда неявный объект)
* - обязательно для служб
* - задать имя компонента можно с помощью метода setComponent(), setClass(), setClassName() или
* конструктора Intent
*
* [Действие]
* - необходимое для выполнения действие
* - при выдаче шир. сообщение - действие, которое произошло
* - можно использовать свои действия (нужно добавлять имя пакета), но обычно константы класса
* Intent, Settings и др.
* - напр. ACTION_VIEW - когда есть определенная инфо для показа пользователю, которую может
* показать операция (напр. фото)
* - напр. ACTION_SEND - при наличии определенных данных, доступ к которым можно предоставить через
* другой апп (напр. имейл)
*
* [Данные]
* - URI, ссылающийся на данные, с которыми будет выполняться действие и/или тип MIME этих данных
* - если требуется задать и URI, и тип MIME, не вызывайте setData() и setType(), поскольку каждый
* из этих методов аннулирует результат выполнения другого. Чтобы задать URI и тип MIME всегда
* используйте метод setDataAndType()
* - тип передаваемых данных обычно определяется действием объекта Intent
*
* [Категория]
* - строка с прочими данными о компоненте, которым должен обрабатываться объект Intent
* - напр. стандартная категория CATEGORY_BROWSABLE: позволяет запускать себя веб-браузером для
* отображения данных, указанных по ссылке — например, изображения или сообщения электронной почты
* - напр. стандартная категория CATEGORY_LAUNCHER: является начальной операцией задачи, она указана
* в средстве запуска приложений системы
*
* [Дополнительные данные]
* - пары "ключ-значение" с прочей информацией (putExtra() или объект Bundle+putExtras())
* - напр. при ACTION_SEND указать получателя ключом EXTRA_EMAIL
* - для своего ключа обязательно имя пакета: static final String EXTRA_GIGAWATTS =
* "com.example.EXTRA_GIGAWATTS";
* - не влияют на то, каким образом определяется требуемый компонент приложения
*
* [Флаги]
* - действуют как метаданные для объекта Intent
* - должны указывать А как запускать операцию (напр. к какому таску должна принадлежать операция)
* и как с ней обращаться после запуска (напр. будет ли она указана в списке последних операций)*/


/* ПОЛУЧЕНИЕ НЕЯВНОГО ИНТЕНТА
* - фильтр Intent - выражение в файле манифеста аппа, указывающее типы объектов Intent, которые
* мог бы принимать компонент
*
* - компонент аппа должен объявлять отдельные фильтры для каждой уникальной работы, которую он может
* выполнить (напр, один для просмотра изображения, второй для его редактирования)
*
* Параметры фильтра:
*   + <action> - объявляет принимаемое действие, заданное в объекте Intent, в атрибуте name.
*   Значение должно быть текстовой строкой действия, а не константой класса
*
*   + <data> - oбъявляет тип принимаемых данных - один или несколько атрибутов, указывающих
*   составные части URI данных (scheme, host, port, path и т. д.) и тип MIME
*
*   + <category> - объявляет принимаемую категорию, заданную в объекте Intent, в атрибуте name.
*   Значение должно быть текстовой строкой действия, а не константой класса
*
* - !!!для получения неявных объектов Intent обязательно необходимо включить категорию CATEGORY_DEFAULT
* в фильтр Intent (методы startActivity() и startActivityForResult() обрабатывают все объекты Intent,
* как если бы они объявляли категорию CATEGORY_DEFAULT). Если вы не объявляете эту категорию в своем
* фильтре Intent, никакие неявные объекты Intent не будут переданы в вашу операцию!!!
*
* - можно создавать фильтры, в которых будет несколько экземпляров action, data или category.
* В этом случае просто нужно убедиться в том, что компонент может справиться с любыми сочетаниями
* этих элементов фильтра.
*
* - когда требуется обрабатывать объекты Intent нескольких видов, но только в определенных
* сочетаниях действия, типа данных и категории, необходимо создать несколько фильтров Intent.
*
* - фильтры для приемников широковещательных сообщений можно регистрировать динамически (не в
* манифесте) путем вызова registerReceiver(). После этого регистрацию приемника можно отменить с
* помощью unregisterReceiver(). В результате апп сможет воспринимать определенные объявления только
* в течение указанного периода времени в процессе работы приложения.*/


/*РАЗРЕШЕНИЕ ОБЪЕКТОВ Intent
* [Тестирование <action>]:
* - Чтобы пройти фильтр, действие, указанное в объекте Intent, должно соответствовать 1 или
* нескольким действиям, перечисленным в фильтре.
* - Если в фильтре не перечислены какие-либо действия, объекту Intent будет нечему соответствовать,
* поэтому все объекты Intent не пройдут этот тест.
* - Однако, если в объекте Intent не указано действие, он пройдет тест (если в фильтре содержится
* хотя бы 1 действие).
*
*
* [Тестирование <category>]
* - Чтобы объект Intent прошел тестирование категории, все категории, приведенные в объекте Intent,
* должны соответствовать категории из фильтра.
* - Обратное не требуется — фильтр Intent может объявлять и другие категории, которых нет в объекте
* Intent, объект Intent при этом все равно пройдет тест.
* - Поэтому объект Intent без категорий всегда пройдет этот тест, независимо от того, какие
* категории объявлены в фильтре.
* - Примечание. Система Android автоматически применяет категорию CATEGORY_DEFAULT ко всем неявным
* объектам Intent, которые передаются в startActivity() и startActivityForResult(). Поэтому, если
* вы хотите, чтобы ваша операция принимала неявные объекты Intent, в ее фильтрах Intent должна быть
* указана категория для "android.intent.category.DEFAULT"
*
*
* [Тестирование <data>]:
* - имеются отдельные атрибуты — scheme, host, port и path — для каждой составной части URI:
*   + Если схема не указана, узел игнорируется.
*   + Если узел не указан, порт игнорируется.
*   + Если не указана ни схема, ни узел, путь игнорируется.
*
* - когда URI сравнивается с URI из фильтра, сравнение выполняется только с теми составными частями
* URI, которые приведены в фильтре
*
* - путь может быть указан с подстановочным символом (*), чтобы потребовалось только частичное
* соответствие имени пути
*
* - Правила:
*   + Объект Intent без URI и MIME пройдет тест, если в фильтре не указано URI или MIME.
*   + Объект Intent с URI, но без MIME (ни явный, ни тот, который можно вывести из URI), пройдет
*   тест, если URI соответствует формату URI из фильтра, а в фильтре также не указан тип MIME.
*   + Объект Intent с MIME, но без URI пройдет тест, если в фильтре указан тот же тип MIME и не
*   указан формат URI.
*   + Объект Intent с URI и MIME (явный или тот, который можно вывести из URI), пройдет только часть
*   теста, проверяющую тип MIME, в том случае, если этот тип совпадает с типом, приведенным в
*   фильтре. Он пройдет часть этого теста, которая проверяет URI, либо если его URI  совпадает с URI
*   из фильтра, либо если этот объект содержит URI content: или file:, а в фильтре URI не указан.
*   Другими словами, предполагается, что компонент поддерживает данные content: и file:, если в его
*   фильтре указан только тип MIME*/


/* ОГРАНИЧЕНИЕ ДОСТУПА К КОМПОНЕНТАМ
* Использование фильтра Intent не является безопасным способом предотвращения запуска ваших
* компонентов другими приложениями. Несмотря на то, что после применения фильтров Intent компонент
* будет реагировать только на неявные объекты Intent определенного вида, другое приложение
* теоретически может запустить компонент вашего приложения с помощью явного объекта Intent, если
* разработчик определит имена ваших компонентов. Если важно, чтобы только ваше собственное приложение
* могло запускать один из ваших компонентов, задайте для атрибута exported этого компонента значение
* "false"*/


/*ДРУГИЕ ОСОБЕННОСТИ:
* - интенты сопоставляются с фильтрами не только для определения целевого компонента, но и для
* выявления определенных сведений о наборе компонентов на устройстве (напр. главный экран заполняет
* средство запуска аппов с операциями с фильтром с действием ACTION_MAIN и категорией CATEGORY_LAUNCHER)
*
* - можно самостоятельно проверить Intentы: в PackageManager набор методов query...(), которые
* возвращают все компоненты, способные принять определенный объект, а также схожий набор методов
* resolve...(), которые определяют наиболее подходящий компонент, способный реагировать на объект
* Intent*/


/*КЛЮЧЕВЫЕ МЕТОДЫ
* addCategory(String category): добавить новую категорию в интент
* addFlags(int flags) добавить дополнительные флаги
* createChooser(Intent target, CharSequence title): удобство для создания интента ACTION_CHOOSER
* getAction(): узнать общее действие для выполнения, напр. ACTION_VIEW.
* getCategories(): вернуть список всех категорий в интенте
* get...Extra(String name): получить расширенные данные из intent
* getType(): получить явный тип MIME в интенте
* parseIntent(Resources resources, XmlPullParser parser, AttributeSet attrs): парсит элемент "intent"
* (и его детей) из XML и запускает объект Intent.
* parseUri(String uri, int flags): создает intent из URI
* putExtra(String name, ...): добавляет расширенные данные в intent.
* removeCategory(String category): убирает категорию из интента
* resolveType(Context context): возвращает данные типа MIME для этого intent
* setFlags(int flags): устанавливает специальные флаги, контролирующие, как обрабатывается intent*/


/*СТАНДАРТНЫЕ ДЕЙСТВИЯ ОПЕРАЦИИ (via startActivity(Intent))
ACTION_MAIN (наиболее важное и используемое)
ACTION_VIEW
ACTION_ATTACH_DATA
ACTION_EDIT (наиболее важное и используемое)
ACTION_PICK
ACTION_CHOOSER
ACTION_GET_CONTENT
ACTION_DIAL
ACTION_CALL
ACTION_SEND
ACTION_SENDTO
ACTION_ANSWER
ACTION_INSERT
ACTION_DELETE
ACTION_RUN
ACTION_SYNC
ACTION_PICK_ACTIVITY
ACTION_SEARCH
ACTION_WEB_SEARCH
ACTION_FACTORY_TEST*/


/* СТАНДАРТНЫЕ ДЕЙСТВИЯ BROADCAST (via registerReceiver(BroadcastReceiver, IntentFilter) или <receiver>)
ACTION_TIME_TICK
ACTION_TIME_CHANGED
ACTION_TIMEZONE_CHANGED
ACTION_BOOT_COMPLETED
ACTION_PACKAGE_ADDED
ACTION_PACKAGE_CHANGED
ACTION_PACKAGE_REMOVED
ACTION_PACKAGE_RESTARTED
ACTION_PACKAGE_DATA_CLEARED
ACTION_PACKAGES_SUSPENDED
ACTION_PACKAGES_UNSUSPENDED
ACTION_UID_REMOVED
ACTION_BATTERY_CHANGED
ACTION_POWER_CONNECTED
ACTION_POWER_DISCONNECTED
ACTION_SHUTDOWN*/


/*СТАНДАРТНЫЕ КАТЕГОРИИ (via addCategory(String))
CATEGORY_DEFAULT
CATEGORY_BROWSABLE
CATEGORY_TAB
CATEGORY_ALTERNATIVE
CATEGORY_SELECTED_ALTERNATIVE
CATEGORY_LAUNCHER
CATEGORY_INFO
CATEGORY_HOME
CATEGORY_PREFERENCE
CATEGORY_TEST
CATEGORY_CAR_DOCK
CATEGORY_DESK_DOCK
CATEGORY_LE_DESK_DOCK
CATEGORY_HE_DESK_DOCK
CATEGORY_CAR_MODE
CATEGORY_APP_MARKET
CATEGORY_VR_HOME*/


/*СТАНДАРТНЫЕ ЭКСТРА ДАННЫЕ (via putExtra(String, Bundle))
EXTRA_ALARM_COUNT
EXTRA_BCC
EXTRA_CC
EXTRA_CHANGED_COMPONENT_NAME
EXTRA_DATA_REMOVED
EXTRA_DOCK_STATE
EXTRA_DOCK_STATE_HE_DESK
EXTRA_DOCK_STATE_LE_DESK
EXTRA_DOCK_STATE_CAR
EXTRA_DOCK_STATE_DESK
EXTRA_DOCK_STATE_UNDOCKED
EXTRA_DONT_KILL_APP
EXTRA_EMAIL
EXTRA_INITIAL_INTENTS
EXTRA_INTENT
EXTRA_KEY_EVENT
EXTRA_ORIGINATING_URI
EXTRA_PHONE_NUMBER
EXTRA_REFERRER
EXTRA_REMOTE_INTENT_TOKEN
EXTRA_REPLACING
EXTRA_SHORTCUT_ICON
EXTRA_SHORTCUT_ICON_RESOURCE
EXTRA_SHORTCUT_INTENT
EXTRA_STREAM
EXTRA_SHORTCUT_NAME
EXTRA_SUBJECT
EXTRA_TEMPLATE
EXTRA_TEXT
EXTRA_TITLE
EXTRA_UID*/


/*СТАНДАРТНЫЕ ФЛАГИ (via setFlags(int) и addFlags(int))
FLAG_GRANT_READ_URI_PERMISSION
FLAG_GRANT_WRITE_URI_PERMISSION
FLAG_FROM_BACKGROUND
FLAG_DEBUG_LOG_RESOLUTION
FLAG_EXCLUDE_STOPPED_PACKAGES
FLAG_INCLUDE_STOPPED_PACKAGES
FLAG_GRANT_PERSISTABLE_URI_PERMISSION
FLAG_GRANT_PREFIX_URI_PERMISSION
FLAG_RECEIVER_REGISTERED_ONLY
FLAG_RECEIVER_REPLACE_PENDING
FLAG_RECEIVER_FOREGROUND
FLAG_RECEIVER_NO_ABORT
FLAG_ACTIVITY_CLEAR_TOP
FLAG_ACTIVITY_FORWARD_RESULT
FLAG_ACTIVITY_PREVIOUS_IS_TOP
FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
FLAG_ACTIVITY_BROUGHT_TO_FRONT
FLAG_RECEIVER_VISIBLE_TO_INSTANT_APPS
FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY
FLAG_ACTIVITY_NEW_DOCUMENT
FLAG_ACTIVITY_NO_USER_ACTION
FLAG_ACTIVITY_REORDER_TO_FRONT
FLAG_ACTIVITY_NO_ANIMATION
FLAG_ACTIVITY_CLEAR_TASK
FLAG_ACTIVITY_TASK_ON_HOME
FLAG_ACTIVITY_RETAIN_IN_RECENTS
FLAG_ACTIVITY_LAUNCH_ADJACENT.*/