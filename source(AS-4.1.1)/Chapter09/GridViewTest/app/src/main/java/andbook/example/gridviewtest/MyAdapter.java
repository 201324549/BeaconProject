package andbook.example.gridviewtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {

    private Context ctx;
    private MyData[] data;

    public MyAdapter(Context ctx, MyData[] data) {
        this.ctx = ctx;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.length * 200; // 데이터 개수 변경
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(ctx);
            view = inflater.inflate(R.layout.fruit_list, viewGroup, false);
        }

        i %= data.length; // 위치값(인덱스) 보정

        ImageView image = (ImageView) view.findViewById(R.id.image);
        image.setImageResource(data[i].icon);
        TextView text = (TextView) view.findViewById(R.id.text);
        text.setText(data[i].name);

        return view;
    }
}
