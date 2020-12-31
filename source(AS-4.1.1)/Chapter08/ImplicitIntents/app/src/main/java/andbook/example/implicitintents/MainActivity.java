package andbook.example.implicitintents;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void mOnClick(View v) {
        Intent intent = new Intent(); // 기본 인텐트 생성
        switch (v.getId()) {
        case R.id.btnTest1: // 내부 액티비티 실행 (1)
            intent.setAction("andbook.example.implicitintents.TEST1");
            break;
        case R.id.btnTest2: // 내부 액티비티 실행 (2)
            intent.setAction("andbook.example.implicitintents.TEST2");
            intent.setType("image/png");
            break;
        case R.id.btnTest3: // 메인 액티비티 실행
            intent.setAction(Intent.ACTION_MAIN);
            break;
        case R.id.btnTest4: // '전화 걸기' 액티비티 실행
            intent.setAction(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:010-2222-3333"));
            break;
        case R.id.btnTest5: // '웹 보기' 액티비티 실행
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://www.android.com"));
            break;
        case R.id.btnTest6: // '홈 화면' 액티비티 실행
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            break;
        }
        startActivity(intent);
    }
}
