package andbook.example.playaudio;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private File mFile;
    private MediaPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        Intent intent;
        switch (v.getId()) {
        case R.id.btnIntent: // 인텐트로 재생
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(mFile), "audio/*");
            startActivity(intent);
            break;
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
}
