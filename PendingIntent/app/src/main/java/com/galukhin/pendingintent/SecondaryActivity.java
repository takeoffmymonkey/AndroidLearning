package com.galukhin.pendingintent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SecondaryActivity extends AppCompatActivity {

    private final String TAG = "Blya, " + SecondaryActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);
        setTitle("SecondaryActivity");
    }
}
