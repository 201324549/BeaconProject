package andbook.example.dialogcustom;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String mName = "???";
    private String mPassword = "???";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateResult();
    }

    private void updateResult() {
        TextView textResult = (TextView) findViewById(R.id.textResult);
        textResult.setText("이름: " + mName + "\n" + "암호: " + mPassword);
    }

    public void mOnClick(View v) {
        // ① XML로 대화상자 레이아웃 정의; dialog_login.xml 참조
        // ② AlertDialog.Builder 객체 생성
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // ③ setView() 메서드로 레이아웃 지정
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.dialog_login, null);
        builder.setView(layout);
        // ④ 제목, 아이콘, 메시지, 버튼 추가 (선택 사항)
        final EditText mEditName = (EditText) layout.findViewById(R.id.editName);
        final EditText mEditPassword = (EditText) layout.findViewById(R.id.editPassword);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mName = mEditName.getText().toString();
                mPassword = mEditPassword.getText().toString();
                updateResult();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        // ⑤ 대화상자 객체 생성 & 화면 보이기
        builder.create().show();
    }
}
