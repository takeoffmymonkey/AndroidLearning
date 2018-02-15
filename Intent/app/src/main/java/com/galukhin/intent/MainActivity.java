package com.galukhin.intent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "Blya, " + MainActivity.class.getSimpleName();

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        button.setOnClickListener(v -> {
            // This is an explicit intent as it has a known target
            Intent intent = new Intent(MainActivity.this, SecondaryActivity.class);

            /*I may create a Bundle by myself, put extras there and add it to intent
            or I may call putExtras and it creates a Bundle and adds extras to it automatically*/
            intent.putExtra("button name", button.toString());
            intent.putExtra("button id", button.getId());
            startActivity(intent);
        });

    }
}
