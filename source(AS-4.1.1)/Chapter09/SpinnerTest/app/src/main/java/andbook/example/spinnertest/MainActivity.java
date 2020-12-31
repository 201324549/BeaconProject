package andbook.example.spinnertest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> mData = new ArrayList<>();
    private Spinner mSpinner1;
    private Spinner mSpinner2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 초기 데이터
        mData.add("수성");
        mData.add("금성");
        mData.add("지구");
        mData.add("화성");
        mData.add("목성");
        mData.add("토성");
        mData.add("천왕성");
        mData.add("해왕성");

        // 스피너 초기화
        mSpinner1 = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, mData);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner1.setAdapter(adapter1);

        mSpinner2 = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.planets, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner2.setAdapter(adapter2);
    }

    public void mOnClick(View v) {
        // 선택된 항목 인덱스 얻기
        int pos1 = mSpinner1.getSelectedItemPosition();
        int pos2 = mSpinner2.getSelectedItemPosition();

        // 선택된 항목 데이터 얻기
        String str1 = mData.get(pos1);
        String[] arrData = getResources().getStringArray(R.array.planets);
        String str2 = arrData[pos2];

        // 선택된 데이터 표시하기
        Toast.makeText(this, str1 + ", " + str2, Toast.LENGTH_SHORT).show();
    }
}
