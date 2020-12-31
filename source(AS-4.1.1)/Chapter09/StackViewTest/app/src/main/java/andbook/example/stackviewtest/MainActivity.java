package andbook.example.stackviewtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.StackView;

public class MainActivity extends AppCompatActivity {

    private MyData[] mData = {
            new MyData(R.drawable.apple, "사과"),
            new MyData(R.drawable.banana, "바나나"),
            new MyData(R.drawable.grape, "포도"),
            new MyData(R.drawable.pineapple, "파인애플"),
            new MyData(R.drawable.watermelon, "수박"),
    };
    private StackView mStackView;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 스택뷰 초기화
        mStackView = (StackView) findViewById(R.id.stackview);
        mAdapter = new MyAdapter(this, mData);
        mStackView.setAdapter(mAdapter);
    }

    public void mOnClick(View v) {
        switch (v.getId()) {
        case R.id.btnPrev:
            mStackView.showPrevious();
            break;
        case R.id.btnNext:
            mStackView.showNext();
            break;
        }
    }
}
