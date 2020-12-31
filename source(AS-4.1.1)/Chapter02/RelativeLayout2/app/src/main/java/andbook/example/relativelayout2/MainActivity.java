package andbook.example.relativelayout2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView mImage1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImage1 = (ImageView) findViewById(R.id.image1);
    }

    public void mOnClick(View v) {
        if (mImage1.getVisibility() == View.VISIBLE)
            mImage1.setVisibility(View.GONE);
        else
            mImage1.setVisibility(View.VISIBLE);
    }
}
