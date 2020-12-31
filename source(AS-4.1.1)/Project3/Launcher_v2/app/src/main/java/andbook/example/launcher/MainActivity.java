package andbook.example.launcher;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GridView mGridApps;
    private GridAdapter mGridAdapter;
    private ArrayList<AppInfo> mAppInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadAppInfos();

        mGridApps = (GridView) findViewById(R.id.gridApps);
        mGridAdapter = new GridAdapter(this, mAppInfos);
        mGridApps.setAdapter(mGridAdapter);
        mGridApps.setOnItemClickListener(mItemClickListener);
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
            loadAppInfos();
            mGridAdapter.notifyDataSetChanged();
            mGridApps.setVisibility(View.VISIBLE);
        } else {
            mGridApps.setVisibility(View.INVISIBLE);
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
}
