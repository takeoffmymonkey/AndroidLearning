package com.galukhin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;

public class ThirdActivity extends AppCompatActivity {

    private final String TAG = "Blya, " + ThirdActivity.class.getSimpleName();

    Button button0; // Restart this screen
    Button button1; // Go to screen 1
    Button button2; // Go to screen 2

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate()");
        setContentView(R.layout.activity_third);
        setTitle("Third Activity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        button0 = findViewById(R.id.button0);
        button0.setOnClickListener(v -> {
            startActivity(new Intent(ThirdActivity.this, ThirdActivity.class));
        });

        button1 = findViewById(R.id.button1);
        button1.setOnClickListener(v -> {
            startActivity(new Intent(ThirdActivity.this, MainActivity.class));
        });

        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(v -> {
            startActivity(new Intent(ThirdActivity.this, SecondActivity.class));
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.i(TAG, "onPostResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()");
        Log.i(TAG, "activity is finishing: " + isFinishing());
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }

    @Override
    public void onBackPressed() {
        Log.i(TAG, "onBackPressed()");
        super.onBackPressed();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // TODO: 016 16 Feb 18 should not direct to main activity if it was opened from it
                NavUtils.navigateUpFromSameTask(this);
                Log.i (TAG, "navigating up");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
