package andbook.example.detectsystemevent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver2 extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
            int state = intent.getIntExtra("state", -1);
            Toast.makeText(context, "ACTION_HEADSET_PLUG: state = " + state,
                    Toast.LENGTH_SHORT).show();
        }
    }
}
