package andbook.example.frameanim;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private AnimationDrawable mAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView img = (ImageView) findViewById(R.id.imgFrame);
        img.setBackgroundResource(R.drawable.frames);
        mAnimation = (AnimationDrawable) img.getBackground();
    }

    public void mOnClick(View v) {
        switch (v.getId()) {
        case R.id.btnStart:
            mAnimation.start();
            break;
        case R.id.btnStop:
            mAnimation.stop();
            break;
        }
    }
}
