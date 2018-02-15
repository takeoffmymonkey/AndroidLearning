package com.galukhin.mvp.ui.main;

import com.galukhin.mvp.data.DataManager;
import com.galukhin.mvp.ui.base.BasePresenter;

/**
 * Created by takeoff on 013 13 Feb 18.
 */

public class MainPresenter<V extends MainMvpView> extends BasePresenter<V> implements MainMvpPresenter<V> {

    public MainPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public String getEmailId() {
        return getDataManager().getEmailId();
    }

    @Override
    public void setUserLoggedOut() {
        getDataManager().clear();
        getMvpView().openSplashActivity();
    }

}