package com.galukhin.mvp.ui.base;

/**
 * Created by takeoff on 013 13 Feb 18.
 *
 * It is an interface that is implemented by BasePresenter,
 * it acts as base presenter interface that is extended by all other
 * presenter interfaces.
 *
 */

public interface MvpPresenter<V extends MvpView> {
    // т.е  я могу использовать внутри интерфейса переменную типа
    // в данном случае, класса MvpView и наследников

    void onAttach(V mvpView);

}