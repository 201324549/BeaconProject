package andbook.example.tweenanim;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView mImgTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImgTest = (ImageView) findViewById(R.id.imgTest);
    }

    public void mOnClick(View v) {
        Animation anim = null;
        switch (v.getId()) {
        case R.id.btnTranslate:
            anim = AnimationUtils.loadAnimation(this, R.anim.translate);
            break;
        case R.id.btnScale:
            anim = AnimationUtils.loadAnimation(this, R.anim.scale);
            break;
        case R.id.btnRotate:
            anim = AnimationUtils.loadAnimation(this, R.anim.rotate);
            break;
        case R.id.btnAlpha:
            anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
            break;
        case R.id.btnTranslateScale:
            anim = AnimationUtils.loadAnimation(this, R.anim.translate_scale);
            break;
        case R.id.btnRotateAlpha:
            anim = AnimationUtils.loadAnimation(this, R.anim.rotate_alpha);
            break;
        }
        mImgTest.startAnimation(anim);
    }
}
