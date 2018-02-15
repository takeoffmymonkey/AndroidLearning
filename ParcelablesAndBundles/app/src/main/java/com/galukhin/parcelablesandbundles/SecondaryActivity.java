package com.galukhin.parcelablesandbundles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SecondaryActivity extends AppCompatActivity {

    private final String TAG = "Blya, " + SecondaryActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);

        // Retrieve data from another activity
        getBundledData(getIntent());
    }

    private void getBundledData(Intent intent) {
        Bundle bundle = intent.getExtras();

        if (bundle != null && bundle.containsKey("button name") && bundle.containsKey("button id")) {
            String buttonName = bundle.getString("button name");
            int buttonId = bundle.getInt("button id", 0);
        }
    }
}