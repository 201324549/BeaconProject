package andbook.example.notifications;

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
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        switch (v.getId()) {
        case R.id.btnTest1:
            Intent resultIntent1 = new Intent(this, SubActivity.class);
            PendingIntent contentIntent1 = PendingIntent.getActivity(this, 0,
                    resultIntent1, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder builder1 = new NotificationCompat.Builder(this);
            builder1.setSmallIcon(R.mipmap.ic_launcher);
            builder1.setContentTitle("첫 번째 알림 제목");
            builder1.setContentText("첫 번째 알림 텍스트");
            builder1.setContentIntent(contentIntent1);
            builder1.setAutoCancel(true);

            notificationManager.notify(0, builder1.build());
            break;
        case R.id.btnTest2:
            Intent resultIntent2 = new Intent(this, SubActivity.class);
            PendingIntent contentIntent2 = PendingIntent.getActivity(this, 0,
                    resultIntent2, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder builder2 = new NotificationCompat.Builder(this);
            builder2.setSmallIcon(R.mipmap.ic_launcher);
            builder2.setContentTitle("두 번째 알림 제목");
            builder2.setContentText("두 번째 알림 텍스트");
            builder2.setContentIntent(contentIntent2);
            builder2.setAutoCancel(true);

            notificationManager.notify(1, builder2.build());
            break;
        }
    }
}
