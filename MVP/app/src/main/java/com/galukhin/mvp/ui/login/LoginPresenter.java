package com.galukhin.mvp.ui.login;

import com.galukhin.mvp.data.DataManager;
import com.galukhin.mvp.ui.base.BasePresenter;

/**
 * Created by takeoff on 013 13 Feb 18.
 */

public class LoginPresenter<V extends LoginMvpView> extends BasePresenter<V> implements LoginMvpPresenter<V> {

    public LoginPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void startLogin(String emailId) {
        getDataManager().saveEmailId(emailId);
        getDataManager().setLoggedIn();
        getMvpView().openMainActivity();
    }

}