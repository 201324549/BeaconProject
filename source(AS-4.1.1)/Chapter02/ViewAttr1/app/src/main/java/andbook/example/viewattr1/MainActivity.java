package andbook.example.viewattr1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((LinearLayout)findViewById(R.id.mainLayout)).setBackgroundColor(0xffffff77);
        ((Button)findViewById(R.id.button1)).setAlpha(0.5f);
        ((Button)findViewById(R.id.rotateBtn)).setRotation(-20f);
    }

    public void mOnClick(View v) {
        Toast.makeText(this, "버튼 클릭!", Toast.LENGTH_SHORT).show();
    }
}
