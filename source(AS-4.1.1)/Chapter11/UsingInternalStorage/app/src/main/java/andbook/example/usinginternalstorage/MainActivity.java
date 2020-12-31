package andbook.example.usinginternalstorage;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    private EditText mEditName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditName = (EditText) findViewById(R.id.editName);
    }

    public void mOnClick(View v) {
        switch (v.getId()) {
        case R.id.btnSave:
            FileOutputStream fos;
            try {
                fos = openFileOutput("test.txt", Context.MODE_PRIVATE);
                fos.write(mEditName.getText().toString().getBytes());
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            break;
        case R.id.btnLoad:
            FileInputStream fis;
            try {
                fis = openFileInput("test.txt");
                int nbytes = fis.available();
                byte[] data = new byte[nbytes];
                fis.read(data);
                mEditName.setText(new String(data));
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            break;
        }
    }
}
