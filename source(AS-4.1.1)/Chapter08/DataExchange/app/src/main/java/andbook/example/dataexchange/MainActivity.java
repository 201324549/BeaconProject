package andbook.example.dataexchange;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText mEditNum1;
    private EditText mEditNum2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditNum1 = (EditText) findViewById(R.id.editNum1);
        mEditNum2 = (EditText) findViewById(R.id.editNum2);
    }

    public void mOnClick(View v) {
        Intent intent = new Intent(this, SubActivity.class); // 명시적 인텐트를 준비한다.
        intent.putExtra("num1", Integer.parseInt(mEditNum1.getText().toString()));
        intent.putExtra("num2", Integer.parseInt(mEditNum2.getText().toString()));
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == RESULT_OK) {
            int result = data.getIntExtra("result", 0);
            Toast.makeText(this, "두 숫자의 곱: " + result, Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
