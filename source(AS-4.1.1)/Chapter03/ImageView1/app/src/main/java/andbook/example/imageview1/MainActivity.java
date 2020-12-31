package andbook.example.imageview1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView mImageTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageTest = (ImageView) findViewById(R.id.imageTest);
        mImageTest.setAlpha(128); /* 안드로이드 1.0부터 사용 가능 */
        //mImageTest.setImageAlpha(128); /* 안드로이드 4.1부터 사용 가능 */
    }
}
