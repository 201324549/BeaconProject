package andbook.example.dialogdatetime;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private String mStrDate = "???";
    private String mStrTime = "???";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateResult();
    }

    private void updateResult() {
        TextView textResult = (TextView) findViewById(R.id.textResult);
        textResult.setText("날짜: " + mStrDate + "\n" + "시간: " + mStrTime);
    }

    public void mOnClick(View v) {
        Calendar c = Calendar.getInstance();
        switch (v.getId()) {
        case R.id.btnSelectDate:
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            new DatePickerDialog(this, mDateSetListener, year, month, day).show();
            break;
        case R.id.btnSelectTime:
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            new TimePickerDialog(this, mTimeSetListener, hour, minute, false).show();
            break;
        }
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener
            = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mStrDate = String.format("%d년 %d월 %d일", year, month + 1, dayOfMonth);
            updateResult();
        }
    };

    private TimePickerDialog.OnTimeSetListener mTimeSetListener
            = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mStrTime = String.format("%d시 %d분", hourOfDay, minute);
            updateResult();
        }
    };
}
