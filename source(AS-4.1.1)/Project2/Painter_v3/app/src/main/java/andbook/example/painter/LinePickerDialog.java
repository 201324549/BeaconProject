package andbook.example.painter;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

public class LinePickerDialog extends Dialog
        implements View.OnClickListener {

    private MainActivity mActivity;

    public LinePickerDialog(Context context) {
        super(context);
        mActivity = (MainActivity) context;
    }

    public LinePickerDialog(Context context, int themeResId) {
        super(context, themeResId);
        mActivity = (MainActivity) context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_line);
        setTitle("Pick Line Width");

        findViewById(R.id.width1).setOnClickListener(this);
        findViewById(R.id.width2).setOnClickListener(this);
        findViewById(R.id.width3).setOnClickListener(this);
        findViewById(R.id.width4).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.width1:
            mActivity.mSetLineWidth(2);
            break;
        case R.id.width2:
            mActivity.mSetLineWidth(4);
            break;
        case R.id.width3:
            mActivity.mSetLineWidth(6);
            break;
        case R.id.width4:
            mActivity.mSetLineWidth(8);
            break;
        }
        dismiss();
    }
}
