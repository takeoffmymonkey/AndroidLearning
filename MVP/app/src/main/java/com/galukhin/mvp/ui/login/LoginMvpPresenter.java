package com.galukhin.mvp.ui.login;

import com.galukhin.mvp.ui.base.MvpPresenter;

/**
 * Created by takeoff on 013 13 Feb 18.
 */

public interface LoginMvpPresenter<V extends LoginMvpView> extends MvpPresenter<V> {

    void startLogin(String emailId);

}