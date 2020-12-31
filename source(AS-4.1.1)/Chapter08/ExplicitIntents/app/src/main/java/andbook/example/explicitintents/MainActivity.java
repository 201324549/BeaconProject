package andbook.example.explicitintents;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void mytest(View v) {
        // 암시적 인텐트를 연습한다.
        Intent intent = new Intent();
        intent.setAction("andbook.example.explicitintents.MYACTION");
//        intent.addCategory(Intent.CATEGORY_DEFAULT);
//        intent.setType("image/png");
//        intent.setData(Uri.parse("http://wwww.naver.com"));
        intent.setDataAndType(Uri.parse("http://wwww.naver.com"), "image/png");
//        intent.setAction(Intent.ACTION_MAIN);
//        intent.addCategory(Intent.CATEGORY_HOME);
//        intent.setData(Uri.parse("https://www.microsoft.com"));
//        intent.setType("image/*");
        startActivity(intent);
    }

    public void mOnClick(View v) {
        Intent intent;
        switch (v.getId()) {
        case R.id.btnTest1: // 내부 액티비티 실행
            intent = new Intent(this, SubActivity.class);
            startActivity(intent);
            break;
        case R.id.btnTest2: // 외부 액티비티 실행
            intent = new Intent();
            intent.setClassName("com.android.settings", "com.android.settings.Settings");
            startActivity(intent);
            break;
        }
    }
}
