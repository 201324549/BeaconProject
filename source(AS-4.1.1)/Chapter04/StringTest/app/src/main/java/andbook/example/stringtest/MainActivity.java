package andbook.example.stringtest;

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
        switch (v.getId()) {
        case R.id.btnMethod1:
            // 방법1
            Toast.makeText(this, R.string.msg4, Toast.LENGTH_LONG).show();
            break;
        case R.id.btnMethod2:
            // 방법2
            String str1 = getResources().getString(R.string.msg4);
            Toast.makeText(this, str1, Toast.LENGTH_LONG).show();
            break;
        case R.id.btnMethod3:
            // 방법3
            CharSequence str2 = getResources().getText(R.string.msg4);
            Toast.makeText(this, str2, Toast.LENGTH_LONG).show();
            break;
        }
    }
}
