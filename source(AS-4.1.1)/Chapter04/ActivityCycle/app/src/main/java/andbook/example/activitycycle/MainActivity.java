package andbook.example.activitycycle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ActivityCycle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate() 호출!");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy() 호출!");
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart() 호출!");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop() 호출!");
        super.onStop();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume() 호출!");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause() 호출!");
        super.onPause();
    }
}
