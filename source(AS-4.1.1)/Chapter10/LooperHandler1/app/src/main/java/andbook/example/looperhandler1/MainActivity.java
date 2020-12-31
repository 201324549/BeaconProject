package andbook.example.looperhandler1;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private MyThread mThread;
    private int mNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mThread = new MyThread();
        mThread.start(); // 스레드 시작
    }

    @Override
    protected void onStop() {
        super.onStop();
        Message msg = Message.obtain();
        msg.what = -1;
        mThread.mHandler.sendMessage(msg); // 메시지 보내기; 루퍼 & 스레드 종료
    }

    public void mOnClick(View v) {
        Message msg = Message.obtain();
        msg.what = 0;
        msg.arg1 = ++mNumber;
        mThread.mHandler.sendMessage(msg); // 메시지 보내기; 처리할 숫자 데이터
    }

    private class MyThread extends Thread {

        public Handler mHandler;

        @Override
        public void run() {
            Looper.prepare(); // 루퍼 초기화

            mHandler = new Handler() { // 핸들러 생성
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 0) {
                        // 계산 지연 시간
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // 계산 결과 출력
                        int result = msg.arg1 * msg.arg1;
                        Log.d("test", msg.arg1 + " * " + msg.arg1 + " = " + result);
                    } else if (msg.what == -1) {
                        Looper.myLooper().quit(); // 루퍼 종료 → 스레드 종료
                        Log.d("test", "루퍼를 종료합니다.");
                    }
                }
            };

            Looper.loop(); // 루퍼 실행
            Log.d("test", "스레드를 종료합니다.");
        }
    }
}
