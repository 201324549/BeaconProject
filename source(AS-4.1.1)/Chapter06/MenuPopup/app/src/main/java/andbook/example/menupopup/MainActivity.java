package andbook.example.menupopup;

import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

public class MainActivity extends AppCompatActivity
        implements PopupMenu.OnMenuItemClickListener {

    private ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImage = (ImageView) findViewById(R.id.image);
    }

    public void mOnClick(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_popup, popup.getMenu());
        popup.setOnMenuItemClickListener(this);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.item1:
            mImage.setColorFilter(new LightingColorFilter(0xff0000, 0));
            return true;
        case R.id.item2:
            mImage.setColorFilter(new LightingColorFilter(0x00ff00, 0));
            return true;
        case R.id.item3:
            mImage.setColorFilter(new LightingColorFilter(0x0000ff, 0));
            return true;
        case R.id.item4:
            mImage.setColorFilter(new LightingColorFilter(0xffffff, 0));
            return true;
        }
        return false;
    }
}
