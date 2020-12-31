package andbook.example.keytest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.editText).setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                    case KeyEvent.KEYCODE_ENTER:
                        Toast.makeText(v.getContext(), ((EditText) v).getText().toString(),
                                Toast.LENGTH_SHORT).show();
                        return true;
                    case KeyEvent.KEYCODE_VOLUME_DOWN:
                        Toast.makeText(v.getContext(), "Volume Down!",
                                Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
                return false;
            }
        });
    }
}
