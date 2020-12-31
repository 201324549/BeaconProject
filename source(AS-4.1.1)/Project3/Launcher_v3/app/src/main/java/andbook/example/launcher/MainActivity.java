package andbook.example.launcher;

import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.BatteryManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView mTextBattery;
    private GridView mGridApps;
    private GridAdapter mGridAdapter;
    private ArrayList<AppInfo> mAppInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadAppInfos();

        mTextBattery = (TextView) findViewById(R.id.textBattery);
        mGridApps = (GridView) findViewById(R.id.gridApps);
        mGridAdapter = new GridAdapter(this, mAppInfos);
        mGridApps.setAdapter(mGridAdapter);
        mGridApps.setOnItemClickListener(mItemClickListener);

        registerPkgReceiver();
        setWallpaper();
    }

    private void loadAppInfos() {
        PackageManager pm = getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, 0);
        if (resolveInfos == null) return;

        Collections.sort(resolveInfos, new ResolveInfo.DisplayNameComparator(pm));
        int count = resolveInfos.size();

        if (mAppInfos == null) {
            mAppInfos = new ArrayList<>(count);
        }
        mAppInfos.clear();

        for (int i = 0; i < count; i++) {
            AppInfo appInfo = new AppInfo();
            ResolveInfo resolveInfo = resolveInfos.get(i);

            appInfo.title = resolveInfo.loadLabel(pm);
            appInfo.icon = resolveInfo.activityInfo.loadIcon(pm);
            appInfo.setIntent(
                    new ComponentName(
                            resolveInfo.activityInfo.applicationInfo.packageName,
                            resolveInfo.activityInfo.name),
                    Intent.FLAG_ACTIVITY_NEW_TASK);

            mAppInfos.add(appInfo);
        }
    }

    public void mDrawerOnClick(View v) {
        if (mGridApps.getVisibility() == View.INVISIBLE) {
            mGridApps.setVisibility(View.VISIBLE);
            mTextBattery.setVisibility(View.GONE);
        } else {
            mGridApps.setVisibility(View.INVISIBLE);
            mTextBattery.setVisibility(View.VISIBLE);
        }
    }

    AdapterView.OnItemClickListener mItemClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            AppInfo appInfo = mAppInfos.get(position);
            startActivity(appInfo.intent);
        }
    };

    public void mIconOnClick(View v) {
        Intent intent = new Intent();

        switch (v.getId()) {
        case R.id.btnDialer:
            intent.setAction(Intent.ACTION_DIAL);
            break;
        case R.id.btnMessages:
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_APP_MESSAGING);
            break;
        case R.id.btnCamera:
            intent.setAction(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
            break;
        case R.id.btnBrowser:
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_APP_BROWSER);
            break;
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterPkgReceiver();
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerBatteryReceiver();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterBatteryReceiver();
    }

    private BroadcastReceiver mPkgReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            loadAppInfos();
            mGridAdapter.notifyDataSetChanged();
        }
    };

    private void registerPkgReceiver() {
        IntentFilter filter = new IntentFilter(Intent.ACTION_PACKAGE_ADDED);
        filter.addAction(Intent.ACTION_PACKAGE_CHANGED);
        filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        filter.addDataScheme("package");
        registerReceiver(mPkgReceiver, filter);
    }

    private void unregisterPkgReceiver() {
        unregisterReceiver(mPkgReceiver);
    }

    private BroadcastReceiver mBatteryReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (!action.equals(Intent.ACTION_BATTERY_CHANGED))
                return;
            if (!intent.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false)) {
                mTextBattery.setText("배터리 없음");
                return;
            }

            int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS,
                    BatteryManager.BATTERY_STATUS_UNKNOWN);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100);
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);

            String strPlugged;
            switch (plugged) {
            case BatteryManager.BATTERY_PLUGGED_AC:
                strPlugged = "AC";
                break;
            case BatteryManager.BATTERY_PLUGGED_USB:
                strPlugged = "USB";
                break;
            default:
                strPlugged = "배터리";
                break;
            }

            String strStatus;
            switch (status) {
            case BatteryManager.BATTERY_STATUS_CHARGING:
                strStatus = "충전 중";
                break;
            case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                strStatus = "충전 중 아님";
                break;
            case BatteryManager.BATTERY_STATUS_DISCHARGING:
                strStatus = "방전 중";
                break;
            case BatteryManager.BATTERY_STATUS_FULL:
                strStatus = "충전 완료";
                break;
            default:
                strStatus = "알 수 없음";
                break;
            }

            String str = String.format(Locale.KOREAN, "연결: %s\n상태: %s\n레벨: %d%%",
                    strPlugged, strStatus, level * 100 / scale);
            mTextBattery.setText(str);
        }
    };

    private void registerBatteryReceiver() {
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(mBatteryReceiver, filter);
    }

    private void unregisterBatteryReceiver() {
        unregisterReceiver(mBatteryReceiver);
    }

    void setWallpaper() {
        WallpaperManager wm = WallpaperManager.getInstance(this);
        Drawable drawable = wm.getDrawable();
        if (drawable != null) {
            getWindow().setBackgroundDrawable(drawable);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mGridApps.getVisibility() == View.VISIBLE) {
                mGridApps.setVisibility(View.INVISIBLE);
                mTextBattery.setVisibility(View.VISIBLE);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
