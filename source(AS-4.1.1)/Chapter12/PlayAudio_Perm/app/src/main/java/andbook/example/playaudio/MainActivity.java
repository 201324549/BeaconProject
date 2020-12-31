package andbook.example.playaudio;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private File mFile;
    private MediaPlayer mPlayer;
    private static final int STORAGE_PERM_REQUEST = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!checkStoragePermission()) {
            requestStoragePermission();
        }

        File sdcard = Environment.getExternalStorageDirectory();
        mFile = new File(sdcard.getAbsolutePath() + "/sample.mp3");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    public void mOnClick(View v) {
        if (!checkStoragePermission()) {
            requestStoragePermission();
        }

        Intent intent;
        switch (v.getId()) {
        case R.id.btnPlay1: // MediaPlayer로 재생
            if (mPlayer == null) {
                mPlayer = new MediaPlayer();
            } else {
                mPlayer.reset();
            }
            try {
                mPlayer.setDataSource(getApplicationContext(), Uri.fromFile(mFile));
                mPlayer.prepare();
                mPlayer.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
            break;
        case R.id.btnPlay2: // MediaPlayer와 Service로 재생
            intent = new Intent(this, MyService.class);
            intent.setAction("andbook.example.PLAYMUSIC");
            intent.setData(Uri.fromFile(mFile));
            startService(intent);
            break;
        }
    }

    private boolean checkStoragePermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        return permissionCheck == PackageManager.PERMISSION_GRANTED;
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Snackbar.make(findViewById(android.R.id.content),
                    "권한 요청을 처리해야 작동합니다.", Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE },
                                    STORAGE_PERM_REQUEST);
                        }
                    }).show();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE },
                    STORAGE_PERM_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (requestCode == STORAGE_PERM_REQUEST) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 사용자가 권한 요청을 허가함
            } else {
                // 사용자가 권한 요청을 거부함
                Snackbar.make(findViewById(android.R.id.content),
                        "앱 정보에서 권한을 확인하십시오.", Snackbar.LENGTH_INDEFINITE)
                        .setAction(android.R.string.ok, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        }).show();
            }
        }
    }
}
