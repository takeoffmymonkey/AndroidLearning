package com.galukhin.mvp.ui.login;

import com.galukhin.mvp.ui.base.MvpView;

/**
 * Created by takeoff on 013 13 Feb 18.
 */

public interface LoginMvpView extends MvpView {

    void openMainActivity();

    void onLoginButtonClick();

}