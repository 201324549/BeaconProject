package andbook.example.menuoption1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, 1, Menu.NONE, "메뉴1");
        menu.add(Menu.NONE, 2, Menu.NONE, "메뉴2");
        SubMenu submenu = menu.addSubMenu("하위 메뉴");
        submenu.add(Menu.NONE, 3, Menu.NONE, "메뉴3");
        submenu.add(Menu.NONE, 4, Menu.NONE, "메뉴4");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case 1:
            Toast.makeText(this, "메뉴1 선택", Toast.LENGTH_SHORT).show();
            return true;
        case 2:
            Toast.makeText(this, "메뉴2 선택", Toast.LENGTH_SHORT).show();
            return true;
        case 3:
            Toast.makeText(this, item.getTitle() + " 선택", Toast.LENGTH_SHORT).show();
            return true;
        case 4:
            Toast.makeText(this, item.getTitle() + " 선택", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
