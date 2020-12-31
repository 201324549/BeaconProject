package andbook.example.receivertest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver2 extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int data = intent.getIntExtra("mydata", -1);
        Toast.makeText(context, "MyReceiver2: mydata = " + data,
                Toast.LENGTH_SHORT).show();
    }
}
