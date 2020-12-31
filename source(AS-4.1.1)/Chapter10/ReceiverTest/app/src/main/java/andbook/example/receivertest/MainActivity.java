package andbook.example.receivertest;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private BroadcastReceiver mReceiver;
    private IntentFilter mFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mReceiver = new MyReceiver2();
        mFilter = new IntentFilter("andbook.example.TESTEVENT2");
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(mReceiver, mFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mReceiver);
    }

    public void mOnClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
        case R.id.btnIntent1:
            intent.setClass(this, MyReceiver1.class); // <- Android 8.0 이상에서 필요!
            // [보충 설명] Android 8.0 이상에서 브로트캐스트 수신기를 동적 등록하지 않고
            // 매니페스트에 정적 등록하면 암시적 인텐트로 활성화되지 않습니다. 이를 피해
            // 가려면 위와 같이 명시적 인텐트가 되도록 브로트캐스트 수신기 클래스 정보를
            // 넣어주어야 합니다.
            intent.setAction("andbook.example.TESTEVENT1");
            intent.putExtra("mydata", 100);
            sendBroadcast(intent);
            break;
        case R.id.btnIntent2:
            intent.setAction("andbook.example.TESTEVENT2");
            intent.putExtra("mydata", 200);
            sendBroadcast(intent);
            break;
        }
    }
}
