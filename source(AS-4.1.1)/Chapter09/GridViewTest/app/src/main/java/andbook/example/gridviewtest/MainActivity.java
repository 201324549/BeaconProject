package andbook.example.gridviewtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private MyData[] mData = {
            new MyData(R.drawable.apple, "사과"),
            new MyData(R.drawable.banana, "바나나"),
            new MyData(R.drawable.grape, "포도"),
            new MyData(R.drawable.pineapple, "파인애플"),
            new MyData(R.drawable.watermelon, "수박"),
    };
    private GridView mGrid;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 그리드뷰 초기화
        mGrid = (GridView) findViewById(R.id.grid);
        mAdapter = new MyAdapter(this, mData);
        mGrid.setAdapter(mAdapter);

        // 클릭 이벤트 처리
        mGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position %= mData.length; // 위치값(인덱스) 보정
                Toast.makeText(MainActivity.this, mData[position].name + " 선택!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
