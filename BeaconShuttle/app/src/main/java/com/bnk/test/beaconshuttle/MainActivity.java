package com.bnk.test.beaconshuttle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.ServiceConnection;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

import com.bnk.test.beaconshuttle.service.SearchBeaconService;
import com.google.android.gms.location.FusedLocationProviderClient;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "BEACON";
    private SearchBeaconService searchBeaconService;
    private ServiceConnection serviceConnection;
    private static final int REQUEST_ENABLE_BT = 2;
    private static final int REQUEST_GPS_BT = 3;
    private LocationManager locationManager;
    private FusedLocationProviderClient fusedLocationProviderClient;

    /**
     * VIEW 작성해야함.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void mOnClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        ImageButton imageButton = findViewById(R.id.imageButton);
        View layout = inflater.inflate(R.layout.custom_dialog, null);
        builder.setView(layout);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create().show();// 대화상자 객체 생성&화면보이기
    }
}