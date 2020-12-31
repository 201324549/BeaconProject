package andbook.example.launcher;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<AppInfo> mAppInfos;

    public GridAdapter(Context context, ArrayList<AppInfo> appInfos) {
        mContext = context;
        mAppInfos = appInfos;
    }

    @Override
    public int getCount() {
        return mAppInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AppInfo appInfo = mAppInfos.get(position);

        TextView textView;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            textView = (TextView) inflater.inflate(R.layout.mygrid_item, parent, false);
        } else {
            textView = (TextView) convertView;
        }
        textView.setText(appInfo.title);

        Resources resources = mContext.getResources();
        int width = (int) resources.getDimension(android.R.dimen.app_icon_size);
        int height = (int) resources.getDimension(android.R.dimen.app_icon_size);
        Drawable icon = appInfo.icon;
        icon.setBounds(0, 0, width, height);
        textView.setCompoundDrawables(null, icon, null, null);

        return textView;
    }
}
