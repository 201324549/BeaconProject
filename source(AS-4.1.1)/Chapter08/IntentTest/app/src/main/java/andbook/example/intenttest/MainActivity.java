package andbook.example.intenttest;

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
        intent.setAction(Intent.ACTION_VIEW); // 액션 지정
        intent.setDataAndType(
                Uri.parse("https://www.google.co.kr/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png"),
                "image/*"); // 위치(URI)와 타입(MIME 타입) 지정
        intent.addCategory(Intent.CATEGORY_DEFAULT); // 카테고리 지정
        startActivity(intent); // 액티비티 실행
    }
}
