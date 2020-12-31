package andbook.example.dialogalert;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final String items[] = { "사과", "바나나", "포도" }; // 목록 데이터
    private boolean status[] = { false, false, false }; // 다중 선택 목록 기억

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void mOnClick(View v) {
        AlertDialog.Builder builder;

        switch (v.getId()) {
        case R.id.btnDialog1: // 제목 + 아이콘 + 메시지 + 버튼 표시
            builder = new AlertDialog.Builder(this);
            builder.setTitle("대화상자1");
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setMessage(Html.fromHtml("안드로이드의 기본 대화상자로는 "
                    + "<b>AlertDialog</b>가 있으며, <i>AlertDialog</i>를 특수화한 "
                    + "<b>DatePickerDialog</b>와 <b>TimePickerDialog</b>가 있다."));

            builder.setNeutralButton("글쎄요", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.create().show();
            break;
        case R.id.btnDialog2: // 제목 + 아이콘 + 목록 표시
            builder = new AlertDialog.Builder(this);
            builder.setTitle("대화상자2");
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(MainActivity.this, items[which], Toast.LENGTH_SHORT).show();
                }
            });
            builder.create().show();
            break;
        case R.id.btnDialog3: // 제목 + 아이콘 + 단일 선택 목록 + 버튼 표시
            builder = new AlertDialog.Builder(this);
            builder.setTitle("대화상자3");
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(MainActivity.this, items[which], Toast.LENGTH_SHORT).show();
                }
            });
            builder.setPositiveButton("Close", null);
            builder.create().show();
            break;
        case R.id.btnDialog4: // 제목 + 아이콘 + 다중 선택 목록 + 버튼 표시
            builder = new AlertDialog.Builder(this);
            builder.setTitle("대화상자4");
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setMultiChoiceItems(items, status, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                    status[which] = isChecked;
                }
            });
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(MainActivity.this, status[0] + ", " + status[1]
                            + ", " + status[2], Toast.LENGTH_SHORT).show();
                }
            });
            builder.create().show();
            break;
        }
    }
}
