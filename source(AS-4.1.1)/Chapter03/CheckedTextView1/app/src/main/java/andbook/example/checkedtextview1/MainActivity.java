package andbook.example.checkedtextview1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckedTextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void mOnClick(View v) {
        CheckedTextView ctv = (CheckedTextView) v;
        ctv.setChecked(!ctv.isChecked());
        //ctv.toggle();
    }
}
