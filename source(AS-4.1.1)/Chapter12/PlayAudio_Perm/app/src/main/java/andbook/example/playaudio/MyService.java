package andbook.example.playaudio;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;

public class MyService extends Service {

    private MediaPlayer mPlayer;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        if (mPlayer == null) {
            mPlayer = new MediaPlayer();
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.reset(); // 재생이 끝나면 리셋!
                }
            });
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction().equals("andbook.example.PLAYMUSIC")) {
            if (!mPlayer.isPlaying()) {
                new MusicThread(intent.getData()).start();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    private class MusicThread extends Thread {

        private Uri mUri;

        MusicThread(Uri uri) {
            mUri = uri;
        }

        @Override
        public void run() {
            try {
                mPlayer.setDataSource(getApplicationContext(), mUri);
                mPlayer.prepare();
                mPlayer.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
