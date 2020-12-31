package andbook.example.boundservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class MyService extends Service { // ① [필수] 서비스 클래스 생성

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // ③ [필수] onBind() 메서드 재정의; 바인더 객체 생성 & 리턴
        return new LocalBinder();
    }

    public class LocalBinder extends Binder {
        // ② [필수] 바인더 클래스 정의; 현재의 서비스 객체 리턴
        MyService getService() {
            return MyService.this;
        }
    }

    public int CalcNum(int m, int n) { // ④ [필수] 서비스 메서드 추가
        return m * n;
    }
}
