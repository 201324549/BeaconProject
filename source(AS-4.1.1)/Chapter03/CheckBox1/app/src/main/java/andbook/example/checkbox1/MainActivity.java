package andbook.example.checkbox1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private CheckBox mCheckColor, mCheckBackground;
    private TextView mTextTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCheckColor = (CheckBox) findViewById(R.id.checkColor);
        mCheckBackground = (CheckBox) findViewById(R.id.checkBackground);
        mTextTest = (TextView) findViewById(R.id.textTest);
    }

    public void mOnClick(View v) {
        switch (v.getId()) {
        case R.id.checkColor:
            if (mCheckColor.isChecked())
                mTextTest.setTextColor(0xffff0000);
            else
                mTextTest.setTextColor(0xff000000);
            break;
        case R.id.checkBackground:
            if (mCheckBackground.isChecked())
                mTextTest.setBackgroundResource(R.drawable.testpic);
            else
                mTextTest.setBackgroundResource(0);
            break;
        }
    }
}
