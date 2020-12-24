package com.bnk.test.beaconshuttle.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BluetoothAutoRunReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("BluetoothAutoRun","ON RECEIVE !");
        final String action = intent.getAction();

//        if(action!=null && action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)){
//            final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
//            switch(state){
//                case BluetoothAdapter.STATE_OFF:
//                    break;
//                case BluetoothAdapter.STATE_TURNING_ON:
//                    Intent autoRun = new Intent(context, BeaconActivity.class);
//                    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
//                            autoRun, PendingIntent.FLAG_UPDATE_CURRENT);
//                    NotificationCompat.Builder notiBuilder =
//                            new NotificationCompat.Builder(context, context.getString(R.string.bnk_noti_id))
//                            .setSmallIcon(R.drawable.ic_small_notification)
//                            .setContentTitle(context.getString(R.string.beacon_found_noti_result))
//                            .setPriority(NotificationCompat.PRIORITY_LOW) // 진동,사운드 X
//                            .setContentIntent(pendingIntent) // 알림 탭 시 intent 발생
//                            .setAutoCancel(true); //  true이면 사용자가 노티를 터치했을 때 사라지게 합니다.
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                        notiBuilder.setColor(context.getColor(R.color.color_bnk_primary)); // 아이콘 색상
//                    }else{
//                        notiBuilder.setColor(ContextCompat.getColor(context,R.color.color_bnk_primary));
//                    }
//                    NotificationManager manager =
//                            (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//                    if(manager!=null){
//                        manager.notify(Global.NOTI_ID_START_BLUETOOTH,notiBuilder.build());
//                    }
//                    break;
//            }
//        }
    }
}
