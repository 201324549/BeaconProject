package andbook.example.dataexchange;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class SubActivity extends AppCompatActivity {

    private int num1;
    private int num2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        TextView textNum1 = (TextView) findViewById(R.id.textNum1);
        TextView textNum2 = (TextView) findViewById(R.id.textNum2);

        Intent intent = getIntent(); // 중요
        num1 = intent.getIntExtra("num1", -1);
        num2 = intent.getIntExtra("num2", -1);
        if (num1 != -1 && num2 != -1) {
            textNum1.setText(String.format(Locale.KOREAN, "숫자1: %d", num1));
            textNum2.setText(String.format(Locale.KOREAN, "숫자2: %d", num2));
        }
    }

    public void mOnClick(View v) {
        Intent data = new Intent();
        data.putExtra("result", num1 * num2);
        setResult(RESULT_OK, data);
        finish(); // 중요!!
    }
}
