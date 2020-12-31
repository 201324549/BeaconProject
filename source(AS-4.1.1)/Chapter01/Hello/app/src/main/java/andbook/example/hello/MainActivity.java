package andbook.example.hello;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button mButton; // 이 부분을 입력하고 Alt+Enter를 누른다.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // 수퍼클래스의 onCreate() 메서드를 호출한다.
        setContentView(R.layout.activity_main); // 액티비티 화면을 초기화한다.

        mButton = (Button) findViewById(R.id.button); // 레이아웃에서 버튼 객체를 찾아낸다.
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "버튼 클릭!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
