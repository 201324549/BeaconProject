package andbook.example.printstreamtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void mOnClick(View v) {
        switch (v.getId()) {
        case R.id.btnOut:
            System.out.printf("%d / %d = %f%n", 10, 4, 10 / 4.0);
            break;
        case R.id.btnErr:
            System.err.printf("%.2f is a wrong answer!%n", 2.378);
            break;
        }
    }
}
