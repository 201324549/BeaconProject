package andbook.example.recordaudio;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    // 파일 정보
    private File mFile;
    // 오디오 기록
    private Button mBtnRecord;
    private MediaRecorder mRecorder;
    private boolean mIsRecording;
    // 오디오 재생
    private Button mBtnPlay;
    private MediaPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File sdcard = Environment.getExternalStorageDirectory();
        mFile = new File(sdcard.getAbsolutePath() + "/record.3gp");

        mBtnRecord = (Button) findViewById(R.id.btnRecord);
        mBtnPlay = (Button) findViewById(R.id.btnPlay);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    public void mOnClick(View v) {
        switch (v.getId()) {
        case R.id.btnRecord: // 녹음 시작 & 중지
            if (!mIsRecording) { // 녹음 중이 아니면 녹음 시작
                if (mRecorder == null) {
                    mRecorder = new MediaRecorder();
                } else {
                    mRecorder.reset();
                }
                try {
                    mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);
                    mRecorder.setOutputFile(mFile.getAbsolutePath());
                    mRecorder.prepare();
                    mRecorder.start();
                    mIsRecording = true;
                    mBtnRecord.setText("녹음 중지");
                    mBtnPlay.setEnabled(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else { // 녹음 중이면 녹음 중지
                mRecorder.stop();
                mIsRecording = false;
                mBtnRecord.setText("녹음 시작");
                mBtnPlay.setEnabled(true);
            }
            break;
        case R.id.btnPlay: // 재생 시작
            if (!mFile.exists()) {
                Toast.makeText(this, "녹음 파일 없음!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (mPlayer == null) {
                mPlayer = new MediaPlayer();
                mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mBtnRecord.setEnabled(true);
                        mBtnPlay.setEnabled(true);
                    }
                });
            } else {
                mPlayer.reset();
            }
            try {
                mPlayer.setDataSource(getApplicationContext(), Uri.fromFile(mFile));
                mPlayer.prepare();
                mPlayer.start();
                mBtnRecord.setEnabled(false);
                mBtnPlay.setEnabled(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
            break;
        }
    }
}
