package andbook.example.menucontext;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button mBtn;
    private EditText mEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtn = (Button) findViewById(R.id.test1);
        mEdit = (EditText) findViewById(R.id.test2);
        registerForContextMenu(mBtn);
        registerForContextMenu(mEdit);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        switch (v.getId()) {
        case R.id.test1:
            getMenuInflater().inflate(R.menu.menu_main_ctx1, menu);
            break;
        case R.id.test2:
            getMenuInflater().inflate(R.menu.menu_main_ctx2, menu);
            menu.setHeaderTitle("텍스트 색상 변경");
            break;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.item1:
            Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
            break;
        case R.id.item2:
            Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
            break;
        case R.id.item3:
            mEdit.setTextColor(Color.RED);
            break;
        case R.id.item4:
            mEdit.setTextColor(Color.GREEN);
            break;
        }
        return super.onContextItemSelected(item);
    }
}
