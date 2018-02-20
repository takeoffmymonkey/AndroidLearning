package com.galukhin.parcelablesandbundles;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class SecondaryActivity extends AppCompatActivity {

    private final String TAG = "Blya, " + SecondaryActivity.class.getSimpleName();
    private String passedString;
    private int passedInteger;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);
        setTitle("Secondary Activity");

        TextView tvString = findViewById(R.id.tv_string);
        TextView tvInt = findViewById(R.id.tv_int);

        // Retrieve simple data from another activity
        getBundledData(getIntent());

        tvString.setText(passedString);
        tvInt.setText(Integer.toString(passedInteger));
    }

    private void getBundledData(Intent intent) {
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            if (bundle.containsKey(MainActivity.STRING_DATA)) {
                passedString = bundle.getString(MainActivity.STRING_DATA);
            }
            if (bundle.containsKey(MainActivity.INT_DATA)) {
                passedInteger = bundle.getInt(MainActivity.INT_DATA, 666);
            }
        }
    }
}