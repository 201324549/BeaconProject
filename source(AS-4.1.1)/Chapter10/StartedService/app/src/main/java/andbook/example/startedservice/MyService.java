package andbook.example.startedservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service { // ① [필수] 서비스 클래스 생성

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("test", "onCreate() 호출!");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // ② [필수] onStartCommand() 메서드 재정의; 수신된 인텐트 처리
        if (intent.getAction().equals("andbook.example.PLAYMUSIC")) {
//            new MusicThread().start();
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if (intent.getAction().equals("andbook.example.DOWNLOAD")) {
            new DownloadThread().start();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("test", "onDestroy() 호출!");
    }
}

class MusicThread extends Thread {
    @Override
    public void run() {
        Log.d("test", "음악 재생 시작!");
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("test", "음악 재생 종료!");
    }
}

class DownloadThread extends Thread {
    @Override
    public void run() {
        Log.d("test", "파일 다운로드 시작!");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("test", "파일 다운로드 종료!");
    }
}
