package andbook.example.notibasics;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // [참고] Android 8.0 이상에서 아래 코드를 그대로 사용하려면
    // build.gradle 파일의 targetSdkVersion을 25로 낮춰야 합니다.
    public void mOnClick(View v) {
        switch (v.getId()) {
        case R.id.btnTest:
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            Intent resultIntent = new Intent(this, SubActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                    resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setContentTitle("알림 제목");
            builder.setContentText("알림 텍스트");
            builder.setContentIntent(contentIntent);
            builder.setAutoCancel(true);

            notificationManager.notify(0, builder.build());
            break;
        }
    }
}
