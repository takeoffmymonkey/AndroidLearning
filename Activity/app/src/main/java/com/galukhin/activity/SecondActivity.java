package com.galukhin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity {

    private final String TAG = "Blya, " + SecondActivity.class.getSimpleName();

    Button button0; // Restart this screen
    Button button1; // Go to screen 1
    Button button2; // Go to screen 3

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate()");
        setContentView(R.layout.activity_second);
        setTitle("Second Activity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        button0 = findViewById(R.id.button0);
        button0.setOnClickListener(v -> {
            startActivity(new Intent(SecondActivity.this, SecondActivity.class));
        });

        button1 = findViewById(R.id.button1);
        button1.setOnClickListener(v -> {
            startActivity(new Intent(SecondActivity.this, MainActivity.class));
        });

        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(v -> {
            startActivity(new Intent(SecondActivity.this, ThirdActivity.class));
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

    @Override
    /* Вызов метода navigateUpFromSameTask() завершает текущую операцию и начинает (или продолжает)
    * соответствующую родительскую операцию. Если целевая родительская операция находится в бэк
    * стеке таска, она выводится вперед. Способ, которым она выводится, зависит на способности
    * родительской операции обрабатывать вызов onNewIntent():
    *   - если у родительской операции режим запуска <singleTop>, или up intent содержит
    *   FLAG_ACTIVITY_CLEAR_TOP, то родительская операция выводится на верх стека, и получает
    *   intent через свой метод onNewIntent().
    *
    *   - если у родительской операции режим запуска <standard>, и up intent не содержит
    *   FLAG_ACTIVITY_CLEAR_TOP, то родительская операция убирается из стека, и новый экземпляр этой
    *   операции создается на верху стека, получающего intent.
    *
    * Тем не менее, использование navigateUpFromSameTask() подходит только, если мой апп является
    * владельцем текущего таска (т.е. юзер начал этот таск с моего аппа). Если это не так, и моя
    * операция была запущена в таске, принадлежащем другому аппу, тогда навигация вверх должна
    * создавать новый таск, который принадлежит моему аппу, что требует от меня создания нового бек
    * стека*/
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                Log.i(TAG, "navigating up");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
