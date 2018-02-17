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
        //Распаковываем и устанавливаем данные (только на null проверить надо)
        tv.setText(getIntent().getStringExtra(Intent.EXTRA_TEXT));

    }
}
