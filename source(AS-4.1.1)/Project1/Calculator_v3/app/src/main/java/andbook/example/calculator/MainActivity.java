package andbook.example.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextDisplay;
    private CheckBox mBtnMode;
    private Button mBtnPer;
    private Button mBtnPow;
    private StringBuilder mMathExpr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextDisplay = (TextView) findViewById(R.id.textDisplay);
        mBtnMode = (CheckBox) findViewById(R.id.btnMode);
        mBtnPer = (Button) findViewById(R.id.btnPer);
        mBtnPow = (Button) findViewById(R.id.btnPow);

        mMathExpr = new StringBuilder(256);
        if (savedInstanceState != null) {
            String expr = savedInstanceState.getString("MathExpr");
            if (expr != null) {
                mMathExpr.append(expr);
                mTextDisplay.setText(expr);
            }
            boolean isChecked = savedInstanceState.getBoolean("isChecked");
            if (isChecked) {
                mBtnPer.setVisibility(View.VISIBLE);
                mBtnPow.setVisibility(View.VISIBLE);
            } else {
                mBtnPer.setVisibility(View.INVISIBLE);
                mBtnPow.setVisibility(View.INVISIBLE);
            }
        }

        Button BtnDel = (Button) findViewById(R.id.btnDel);
        BtnDel.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mMathExpr.delete(0, mMathExpr.length());
                mTextDisplay.setText(mMathExpr);
                return true;
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mMathExpr.length() > 0) {
            outState.putString("MathExpr", mMathExpr.toString());
        }
        outState.putBoolean("isChecked", mBtnMode.isChecked());
    }

    public void mOnClick(View v) {
        switch (v.getId()) {
        case R.id.btnMode:
            if (mBtnMode.isChecked()) {
                mBtnPer.setVisibility(View.VISIBLE);
                mBtnPow.setVisibility(View.VISIBLE);
            } else {
                mBtnPer.setVisibility(View.INVISIBLE);
                mBtnPow.setVisibility(View.INVISIBLE);
            }
            break;
        case R.id.btn0:
            mMathExpr.append("0");
            break;
        case R.id.btn1:
            mMathExpr.append("1");
            break;
        case R.id.btn2:
            mMathExpr.append("2");
            break;
        case R.id.btn3:
            mMathExpr.append("3");
            break;
        case R.id.btn4:
            mMathExpr.append("4");
            break;
        case R.id.btn5:
            mMathExpr.append("5");
            break;
        case R.id.btn6:
            mMathExpr.append("6");
            break;
        case R.id.btn7:
            mMathExpr.append("7");
            break;
        case R.id.btn8:
            mMathExpr.append("8");
            break;
        case R.id.btn9:
            mMathExpr.append("9");
            break;
        case R.id.btnDot:
            mMathExpr.append(".");
            break;
        case R.id.btnAdd:
            mMathExpr.append("+");
            break;
        case R.id.btnSub:
            mMathExpr.append("-");
            break;
        case R.id.btnMul:
            mMathExpr.append("*");
            break;
        case R.id.btnDiv:
            mMathExpr.append("/");
            break;
        case R.id.btnPer:
            mMathExpr.append("%");
            break;
        case R.id.btnPow:
            mMathExpr.append("^");
            break;
        case R.id.btnDel:
            if (mMathExpr.length() > 0)
                mMathExpr.deleteCharAt(mMathExpr.length() - 1);
            break;
        case R.id.btnEql:
            if (mMathExpr.length() == 0)
                return;
            // 중위 표기법 → 후위 표기법 → 스택으로 계산 → 결과 리턴
            String result = NumCalc.calc(mMathExpr.toString());
            if (result != null) {
                mMathExpr.delete(0, mMathExpr.length());
                mMathExpr.append(result);
            }
            break;
        }
        mTextDisplay.setText(mMathExpr);
    }
}
