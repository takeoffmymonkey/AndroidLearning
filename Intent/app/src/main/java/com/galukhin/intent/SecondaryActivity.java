package com.galukhin.intent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class SecondaryActivity extends AppCompatActivity {

    private final String TAG = "Blya, " + SecondaryActivity.class.getSimpleName();
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);
        setTitle("SecondaryActivity");

        tv = findViewById(R.id.textView);

        String receivedExtraText = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        if (receivedExtraText == null) // Пришел сюда по кастомному фильтру
            receivedExtraText = getIntent().getStringExtra(MainActivity.CUSTOM_EXTRA_TEXT);
        tv.setText(receivedExtraText);

    }
}
