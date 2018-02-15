package com.galukhin.mvp;

import android.app.Application;

import com.galukhin.mvp.data.DataManager;
import com.galukhin.mvp.data.SharedPrefsHelper;

/**
 * Created by takeoff on 013 13 Feb 18.
 */

public class MvpApp extends Application {

    DataManager dataManager;

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPrefsHelper sharedPrefsHelper = new SharedPrefsHelper(getApplicationContext());
        dataManager = new DataManager(sharedPrefsHelper);

    }

    public DataManager getDataManager() {
        return dataManager;
    }

}