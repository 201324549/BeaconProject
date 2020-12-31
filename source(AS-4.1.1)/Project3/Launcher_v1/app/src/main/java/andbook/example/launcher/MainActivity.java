package andbook.example.launcher;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView mTextResult;
    private EditText mEditNumber;
    private ArrayList<AppInfo> mAppInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadAppInfos();

        mTextResult = (TextView) findViewById(R.id.textResult);
        mEditNumber = (EditText) findViewById(R.id.editNumber);
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

    private void showApps() {
        int index = 0;
        StringBuilder sb = new StringBuilder();
        for (AppInfo appInfo : mAppInfos) {
            sb.append(index++);
            sb.append(": ");
            sb.append(appInfo.title);
            sb.append("\n");
        }
        mTextResult.setText(sb);
    }

    public void mDrawerOnClick(View v) {
        if (mTextResult.getVisibility() == View.INVISIBLE) {
            loadAppInfos();
            showApps();
            mTextResult.setVisibility(View.VISIBLE);
        } else {
            mTextResult.setVisibility(View.INVISIBLE);
        }
    }

    public void mLaunchOnClick(View v) {
        int num = -1;
        try {
            num = Integer.parseInt(mEditNumber.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return;
        }
        if (num >= 0 && num < mAppInfos.size()) {
            startActivity(mAppInfos.get(num).intent);
        }
    }
}
