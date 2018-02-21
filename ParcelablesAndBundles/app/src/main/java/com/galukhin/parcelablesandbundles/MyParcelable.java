package com.galukhin.parcelablesandbundles;

import android.os.Parcel;
import android.os.Parcelable;

/* Передаваемый объект должен имплементировать интерфейс Parcelable */

public class MyParcelable implements Parcelable {
    private final String TAG = "Blya, " + MyParcelable.class.getSimpleName();

    /* Данные, которые будем передавать */
    int someInt;
    SomeClass someClass; // Должен также реализовать Parcelable

    @Override
    /* Вызывается системой при упаковке
    * Приходит пустой объект (вероятно), в него упаковываем данные*/
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(someInt);
        dest.writeParcelable(someClass, flags);
    }

    @Override
    /* Непонятный обязательный метод */
    public int describeContents() {
        return 0;
    }

    /* У класса, реализующего Parcelable, обязательно должно быть не наловое статическое поле
    * CREATOR типа класса, которое реализует интерфейс Parcelable.Creator
    * Parcelable.Creator генерирует экземпляры моего класса из Parcel */
    public static final Creator<MyParcelable> CREATOR = new Creator<MyParcelable>() {
        @Override
        // Распаковываем объект из Parcel вызовом конструктора
        public MyParcelable createFromParcel(Parcel in) {
            return new MyParcelable(in);
        }

        /* Непонятный обязательный метод*/
        @Override
        public MyParcelable[] newArray(int size) {
            return new MyParcelable[size];
        }
    };

    /* Конструктор, считывающий данные из Parcel */
    private MyParcelable(Parcel in) {
        someInt = in.readInt();
        someClass = in.readParcelable(getClass().getClassLoader());
    }

    /* Пустой конструктор, где я просто изначально инициализирую поля */
    MyParcelable() {
        someInt = 555;
        someClass = new SomeClass();
    }
}

/* Объект, который будет внутри MyParcelable. Также должен имплементировать Parcelable*/
class SomeClass implements Parcelable {
    String someClassString;

    private SomeClass(Parcel in) {
        someClassString = in.readString();
    }

    public static final Creator<SomeClass> CREATOR = new Creator<SomeClass>() {
        @Override
        public SomeClass createFromParcel(Parcel in) {
            return new SomeClass(in);
        }

        @Override
        public SomeClass[] newArray(int size) {
            return new SomeClass[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(someClassString);
    }

    SomeClass() {
        someClassString = "Строка объекта внутри объекта";
    }
}


/* PARCELABLE
* public interface Parcelable
*
* - интерфейс для классов, чьи экземпляры могут быть записаны в и восстановлены из Parcel.
*
* - классы, имплементирующие Parcelable, должны также иметь non-null статичное поле CREATOR типа,
* имплементирующее интерфейс Parcelable.Creator.*/


/* PARCEL
 * public final class Parcel extends Object
 *
 * - Контейнер для сообщения (ссылок на данные и объекты), который может пересылаться через IBinder
 *
 * - Parcel может содержать И flattened данные, которые будут unflattened с другой стороны IPC
 * (при помощи разных методов для записи определенных типов или общего интерфейса Parcelable),
 * И ссылки на живые объекты IBinder, что приведет к тому, что другая сторона получит прокси IBinder,
 * связанный с оригинальным Binder в Parcel. TODO: 021 21 Feb 18 чо?
 *
 * - Parcel - не механизм сериализации общего назначения - никогда не хранить данные Parcel на диске
 * или отправлять по сети
 *
 * - Кусок API Parcel относится к чтению/записи данных различных типов. 6 таких основных классов:
 *  1) Примитивы:
 *      базовые функции данных для записи/чтения примитивных типов данных:
 *          - writeByte(byte)
 *          - readByte()
 *          - writeDouble(double)
 *          - readDouble()
 *          - writeFloat(float)
 *          - readFloat()
 *          - writeInt(int)
 *          - readInt()
 *          - writeLong(long)
 *          - readLong()
 *          - writeString(String)
 *          - readString().
 *      Большинство операций с данными строятся на этих методах. Эти данные пишутся и считываются
 *      при помощи порядка следования байтов ЦП хоста.
 *
 *  2) Примитивные массивы:
 *      Есть множество методов для чтения и записи raw массивов из примитивных объектов, которые
 *      обычно результируют записью длины в 4 бита, после которых идут примитивные элементы. Методы
 *      для чтения могут как считывать данные в существующий массив, так и создавать и возвращать
 *      новый. Доступные типы:
 *          - writeBooleanArray(boolean[]), readBooleanArray(boolean[]), createBooleanArray()
 *          - writeByteArray(byte[]), writeByteArray(byte[], int, int), readByteArray(byte[]), createByteArray()
 *          - writeCharArray(char[]), readCharArray(char[]), createCharArray()
 *          - writeDoubleArray(double[]), readDoubleArray(double[]), createDoubleArray()
 *          - writeFloatArray(float[]), readFloatArray(float[]), createFloatArray()
 *          - writeIntArray(int[]), readIntArray(int[]), createIntArray()
 *          - writeLongArray(long[]), readLongArray(long[]), createLongArray()
 *          - writeStringArray(String[]), readStringArray(String[]), createStringArray().
 *          - writeSparseBooleanArray(SparseBooleanArray), readSparseBooleanArray().*
 *
 *  3) Parcelables:
 *      Протокол Parcelable предоставляет очень эффективный (но низкоуровневый) протокол для
 *      объектов для записи и чтения самих себя из Parcels. Можно использовать прямые методы
 *          - writeParcelable(Parcelable, int) и readParcelable(ClassLoader) или
 *          - writeParcelableArray(T[], int) и readParcelableArray(ClassLoader)
 *      для записи или чтения. Эти методы записывают и тип класса и его данные в Parcel, позволяя этому классу быть
 *      воссозданным из соответствующего загрузчика класса при дальнейшем чтении.
 *
 *      Также есть методы, которые предоставляют способ более эффективной работы с Parcelables:
 *          - writeTypedObject(T, int)
 *          - writeTypedArray(T[], int)
 *          - writeTypedList(List)
 *          - readTypedObject(Parcelable.Creator)
 *          - createTypedArray(Parcelable.Creator)
 *          - createTypedArrayList(Parcelable.Creator).
 *      Эти методы не пишут информацию о классе оригинального объекта, а вместо этого вызывающий
 *      функцию чтения должен знать, какой тип ожидается, и передавать соответствующий
 *      Parcelable.Creator, чтобы правильно построить новый объект и считать его данные.
 *      (Чтобы более эффективно писать и читать единичный объект Parcelable, который не null, можно
 *      напрямую самому вызвать
 *          - Parcelable.writeToParcel и
 *          - Parcelable.Creator.createFromParcel)
 *
 *  4) Bundles:
 *      Специальный типобезопасный контейнер, доступен для маппинга ключей и неоднородных значений.
 *      Он хорошо оптимизирован для чтения/записи данных, и его типобезопасный API избегает
 *      сложности при дебаггинге ошибок типа, когда в конце упорядочиваются данные в Parcel. Методы
 *      для использования:
 *          - writeBundle(Bundle)
 *          - readBundle() и
 *          - readBundle(ClassLoader).
 *
 *  5) Активные объекты:
 *      Необычная фича Parcel - ее возможность читать и писать активные объекты. Для таких объектов
 *      реальное содержание объекта не записывается, а записывается специальный token, ссылающийся
 *      на объект, который записывается. Когда объект обратно считывается из Parcel, то не получаешь
 *      новый экземпляр, а скорее идентификатор, который управляет тем же объектом, который
 *      изначально записывался. Есть 2 доступные формы активных объектов:
 *          - объекты Binder - базовое удобство общей межпроцессной коммуникационной системы Android.
 *          Интерфейс IBinder описывает абстрактный протокол с объектом Binder. Такой интерфейс
 *          может быть записан в Parcel, и при чтении получаешь либо оригинальный объект,
 *          имплементирующий этот интерфейс, либо спициальную proxy имплементацию, которая направляет
 *          вызовы обратно к оригинальному объекту. Методы:
 *              - writeStrongBinder(IBinder),
 *              - writeStrongInterface(IInterface),
 *              - readStrongBinder(), writeBinderArray(IBinder[]),
 *              - readBinderArray(IBinder[]),
 *              - createBinderArray(),
 *              - writeBinderList(List),
 *              - readBinderList(List)
 *              - createBinderArrayList().
 *
 *          - объекты FileDescriptor, представляющие raw Linux file descriptor identifiers, могут
 *          быть записаны, и будет возвращены объекты ParcelFileDescriptor для операций над
 *          оригинальным file descriptor. Возвращенный file descriptor - дубликат оригинального
 *          file descriptor: объект и fd разные, но оперируют над тем же лежащем в основе файловым
 *          потоком, с той же позицией, и т.д. Методы:
 *              - writeFileDescriptor(FileDescriptor),
 *              - readFileDescriptor().
 *
 *  6) Нетипизированные контейнеры
 *      Оставшийся класс методов предназначен для записи и чтения стандартных контейнеров Java
 *      разных типов. Методы:
 *          - writeValue(Object) и
 *          - readValue(ClassLoader)
 *      определяют типы допустимых объектов.
 *
 *      Методы контейнера:
 *          - writeArray(Object[]),
 *          - readArray(ClassLoader),
 *          - writeList(List),
 *          - readList(List, ClassLoader),
 *          - readArrayList(ClassLoader),
 *          - writeMap(Map),
 *          - readMap(Map, ClassLoader),
 *          - writeSparseArray(SparseArray),
 *          - readSparseArray(ClassLoader).*/