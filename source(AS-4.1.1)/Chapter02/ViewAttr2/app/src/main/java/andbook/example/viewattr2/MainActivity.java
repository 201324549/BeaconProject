package andbook.example.viewattr2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView mImagePic1;
    private Button btn1;
    private Button btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImagePic1 = (ImageView) findViewById(R.id.imagePic1);
        btn1 = (Button) findViewById(R.id.button1);
        btn2 = (Button) findViewById(R.id.button2);
    }

    public void mOnClick1(View v) {
        if(mImagePic1.getVisibility() == View.INVISIBLE) {
            mImagePic1.setVisibility(View.VISIBLE);
            btn1.setText("첫 번째 그림 숨기기");
        } else {
            mImagePic1.setVisibility(View.INVISIBLE);
            btn1.setText("첫 번째 그림 보이기");
        }
    }

    public void mOnClick2(View v) {
        if(mImagePic1.getVisibility() == View.GONE){
            mImagePic1.setVisibility(View.VISIBLE);
            btn2.setText("첫 번째 그림 없애기");
        } else {
            mImagePic1.setVisibility(View.GONE);
            btn2.setText("첫 번째 그림 보이기");
        }
    }
}
