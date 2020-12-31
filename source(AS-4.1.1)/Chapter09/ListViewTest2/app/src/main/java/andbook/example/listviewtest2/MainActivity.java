package andbook.example.listviewtest2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> mData = new ArrayList<String>();
    private int mNextNumber;
    private ListView mList;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 초기 데이터
        for (int i = 1; i <= 5; i++) {
            mData.add("항목" + i);
        }
        mNextNumber = 6;

        // 리스트뷰 초기화
        mList = (ListView) findViewById(R.id.list);
        mAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_single_choice, mData);
        mList.setAdapter(mAdapter);
        mList.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
    }

    public void mOnClick(View v) {
        switch (v.getId()) {
        case R.id.btnAdd:
            // 항목 추가
            mData.add("항목" + mNextNumber++);
            mAdapter.notifyDataSetChanged();
            break;
        case R.id.btnRemove:
            int index = mList.getCheckedItemPosition();
            if (index != AdapterView.INVALID_POSITION) {
                // 선택 초기화
                mList.clearChoices();
                // 항목 삭제
                mData.remove(index);
                mAdapter.notifyDataSetChanged();
            }
            break;
        }
    }
}
