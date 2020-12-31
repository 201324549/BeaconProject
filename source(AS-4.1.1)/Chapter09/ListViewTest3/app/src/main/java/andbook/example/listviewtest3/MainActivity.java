package andbook.example.listviewtest3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private MyData[] mData = {
            new MyData(R.drawable.apple, "사과"),
            new MyData(R.drawable.banana, "바나나"),
            new MyData(R.drawable.grape, "포도"),
            new MyData(R.drawable.pineapple, "파인애플"),
            new MyData(R.drawable.watermelon, "수박"),
    };
    private ListView mList;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 리스트뷰 초기화
        mList = (ListView) findViewById(R.id.list);
        mAdapter = new MyAdapter(this, mData);
        mList.setAdapter(mAdapter);

        // 클릭 이벤트 처리
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, mData[position].name + " 선택!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
