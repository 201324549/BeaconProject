package andbook.example.seekbar1;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private SeekBar mSeekRed;
    private SeekBar mSeekGreen;
    private SeekBar mSeekBlue;
    private View mViewColor;
    private int seekR, seekG, seekB;

    private void doSomethingWithColor() {
        mViewColor.setBackgroundColor(Color.argb(255, seekR, seekG, seekB));
        // Do something with color
    }

    public class MySeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            switch (seekBar.getId()) {
                case R.id.seekRed:
                    seekR = progress;
                    break;
                case R.id.seekGreen:
                    seekG = progress;
                    break;
                case R.id.seekBlue:
                    seekB = progress;
                    break;
            }

            doSomethingWithColor();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSeekRed = (SeekBar) findViewById(R.id.seekRed);
        mSeekGreen = (SeekBar) findViewById(R.id.seekGreen);
        mSeekBlue = (SeekBar) findViewById(R.id.seekBlue);
        mViewColor = findViewById(R.id.viewColor);

        mViewColor.setBackgroundColor(Color.argb(255, mSeekRed.getProgress(), mSeekRed.getProgress(), mSeekRed.getProgress()));

        // "Anonymous inner class" 문법이 사용된다.
        // = 객체 생성 + 클래스 정의
//        mSeekRed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });

        MySeekBarChangeListener msbclR = new MySeekBarChangeListener();
        MySeekBarChangeListener msbclG = new MySeekBarChangeListener();
        MySeekBarChangeListener msbclB = new MySeekBarChangeListener();

        mSeekRed.setOnSeekBarChangeListener(msbclR);
        mSeekGreen.setOnSeekBarChangeListener(msbclG);
        mSeekBlue.setOnSeekBarChangeListener(msbclB);
    }
}
