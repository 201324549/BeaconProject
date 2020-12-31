package andbook.example.usingexternalstorage;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.io.File;
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
        FileOutputStream fos;
        FileInputStream fis;

        if (!StorageCheck.isExternalStorageWritable())
            return;

        switch (v.getId()) {
        case R.id.btnSave1: // 저장하기(외부 공용 저장소)
            try {
                File dir = Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS);
                fos = new FileOutputStream(dir.getAbsolutePath() + "/test.txt");
                fos.write(mEditName.getText().toString().getBytes());
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            break;
        case R.id.btnLoad1: // 읽어오기(외부 공용 저장소)
            try {
                File dir = Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS);
                fis = new FileInputStream(dir.getAbsolutePath() + "/test.txt");
                int nbytes = fis.available();
                byte[] data = new byte[nbytes];
                fis.read(data);
                mEditName.setText(new String(data));
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            break;
        case R.id.btnSave2: // 저장하기(외부 전용 저장소)
            try {
                File[] dirs = ContextCompat.getExternalFilesDirs(this, null);
                fos = new FileOutputStream(dirs[0].getAbsolutePath() + "/test.txt");
                fos.write(mEditName.getText().toString().getBytes());
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            break;
        case R.id.btnLoad2: // 읽어오기(외부 전용 저장소)
            try {
                File[] dirs = ContextCompat.getExternalFilesDirs(this, null);
                fis = new FileInputStream(dirs[0].getAbsolutePath() + "/test.txt");
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

class StorageCheck {

    /* 외부 저장소가 읽기/쓰기가 가능한지 체크한다. */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    /* 외부 저장소가 읽기가 가능한지 체크한다. */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED) ||
                state.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
            return true;
        }
        return false;
    }
}
