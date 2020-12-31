package andbook.example.savestate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int mNumber;
    private TextView mTextNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        if (savedInstanceState != null) {
//            mNumber = savedInstanceState.getInt("number", 0);
//        }

        if (savedInstanceState != null) {
            mNumber = savedInstanceState.getInt("mNumber", 0);
        }

        mTextNumber = (TextView) findViewById(R.id.textNumber);
        mTextNumber.setText(mNumber + "");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("mNumber", mNumber);
    }

    //    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putInt("number", mNumber);
//    }

    public void mOnClick(View v) {
        mNumber++;
        mTextNumber.setText(mNumber + "");
    }
}
