package andbook.example.valuestest;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void mOnClick(View v) {
        Resources res = getResources();
        switch (v.getId()) {
        case R.id.btnGetBool:
            boolean bool_value = res.getBoolean(R.bool.test_bool);
            Toast.makeText(this, "부울값 = " + bool_value, Toast.LENGTH_SHORT).show();
            break;
        case R.id.btnGetInt:
            int int_value = res.getInteger(R.integer.test_int);
            Toast.makeText(this, "정숫값 = " + int_value, Toast.LENGTH_SHORT).show();
            break;
        }
    }
}
