package andbook.example.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView mTextDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextDisplay = (TextView) findViewById(R.id.textDisplay);
    }

    public void mOnClick(View v) {
        switch (v.getId()) {
        case R.id.btnMode:
            break;
        case R.id.btn0:
            break;
        case R.id.btn1:
            break;
        case R.id.btn2:
            break;
        case R.id.btn3:
            break;
        case R.id.btn4:
            break;
        case R.id.btn5:
            break;
        case R.id.btn6:
            break;
        case R.id.btn7:
            break;
        case R.id.btn8:
            break;
        case R.id.btn9:
            break;
        case R.id.btnDot:
            break;
        case R.id.btnAdd:
            break;
        case R.id.btnSub:
            break;
        case R.id.btnMul:
            break;
        case R.id.btnDiv:
            break;
        case R.id.btnDel:
            break;
        case R.id.btnEql:
            break;
        }
        Toast.makeText(this, ((Button) v).getText(), Toast.LENGTH_SHORT).show();
    }
}
