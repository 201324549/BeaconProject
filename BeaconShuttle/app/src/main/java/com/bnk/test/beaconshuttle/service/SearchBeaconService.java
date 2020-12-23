package com.bnk.test.beaconshuttle.service;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.bnk.test.beaconshuttle.MainActivity;
import com.bnk.test.beaconshuttle.R;
import com.bnk.test.beaconshuttle.model.BeaconServiceSettings;
import com.bnk.test.beaconshuttle.util.Global;
import com.minew.beaconset.BluetoothState;
import com.minew.beaconset.MinewBeacon;
import com.minew.beaconset.MinewBeaconManager;
import com.minew.beaconset.MinewBeaconManagerListener;

import java.util.Collection;
import java.util.List;

/**
 *  library minewBeacon 사용
 *
 * @author Minseok Kim
 * on 2020.12.23
 */
public class SearchBeaconService extends Service {

    private final String TAG = "BeaconService";
    private BeaconServiceSettings serviceSettings;
    private MinewBeaconManager beaconManager;
    private final IBinder binder = new SearchBeaconServiceBinder();
    private ICallback callBackToActivity;

    private Boolean checkFlag;
    private Handler mHandler;

    public interface ICallback {
        void serviceStarted();
        void searchStart();
        void searchStop();
        void foundBeacons(Collection<MinewBeacon> beacons);
        void serviceClosed(); // 사용자가 서비스를 직접 종료했을 때
    }

    public class SearchBeaconServiceBinder extends Binder {
        public SearchBeaconService getService() {
            return SearchBeaconService.this;
        }
    }

    public BluetoothState getBluetoothState() {
        return beaconManager.checkBluetoothState();
    }

    public void registerCallback(ICallback cb) {
        callBackToActivity = cb;
        callBackToActivity.serviceStarted();
        searchStart();
    }

    public void searchStart() {
        if (beaconManager == null) {
            return;
        }
        beaconManager.startService();
        Log.d(TAG, "searchStart");

        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            public void run() {
                Log.i(TAG, "SEARCHING TIME's OVER");
                searchStop();
                return;
            }
        }, 3000L); // 3초후

        beaconManager.startScan();

        if (callBackToActivity != null) {
            callBackToActivity.searchStart();
        }
    }

    public void searchStop() {
        if (beaconManager == null) {
            return;
        }
        Log.d(TAG, "stopScan()");
        beaconManager.stopScan();
        callBackToActivity.searchStop();
    }

    public void foundBeacons(List<MinewBeacon> beacons) {
        if (beaconManager == null) {
            return;
        }

        mHandler.removeMessages(0);
        callBackToActivity.foundBeacons(beacons);
    }

    private void init() {
        beaconManager = MinewBeaconManager.getInstance(this);
        beaconManager.setMinewbeaconManagerListener(new MinewBeaconManagerListener() {
            @Override
            public void onUpdateBluetoothState(BluetoothState bluetoothState) {
                switch (bluetoothState) {
                    case BluetoothStatePowerOff:
                        Toast.makeText(getApplicationContext(), "bluetooth off", Toast.LENGTH_SHORT).show();
                        break;
                    case BluetoothStatePowerOn:
                        Toast.makeText(getApplicationContext(), "bluetooth on", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onRangeBeacons(List<MinewBeacon> beacons) {
                Log.d(TAG, "onRangeBeacons");
                if (beacons.size() >= 1) {
                    foundBeacons(beacons);
                }
            }

            @Override
            public void onAppearBeacons(List<MinewBeacon> beacons) {
                foundBeacons(beacons);
            }

            @Override
            public void onDisappearBeacons(List<MinewBeacon> beacons) {
                Log.d(TAG, "onDisappearBeacons");
            }
        });

        startServiceForeground();
    }

    public void startServiceForeground() {
        Log.d(TAG, "START BEACON SEARCH");

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent closeAction = new Intent(this, SearchBeaconService.class);
        closeAction.setAction(Global.SERVICE_CLOSE);
        PendingIntent closePending = PendingIntent.getService(this, 0,
                closeAction, PendingIntent.FLAG_CANCEL_CURRENT);

        // 진동, 사운드 X
        // 알림 탭 시 Intent 발생
        NotificationCompat.Builder notiBuilder = new NotificationCompat.Builder(this, getString(R.string.bnk_noti_channel_id))
                .setSmallIcon(R.drawable.ic_small_notification)
                .setContentTitle(getString(R.string.beacon_found_noti_title))
                .setPriority(NotificationCompat.PRIORITY_LOW) // 진동, 사운드X
                .setContentIntent(pendingIntent) // 알림 탭 시 Intent발생
                .addAction(R.drawable.ic_baseline_close_24, "종료", closePending)
                .setAutoCancel(true); // true이면 사용자가 노티를 터치했을 때 사라짐
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            notiBuilder.setColor(getColor(R.color.color_bnk_primary)); //아이콘 색상
        } else {
            notiBuilder.setColor(ContextCompat.getColor(this, R.color.color_bnk_primary));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Oreo 이상에서 Notificatiion 채널을 먼저 만들지 않으면
            // invalid channel for service notification 에러 발생.
            NotificationManager manager = (NotificationManager)
                    getSystemService(Context.NOTIFICATION_SERVICE);

            if (manager != null && manager.getNotificationChannel(getString(R.string.bnk_noti_channel_id))==null) {
                manager.createNotificationChannel(
                        new NotificationChannel(
                                getString(R.string.bnk_noti_channel_id),
                                getString(R.string.bnk_noti_channel_name),
                                NotificationManager.IMPORTANCE_MIN)
                );
            }
        }
        startForeground(Global.NOTI_ID_SERVICE_IS_RUNNING, notiBuilder.build()); // FOREGROUND_SERVICE 권한 필요.
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind!");
        if (intent != null) {
            serviceSettings = intent.getParcelableExtra(Global.SERVICE_SETTINGS);
        }
        init();
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind");
        searchStop();
        return super.onUnbind(intent);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.d(TAG, "onTaskRemoved");
        super.onTaskRemoved(rootIntent);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction() != null && intent.getAction().equals(Global.SERVICE_CLOSE)) {
//            stopSelf is called but the service is not destroyed as the foreground activity
//            is still bound. When I leave the activity by pressing back or going to home screen,
//            then the service is destroyed. In short,
//            stopSelf won't work when a foreground activity

            if (callBackToActivity != null) {
                callBackToActivity.serviceClosed();
            }
            stopForeground(true);
            stopSelfResult(startId);
            return START_NOT_STICKY;
        }

        return super.onStartCommand(intent, flags, startId);
    }

    public void setCheckFlag(boolean flag) { checkFlag = flag; }
}
