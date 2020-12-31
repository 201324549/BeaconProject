package andbook.example.togglebutton1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void mOnClick(View v) {
        ToggleButton btn = (ToggleButton) v;
        if (btn.isChecked())
            btn.setTextColor(0xffff0000);
        else
            btn.setTextColor(0xff0000ff);
    }
}
