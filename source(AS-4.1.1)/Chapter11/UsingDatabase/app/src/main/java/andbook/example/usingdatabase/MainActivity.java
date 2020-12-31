package andbook.example.usingdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private MyDBHelper mDBHelper;
    private EditText mEditName;
    private EditText mEditAge;
    private TextView mTextResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDBHelper = new MyDBHelper(this);
        mEditName = (EditText) findViewById(R.id.editName);
        mEditAge = (EditText) findViewById(R.id.editAge);
        mTextResult = (TextView) findViewById(R.id.textResult);
    }

    public void mOnClick(View v) {
        SQLiteDatabase db;
        ContentValues values;
        String[] projection = { "_id", "name", "age" };
        Cursor cur;

        switch (v.getId()) {
        case R.id.btnInsert: // 삽입
            if (mEditName.getText().length() > 0 && mEditAge.getText().length() > 0) {
                db = mDBHelper.getWritableDatabase();
                values = new ContentValues();
                values.put("name", mEditName.getText().toString());
                values.put("age", mEditAge.getText().toString());
                db.insert("people", null, values);
                mDBHelper.close();
            }
            break;
        case R.id.btnSelectAll: // 전체 검색
            db = mDBHelper.getReadableDatabase();
            cur = db.query("people", projection, null,
                    null, null, null, null);
            if (cur != null) {
                showResult(cur);
                cur.close();
            }
            mDBHelper.close();
            break;
        case R.id.btnSelectName: // 이름 검색
            if (mEditName.getText().length() > 0) {
                db = mDBHelper.getReadableDatabase();
                String name = mEditName.getText().toString();
                cur = db.query("people", projection, "name=?",
                        new String[]{ name }, null, null, null);
                if (cur != null) {
                    showResult(cur);
                    cur.close();
                }
                mDBHelper.close();
            }
            break;
        case R.id.btnUpdateAge: // 나이 갱신
            if (mEditName.getText().length() > 0 && mEditAge.getText().length() > 0) {
                db = mDBHelper.getWritableDatabase();
                String name = mEditName.getText().toString();
                values = new ContentValues();
                values.put("age", mEditAge.getText().toString());
                db.update("people", values, "name=?", new String[]{ name });
                mDBHelper.close();
            }
            break;
        case R.id.btnDeleteName: // 이름 삭제
            if (mEditName.getText().length() > 0) {
                db = mDBHelper.getWritableDatabase();
                String name = mEditName.getText().toString();
                db.delete("people", "name=?", new String[]{ name });
                mDBHelper.close();
            }
            break;
        case R.id.btnDeleteAll: // 전체 삭제
            db = mDBHelper.getWritableDatabase();
            db.delete("people", null, null);
            mDBHelper.close();
            break;
        }
    }

    private void showResult(Cursor cur) {
        mTextResult.setText("");
        int name_col = cur.getColumnIndex("name");
        int age_col = cur.getColumnIndex("age");
        while (cur.moveToNext()) {
            String name = cur.getString(name_col);
            String age = cur.getString(age_col);
            mTextResult.append(name + ", " + age + "\n");
        }
    }
}

class MyDBHelper extends SQLiteOpenHelper {

    public MyDBHelper(Context context) {
        super(context, "mytest.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE people(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT, age TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS people;");
        onCreate(db);
    }
}
