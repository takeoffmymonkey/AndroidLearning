package com.galukhin.mvp.ui.base;

import com.galukhin.mvp.data.DataManager;

/**
 * Created by takeoff on 013 13 Feb 18.
 *
 * It is base class for all presenter that implements MvpPresenter
 * and it is extended by all other presenters there in application.
 */

public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {
    // т.е. в классе используется переменная типа MvpView и наследников
    // также класс реализует интерфейс, в котором требуется переменная
    // типа MvpView и наследников (указано в объявлении интерфейса)

    private V mMvpView;

    DataManager mDataManager;


    public BasePresenter(DataManager dataManager){
        mDataManager = dataManager;
    }

    @Override
    public void onAttach(V mvpView) {
        mMvpView = mvpView;
    }

    public V getMvpView() {
        return mMvpView;
    }

    public DataManager getDataManager() {
        return mDataManager;
    }
}