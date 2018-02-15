package com.galukhin.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

/* CLASS HIERARCHY:
* > MainActivity
* > AppCompatActivity - If MaterialDesign implementation is needed = Target < API 21
* > FragmentActivity - If nested Fragments are needed
* > BaseFragmentActivityApi16 (Honeycomb)
* > BaseFragmentActivityApi14 (Eclair)
* > SupportActivity (Donut)
* > Activity */


/* MINIMUM API 15-16 ~ 95% devices */


/* ACTIVITY LIFECYCLE:
* 1. onCreate()
* 2. onStart() // + onRestoreInstanceState() if there was rotation
* 3. onResume()
* 4. > NAVIGATING TO ANOTHER ACTIVITY
* 5. onPause(), onSaveInstanceState()
* 6. onCreate(), onStart(), onResume() // for another activity
* 7. onStop()
* 8. > BACK PRESSED
* 9. onPause(), onSaveInstanceState() // for another activity
* 10. onDestroy() for another activity!
* 11. onRestart()
* 12. onStart()
* 13. onResume()
* 14. onStop() for another activity
* 15. > BACK PRESSED AGAIN OR HOMESCREEN
* 16. onPause(), onSaveInstanceState(), onStop() // for both buttons
* 17. onDestroy() (only for BACK button) */


/* STACK - here Activities are being added
* LIFO - last in first out
* onDestroy is called when Activity is deleted from stack (back pressed) */


/* SCREEN ROTATION (normal)
* 1. onPause()
* 2. onStop()
* 3. onDestroy()
* 4. onCreate(): partial reinitialization
* 5. onStart()
* 6. onResume()
*
* All views have unique ID:
* - variables are reinitialized
* - TextView and ButtonView are reinitialized
* - EditText, CheckBox, Switch, RadioButton are NOT reinitialized
*
* Views don't have unique ID:
* - everything is reinitialized
*
* Handling:
* 1. Via onSaveInstanceState + onRestoreInstanceState (or in onCreate)
* 2. Manually via onConfigurationChange, Activity not destroyed (NOT RECOMMENDED)
*   a) add android:configChanges="orientation|screenSize" in manifest
*   or android:configChanges="orientation" if target is < API 12
*   b) override onConfigurationChanged()
*   c) NO LIFECYCLE METHODS ARE CALLED - only onConfigurationChanged()
*   d) in onConfigurationChanged() change resources if needed */


public class MainActivity extends AppCompatActivity {

    private final String TAG = "Blya, " + MainActivity.class.getSimpleName();

    Button button1; // Go to screen 2
    Button button2; // Go to screen 3
    Button button3; // Change text button
    TextView textView;

    @Override
    /* Called when activity is first created
    * Create views
    * Attach layouts (setContentView)
    * Initialize field variables and Widgets
    * Make use of Bundle parameters to retrieve previous froze state*/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate()");
        setContentView(R.layout.activity_main);

        button1 = findViewById(R.id.button1);
        button1.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, SecondActivity.class));
        });

        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ThirdActivity.class));
        });

        textView = findViewById(R.id.textView);
        button3 = findViewById(R.id.button3);
        button3.setOnClickListener(v -> {
            textView.setText("New text");
        });

    }

    @Override
    /*This is called only if set up properly in Manifest
    * No other lifecycle methods are called if configuration gets changed
    * Not recommended by guidelines
    * Use ONLY when restoring activity is very expensive*/
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i(TAG, "onConfigurationChanged()");

        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // do smth like change image
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // do smth else
        }
    }

    @Override
    /*Only called if activity is restarted after being stopped*/
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart()");
    }

    @Override
    /* Called when activity is becoming visible to user
    * User interaction not allowed*/
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart()");
    }

    @Override
    /*Called after onStart() in case of rotation
    * Data can be restored via onCreate() as well, but this way is cleaner*/
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.i(TAG, "onRestoreInstanceState()");

        if (savedInstanceState != null) {
            textView.setText(savedInstanceState.getString("textview text"));
        }
    }

    @Override
    /* User interaction enabled
    * Activity appears at top of Activity Stack
    * Activity is completely in Foreground*/
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
    /*Activity starts to go into Background
    * Save persistent data here*/
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()");
    }

    @Override
    /*Called after onPause()*/
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState()");
        outState.putString("textview text", textView.getText().toString());
    }

    @Override
    /*User interaction stops
    * Activity completely in Background*/
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop()");
    }

    @Override
    /*Activity is about to be destroyed*/
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }
}
