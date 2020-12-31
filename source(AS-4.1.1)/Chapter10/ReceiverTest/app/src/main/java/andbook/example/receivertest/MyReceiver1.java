package andbook.example.receivertest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver1 extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int data = intent.getIntExtra("mydata", -1);
        Toast.makeText(context, "MyReceiver1: mydata = " + data,
                Toast.LENGTH_SHORT).show();
    }
}
