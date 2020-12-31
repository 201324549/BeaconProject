package andbook.example.menuoption2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.item1:
            Toast.makeText(this, "메뉴1 선택", Toast.LENGTH_SHORT).show();
            return true;
        case R.id.item2:
            Toast.makeText(this, "메뉴2 선택", Toast.LENGTH_SHORT).show();
            return true;
        case R.id.item3:
            Toast.makeText(this, item.getTitle() + " 선택", Toast.LENGTH_SHORT).show();
            return true;
        case R.id.item4:
            Toast.makeText(this, item.getTitle() + " 선택", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
