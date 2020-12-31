package andbook.example.logtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "LogTest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void mOnClick(View v) {
        switch (v.getId()) {
        case R.id.btnAssert:
            Log.wtf(TAG, "Assert message");
            break;
        case R.id.btnError:
            Log.e(TAG, "Error message");
            break;
        case R.id.btnWarn:
            Log.w(TAG, "Warn message");
            break;
        case R.id.btnInfo:
            Log.i(TAG, "Info message");
            break;
        case R.id.btnDebug:
            Log.d(TAG, "Debug message");
            break;
        case R.id.btnVerbose:
            Log.v(TAG, "Verbose message");
            break;
        }
    }
}
