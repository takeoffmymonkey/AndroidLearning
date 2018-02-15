package com.galukhin.portraitlandscape;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
/*Simply add layout-land folder to res with the same activity_main.xml in it
* Edit design for elements that have to change*/

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
