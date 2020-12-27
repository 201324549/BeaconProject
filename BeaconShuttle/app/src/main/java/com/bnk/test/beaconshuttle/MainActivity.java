package com.bnk.test.beaconshuttle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bnk.test.beaconshuttle.model.BeaconServiceSettings;
import com.bnk.test.beaconshuttle.model.User;
import com.bnk.test.beaconshuttle.service.SearchBeaconService;
import com.bnk.test.beaconshuttle.util.DataHelper;
import com.bnk.test.beaconshuttle.util.Global;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.minew.beaconset.MinewBeacon;

import org.w3c.dom.Text;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "BEACON";
    private SearchBeaconService searchBeaconService;
    private ServiceConnection serviceConnection;
    private AlertDialog dialog;
    private static final int REQUEST_ENABLE_BT = 2;
    private static final int REQUEST_GPS_BT = 3;
    private LocationManager locationManager;
    private FusedLocationProviderClient fusedLocationProviderClient;


    /**
     * DataBase using SQLite
     */
    private DataHelper mDataHelper;
    private SQLiteDatabase db;
    private Cursor cur;
    private String[] beaconProjection = {"BCN_ID", "BCN_NM"};
    private HashMap<String, String> beaconMap = new HashMap<>();
    /**
     * VIEW 작성해야함.
     */
    private TextView userTextView;
    private TextView timeTextView;
    private TextView dateTextView;
    private TextView comGroupTextView;
    private TextView userNameTextView;
    private TextView infoTextView;
    private ImageButton beaconButton;

    private String rot;
    private boolean mBound = false;
    private String[] days = {"일", "월", "화", "수", "목", "금", "토"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initDataBase();
        startPermissions();
    }

    private void initView() {
        userTextView = findViewById(R.id.userTextView);
        infoTextView = findViewById(R.id.infoTextView);
        beaconButton = findViewById(R.id.beaconButton);
        User user = getUserInfo();
        userTextView.setText(String.format("%s %s님\n안녕하세요.", user.getUser_nm(), user.getEmpe_psit_nm()));
    }

    private void initDataBase() {
        mDataHelper = new DataHelper(this);
        createDataBase();
        db = mDataHelper.getReadableDatabase();
        cur = db.query("BO_STBM_BCN", beaconProjection, null, null, null, null, null);

        if (cur != null) {
            int bcnIdCol = cur.getColumnIndex("BCN_ID");
            int bcnNmCol = cur.getColumnIndex("BCN_NM");
            while (cur.moveToNext()) {
                String bcnId = cur.getString(bcnIdCol);
                String bcnNm = cur.getString(bcnNmCol);
                Log.d(TAG, String.format("%s, %s called", bcnId, bcnNm));
                beaconMap.put(bcnId, bcnNm);
            }

            cur.close();
        }
    }

    private void createDataBase() {
        try {
            mDataHelper.createDataBase();
        } catch (IOException e) {
            Log.e(TAG, e.toString() + "  UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
    }

    private User getUserInfo() {
        User u = new User();
        u.setCmgrp_cd("BNK시스템");
        u.setEmpe_psit_nm("파트너");
        u.setUser_nm("김민석");
        return u;
    }

    private final SearchBeaconService.ICallback callback = new SearchBeaconService.ICallback() {
        @Override
        public void serviceStarted() {

        }

        @Override
        public void searchStart() {
            setBeaconText(null, null, null);
            Toast.makeText(MainActivity.this, "비콘 검색 시작", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void searchStop() {
            Toast.makeText(MainActivity.this, "비콘 검색 종료", Toast.LENGTH_SHORT).show();
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void foundBeacons(Collection<MinewBeacon> beacons){
            if (beacons != null && beacons.size() > 0) {
                for (MinewBeacon beacon : beacons) {
                    String beaconKey = beacon.getUuid() + beacon.getMajor() + beacon.getMinor();
                    beaconKey = beaconKey.replaceAll("-", "");
                    String bType = beaconMap.getOrDefault(beaconKey, Global.UNKNOWN);
                    Log.d(TAG, String.format("Beacon Key: %s called", beaconKey));
                    Log.d(TAG, String.format("BTYPE : %s called", bType));
                    Log.d(TAG, String.format("Beacon Name : %s called", beacon.getName()));
                    if(!bType.equals(Global.UNKNOWN)) {
                        rot = bType;
                        Log.d(TAG, String.format("ROT : %s called", rot));
                        if (searchBeaconService != null) {
                            searchBeaconService.searchStop();
                        }
                        return;
                    }
                }

                // 조회된 결과 중 일치하는 비콘이 없을 경우
                searchBeaconService.searchStop();
            }
        }

        @Override
        public void serviceClosed() {
            unbindService(serviceConnection);
            serviceConnection = null;
            searchBeaconService = null;
        }
    };

    public void mOnClick(View v) {
        startPermissions();
        if (mBound) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.custom_dialog, null);
            setBeaconText(layout, getUserInfo(), rot);

            Button button3 = layout.findViewById(R.id.button3);
            button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            builder.setView(layout);
            dialog = builder.create();// 대화상자 객체 생성&화면보이기
            dialog.show();
        } else {
            Toast.makeText(v.getContext(), "비콘 검색 실패", Toast.LENGTH_SHORT).show();
        }
    }

    private void setBeaconText(View v, User user, String busType) {
        if (v == null) {
            return;
        }
        Date date = new Date(System.currentTimeMillis());
        String time = new SimpleDateFormat("HH:mm:ss").format(date);
        String today = new SimpleDateFormat("yyyy.MM.dd").format(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        today = String.format("%s(%s)", today, days[day-1]);
        Log.d(TAG, "setBeaconText() called");
        timeTextView = v.findViewById(R.id.timeTextView);
        dateTextView = v.findViewById(R.id.dateTextView);
        comGroupTextView = v.findViewById(R.id.comGroupTextView);
        userNameTextView = v.findViewById(R.id.userNameTextView);
        if (user != null && busType != null ) {
            timeTextView.setText(time);
            dateTextView.setText(today);
            comGroupTextView.setText(user.getCmgrp_cd());
            userNameTextView.setText(user.getUser_nm());
        } else {
            timeTextView.setText(time);
            dateTextView.setText(today);
            comGroupTextView.setText("인식 실패");
            userNameTextView.setText("실패");
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                String alertTitle = "셔틀버스시스템";
                String buttonMessage = "앱을 종료하시겠습니까?";
                String buttonYes = "예";
                String buttonNo = "아니오";

                new android.app.AlertDialog.Builder(MainActivity.this)
                        .setTitle(alertTitle)
                        .setMessage(buttonMessage)
                        .setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                moveTaskToBack(true);
                                finish();
                            }
                        })
                        .setNegativeButton(buttonNo, null)
                        .show();
        }
        return true;
    }


    private void startPermissions() {
        String[] permissions;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            permissions = new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.BLUETOOTH,
                    Manifest.permission.BLUETOOTH_ADMIN,
                    Manifest.permission.FOREGROUND_SERVICE
            };
        } else {
            permissions = new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.BLUETOOTH,
                    Manifest.permission.BLUETOOTH_ADMIN,
            };
        }

        Dexter.withContext(this)
                .withPermissions(
                        permissions
                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                if (!multiplePermissionsReport.areAllPermissionsGranted()) {
                    beaconButton.setEnabled(false);
                    infoTextView.setText("허용되지 않은 권한이 존재합니다.");
                } else {
                    checkBluetooth();
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken permissionToken) {
                beaconButton.setEnabled(false);
                infoTextView.setText("허용되지 않은 권한이 존재합니다.\n권한 설정을 변경해 주세요.");
                for (PermissionRequest req : permissions) {
                    Log.d(TAG, "::req - " + req.getName());
                }
            }
        }).check();
    }

    private void startService() {
        final BeaconServiceSettings serviceSettings = new BeaconServiceSettings(3000L);
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                SearchBeaconService.SearchBeaconServiceBinder binder =
                        (SearchBeaconService.SearchBeaconServiceBinder) service;
                searchBeaconService = binder.getService();
                searchBeaconService.registerCallback(callback);
                mBound = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                serviceConnection = null;
                searchBeaconService = null;
                mBound = false;
            }
        };

        Intent serviceIntent = new Intent(this, SearchBeaconService.class);
        serviceIntent.putExtra(Global.SERVICE_SETTINGS, serviceSettings);
        bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    /**
     * check Bluetooth state
     */
    private void checkBluetooth() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) {
            Toast.makeText(this, "기기가 블루투스를 지원하지 않습니다.", Toast.LENGTH_LONG).show();
            finish();
        } else if (!bluetoothAdapter.isEnabled()) {
            showBLEDialog();
        } else {
            checkGPS();
        }
    }

    /**
     * check GPS state
     */
    private void checkGPS() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null) {
            Toast.makeText(this, "기기가 GPS를 지원하지 않습니다.", Toast.LENGTH_LONG).show();
            finish();
        } else if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            showGPSDialog();
        } else {
            startService();
        }
    }

    /**
     * 블루투스 켜기
     */
    private void showBLEDialog() {
        Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
    }

    /**
     * 위치설정 켜기
     */
    private void showGPSDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("비콘 검색을 위해 위치 설정을 사용해 주세요")
                .setCancelable(false)
                .setPositiveButton("설정 열기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS),
                                REQUEST_GPS_BT);
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        beaconButton.setEnabled(false);
                        infoTextView.setText("비콘 검색을 위해 위치설정을 사용해 주세요.");
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                checkGPS();
            }
        } else if (requestCode == REQUEST_GPS_BT) {
            checkGPS();
        }
    }

    @Override
    public void onBackPressed() {
        if (searchBeaconService != null && serviceConnection != null) {
            unbindService(serviceConnection);
            serviceConnection = null;
            searchBeaconService = null;
            stopService(new Intent(MainActivity.this, SearchBeaconService.class));
            mBound = false;
        }
        finish();
        super.onBackPressed();
    }

    public void successSubmit() {
        String alertTitle = "안내메시지";
        String buttonMessage = "정상적으로 입력되었습니다.";
        String buttonYes = "확인";

        new android.app.AlertDialog.Builder(MainActivity.this)
                .setTitle(alertTitle)
                .setMessage(buttonMessage)
                .setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    public void coronaClick(View v){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.popup_corona, null);
        builder.setView(layout);
        android.app.AlertDialog Ad = builder.create();
        Ad.show();

        Button submitBtn = (Button)layout.findViewById(R.id.coronaSubmit);
        submitBtn.setEnabled(false);
        RadioButton rb1_1 = (RadioButton)layout.findViewById(R.id.cronaq1yes);
        RadioButton rb1_2 = (RadioButton)layout.findViewById(R.id.cronaq1no);
        RadioButton rb2_1 = (RadioButton)layout.findViewById(R.id.cronaq2yes);
        RadioButton rb2_2 = (RadioButton)layout.findViewById(R.id.cronaq2no);
        RadioButton rb3_1 = (RadioButton)layout.findViewById(R.id.cronaq3yes);
        RadioButton rb3_2 = (RadioButton)layout.findViewById(R.id.cronaq3no);
        RadioButton rb4_1 = (RadioButton)layout.findViewById(R.id.cronaq4yes);
        RadioButton rb4_2 = (RadioButton)layout.findViewById(R.id.cronaq4no);
        RadioButton rb5_1 = (RadioButton)layout.findViewById(R.id.cronaq5yes);
        RadioButton rb5_2 = (RadioButton)layout.findViewById(R.id.cronaq5no);
        RadioButton rb6_1 = (RadioButton)layout.findViewById(R.id.cronaq6yes);
        RadioButton rb6_2 = (RadioButton)layout.findViewById(R.id.cronaq6no);
        RadioButton rb7_1 = (RadioButton)layout.findViewById(R.id.cronaq7yes);
        RadioButton rb7_2 = (RadioButton)layout.findViewById(R.id.cronaq7no);

        rb1_1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if((rb1_1.isChecked() || rb1_2.isChecked()) &&
                        (rb2_1.isChecked() || rb2_2.isChecked()) &&
                        (rb3_1.isChecked() || rb3_2.isChecked()) &&
                        (rb4_1.isChecked() || rb4_2.isChecked()) &&
                        (rb5_1.isChecked() || rb5_2.isChecked()) &&
                        (rb6_1.isChecked() || rb6_2.isChecked()) &&
                        (rb7_1.isChecked() || rb7_2.isChecked())
                ) {
                    submitBtn.setEnabled(true);
                }
            }
        });

        rb1_2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if((rb1_1.isChecked() || rb1_2.isChecked()) &&
                        (rb2_1.isChecked() || rb2_2.isChecked()) &&
                        (rb3_1.isChecked() || rb3_2.isChecked()) &&
                        (rb4_1.isChecked() || rb4_2.isChecked()) &&
                        (rb5_1.isChecked() || rb5_2.isChecked()) &&
                        (rb6_1.isChecked() || rb6_2.isChecked()) &&
                        (rb7_1.isChecked() || rb7_2.isChecked())
                ) {
                    submitBtn.setEnabled(true);
                }
            }
        });
        rb2_1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if((rb1_1.isChecked() || rb1_2.isChecked()) &&
                        (rb2_1.isChecked() || rb2_2.isChecked()) &&
                        (rb3_1.isChecked() || rb3_2.isChecked()) &&
                        (rb4_1.isChecked() || rb4_2.isChecked()) &&
                        (rb5_1.isChecked() || rb5_2.isChecked()) &&
                        (rb6_1.isChecked() || rb6_2.isChecked()) &&
                        (rb7_1.isChecked() || rb7_2.isChecked())
                ) {
                    submitBtn.setEnabled(true);
                }

            }
        });
        rb2_2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if((rb1_1.isChecked() || rb1_2.isChecked()) &&
                        (rb2_1.isChecked() || rb2_2.isChecked()) &&
                        (rb3_1.isChecked() || rb3_2.isChecked()) &&
                        (rb4_1.isChecked() || rb4_2.isChecked()) &&
                        (rb5_1.isChecked() || rb5_2.isChecked()) &&
                        (rb6_1.isChecked() || rb6_2.isChecked()) &&
                        (rb7_1.isChecked() || rb7_2.isChecked())
                ) {
                    submitBtn.setEnabled(true);
                }

            }
        });

        rb3_1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if((rb1_1.isChecked() || rb1_2.isChecked()) &&
                        (rb2_1.isChecked() || rb2_2.isChecked()) &&
                        (rb3_1.isChecked() || rb3_2.isChecked()) &&
                        (rb4_1.isChecked() || rb4_2.isChecked()) &&
                        (rb5_1.isChecked() || rb5_2.isChecked()) &&
                        (rb6_1.isChecked() || rb6_2.isChecked()) &&
                        (rb7_1.isChecked() || rb7_2.isChecked())
                ) {
                    submitBtn.setEnabled(true);
                }

            }
        });
        rb3_2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if((rb1_1.isChecked() || rb1_2.isChecked()) &&
                        (rb2_1.isChecked() || rb2_2.isChecked()) &&
                        (rb3_1.isChecked() || rb3_2.isChecked()) &&
                        (rb4_1.isChecked() || rb4_2.isChecked()) &&
                        (rb5_1.isChecked() || rb5_2.isChecked()) &&
                        (rb6_1.isChecked() || rb6_2.isChecked()) &&
                        (rb7_1.isChecked() || rb7_2.isChecked())
                ) {
                    submitBtn.setEnabled(true);
                }

            }
        });

        rb4_1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if((rb1_1.isChecked() || rb1_2.isChecked()) &&
                        (rb2_1.isChecked() || rb2_2.isChecked()) &&
                        (rb3_1.isChecked() || rb3_2.isChecked()) &&
                        (rb4_1.isChecked() || rb4_2.isChecked()) &&
                        (rb5_1.isChecked() || rb5_2.isChecked()) &&
                        (rb6_1.isChecked() || rb6_2.isChecked()) &&
                        (rb7_1.isChecked() || rb7_2.isChecked())
                ) {
                    submitBtn.setEnabled(true);
                }

            }
        });
        rb4_2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if((rb1_1.isChecked() || rb1_2.isChecked()) &&
                        (rb2_1.isChecked() || rb2_2.isChecked()) &&
                        (rb3_1.isChecked() || rb3_2.isChecked()) &&
                        (rb4_1.isChecked() || rb4_2.isChecked()) &&
                        (rb5_1.isChecked() || rb5_2.isChecked()) &&
                        (rb6_1.isChecked() || rb6_2.isChecked()) &&
                        (rb7_1.isChecked() || rb7_2.isChecked())
                ) {
                    submitBtn.setEnabled(true);
                }

            }
        });

        rb5_1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if((rb1_1.isChecked() || rb1_2.isChecked()) &&
                        (rb2_1.isChecked() || rb2_2.isChecked()) &&
                        (rb3_1.isChecked() || rb3_2.isChecked()) &&
                        (rb4_1.isChecked() || rb4_2.isChecked()) &&
                        (rb5_1.isChecked() || rb5_2.isChecked()) &&
                        (rb6_1.isChecked() || rb6_2.isChecked()) &&
                        (rb7_1.isChecked() || rb7_2.isChecked())
                ) {
                    submitBtn.setEnabled(true);
                }

            }
        });
        rb5_2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if((rb1_1.isChecked() || rb1_2.isChecked()) &&
                        (rb2_1.isChecked() || rb2_2.isChecked()) &&
                        (rb3_1.isChecked() || rb3_2.isChecked()) &&
                        (rb4_1.isChecked() || rb4_2.isChecked()) &&
                        (rb5_1.isChecked() || rb5_2.isChecked()) &&
                        (rb6_1.isChecked() || rb6_2.isChecked()) &&
                        (rb7_1.isChecked() || rb7_2.isChecked())
                ) {
                    submitBtn.setEnabled(true);
                }

            }
        });

        rb6_1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if((rb1_1.isChecked() || rb1_2.isChecked()) &&
                        (rb2_1.isChecked() || rb2_2.isChecked()) &&
                        (rb3_1.isChecked() || rb3_2.isChecked()) &&
                        (rb4_1.isChecked() || rb4_2.isChecked()) &&
                        (rb5_1.isChecked() || rb5_2.isChecked()) &&
                        (rb6_1.isChecked() || rb6_2.isChecked()) &&
                        (rb7_1.isChecked() || rb7_2.isChecked())
                ) {
                    submitBtn.setEnabled(true);
                }

            }
        });
        rb6_2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if((rb1_1.isChecked() || rb1_2.isChecked()) &&
                        (rb2_1.isChecked() || rb2_2.isChecked()) &&
                        (rb3_1.isChecked() || rb3_2.isChecked()) &&
                        (rb4_1.isChecked() || rb4_2.isChecked()) &&
                        (rb5_1.isChecked() || rb5_2.isChecked()) &&
                        (rb6_1.isChecked() || rb6_2.isChecked()) &&
                        (rb7_1.isChecked() || rb7_2.isChecked())
                ) {
                    submitBtn.setEnabled(true);
                }

            }
        });

        rb7_1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if((rb1_1.isChecked() || rb1_2.isChecked()) &&
                        (rb2_1.isChecked() || rb2_2.isChecked()) &&
                        (rb3_1.isChecked() || rb3_2.isChecked()) &&
                        (rb4_1.isChecked() || rb4_2.isChecked()) &&
                        (rb5_1.isChecked() || rb5_2.isChecked()) &&
                        (rb6_1.isChecked() || rb6_2.isChecked()) &&
                        (rb7_1.isChecked() || rb7_2.isChecked())
                ) {
                    submitBtn.setEnabled(true);
                }

            }
        });
        rb7_2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if((rb1_1.isChecked() || rb1_2.isChecked()) &&
                        (rb2_1.isChecked() || rb2_2.isChecked()) &&
                        (rb3_1.isChecked() || rb3_2.isChecked()) &&
                        (rb4_1.isChecked() || rb4_2.isChecked()) &&
                        (rb5_1.isChecked() || rb5_2.isChecked()) &&
                        (rb6_1.isChecked() || rb6_2.isChecked()) &&
                        (rb7_1.isChecked() || rb7_2.isChecked())
                ) {
                    submitBtn.setEnabled(true);
                }

            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Ad.dismiss();
                successSubmit();
            }
        });


    }

}
