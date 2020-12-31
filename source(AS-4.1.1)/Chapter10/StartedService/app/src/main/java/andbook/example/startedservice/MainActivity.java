package andbook.example.startedservice;

import android.content.Intent;
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
        Intent intent = new Intent(this, MyService.class);
        switch (v.getId()) {
        case R.id.btnPlayMusic:
            intent.setAction("andbook.example.PLAYMUSIC");
            startService(intent); // ① [필수] 서비스 시작
            break;
        case R.id.btnDownload:
            intent.setAction("andbook.example.DOWNLOAD");
            startService(intent); // ① [필수] 서비스 시작
            break;
        case R.id.btnStopService:
            stopService(intent); // ② [선택] 서비스 중지(=종료)
            break;
        }
    }
}
