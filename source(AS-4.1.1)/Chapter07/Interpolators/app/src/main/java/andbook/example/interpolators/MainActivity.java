package andbook.example.interpolators;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mBtnChild1;
    private Button mBtnChild2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnChild1 = (Button) findViewById(R.id.btnChild1);
        mBtnChild2 = (Button) findViewById(R.id.btnChild2);
    }

    public void mOnClick(View v) {
        Animation anim = null;
        switch (v.getId()) {
        case R.id.btnTest1:
            anim = AnimationUtils.loadAnimation(this, R.anim.translate1);
            break;
        case R.id.btnTest2:
            anim = AnimationUtils.loadAnimation(this, R.anim.translate2);
            break;
        case R.id.btnTest3:
            anim = AnimationUtils.loadAnimation(this, R.anim.translate3);
            break;
        case R.id.btnTest4:
            anim = AnimationUtils.loadAnimation(this, R.anim.translate4);
            break;
        case R.id.btnTest5:
            anim = AnimationUtils.loadAnimation(this, R.anim.translate5);
            break;
        case R.id.btnTest6:
            anim = AnimationUtils.loadAnimation(this, R.anim.translate6);
            break;
        case R.id.btnTest7:
            anim = AnimationUtils.loadAnimation(this, R.anim.translate7);
            break;
        case R.id.btnTest8:
            anim = AnimationUtils.loadAnimation(this, R.anim.translate8);
            mBtnChild2.startAnimation(anim);
            return;
        }
        mBtnChild1.startAnimation(anim);
    }
}
