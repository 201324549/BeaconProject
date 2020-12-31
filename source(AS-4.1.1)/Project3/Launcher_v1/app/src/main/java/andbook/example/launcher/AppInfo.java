package andbook.example.launcher;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.drawable.Drawable;

public class AppInfo {

    public CharSequence title;
    public Drawable icon;
    public Intent intent;

    public void setIntent(ComponentName componentName, int launchFlags) {
        intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setComponent(componentName);
        intent.setFlags(launchFlags);
    }
}
