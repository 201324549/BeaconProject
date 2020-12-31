package andbook.example.edittext1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText mEditPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditPassword = (EditText) findViewById(R.id.editPassword);
    }

    public void mOnClick(View v) {
        String password = mEditPassword.getText().toString();
        if (password.length() > 0) {
            Toast.makeText(this, "암호: " + password, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "암호를 먼저 입력하세요!", Toast.LENGTH_SHORT).show();
        }
    }
}
