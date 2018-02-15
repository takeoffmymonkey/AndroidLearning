package com.galukhin.mvp.ui.splash;

import com.galukhin.mvp.ui.base.MvpPresenter;

/**
 * Created by takeoff on 013 13 Feb 18.
 */

public interface SplashMvpPresenter<V extends SplashMvpView> extends MvpPresenter<V> {

    void decideNextActivity();

}