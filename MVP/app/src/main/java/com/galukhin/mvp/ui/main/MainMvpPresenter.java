package com.galukhin.mvp.ui.main;

import com.galukhin.mvp.ui.base.MvpPresenter;

/**
 * Created by takeoff on 013 13 Feb 18.
 */

public interface MainMvpPresenter<V extends MainMvpView> extends MvpPresenter<V> {

    String getEmailId();

    void setUserLoggedOut();

}