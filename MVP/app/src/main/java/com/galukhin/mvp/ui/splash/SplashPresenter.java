package com.galukhin.mvp.ui.splash;

import com.galukhin.mvp.data.DataManager;
import com.galukhin.mvp.ui.base.BasePresenter;

/**
 * Created by takeoff on 013 13 Feb 18.
 */

public class SplashPresenter<V extends SplashMvpView> extends BasePresenter<V> implements SplashMvpPresenter<V> {

    public SplashPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void decideNextActivity() {
        if (getDataManager().getLoggedInMode()) {
            getMvpView().openMainActivity();
        } else {
            getMvpView().openLoginActivity();
        }
    }
}