package andbook.example.boundservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private MyService mService;
    private boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    ServiceConnection mConn = new ServiceConnection() { // ① ServiceConnection 객체 생성

        // 서비스와 연결이 성공하면 자동 호출
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // ③ 바인더 객체의 메서드(예: getService()) 호출; 서비스 객체 얻기
            mService = ((MyService.LocalBinder) service).getService();
            mBound = true;
        }

        // 서비스와 연결이 강제로 끊어지면 자동 호출된다.
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MyService.class);    // 명시적 인텐트(바인드된 서비스는 반드시 명시적 인텐트 사용 !!)
        bindService(intent, mConn, Context.BIND_AUTO_CREATE); // ② 서비스와 연결하기
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            unbindService(mConn); // ⑤ 서비스와 연결 해제
            mBound = false;
        }
    }

    public void mOnClick(View v) {
        switch (v.getId()) {
        case R.id.btnCalc:
            if (mBound) {
                EditText editNum1 = (EditText) findViewById(R.id.editNum1);
                EditText editNum2 = (EditText) findViewById(R.id.editNum2);
                if (editNum1.length() > 0 && editNum2.length() > 0) {
                    int num1 = Integer.parseInt(editNum1.getText().toString());
                    int num2 = Integer.parseInt(editNum2.getText().toString());
                    // ④ 서비스 객체를 통해 서비스 메서드 호출
                    int result = mService.CalcNum(num1, num2);
                    Toast.makeText(this, "계산 결과 = " + result, Toast.LENGTH_SHORT).show();
                }
            }
            break;
        }
    }
}
