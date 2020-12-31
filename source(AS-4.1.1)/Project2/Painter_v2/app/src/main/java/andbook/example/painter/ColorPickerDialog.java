package andbook.example.painter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

public class ColorPickerDialog extends Dialog
        implements View.OnClickListener {

    private MainActivity mActivity;

    public ColorPickerDialog(Context context) {
        super(context);
        mActivity = (MainActivity) context;
    }

    public ColorPickerDialog(Context context, int themeResId) {
        super(context, themeResId);
        mActivity = (MainActivity) context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_color);
        setTitle("Pick Line Color");

        findViewById(R.id.color_black).setOnClickListener(this);
        findViewById(R.id.color_blue).setOnClickListener(this);
        findViewById(R.id.color_cyan).setOnClickListener(this);
        findViewById(R.id.color_gray).setOnClickListener(this);
        findViewById(R.id.color_green).setOnClickListener(this);
        findViewById(R.id.color_magenta).setOnClickListener(this);
        findViewById(R.id.color_red).setOnClickListener(this);
        findViewById(R.id.color_yellow).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int color = Color.BLACK;
        switch (v.getId()) {
        case R.id.color_black:
            color = Color.BLACK;
            break;
        case R.id.color_blue:
            color = Color.BLUE;
            break;
        case R.id.color_cyan:
            color = Color.CYAN;
            break;
        case R.id.color_gray:
            color = Color.GRAY;
            break;
        case R.id.color_green:
            color = Color.GREEN;
            break;
        case R.id.color_magenta:
            color = Color.MAGENTA;
            break;
        case R.id.color_red:
            color = Color.RED;
            break;
        case R.id.color_yellow:
            color = Color.YELLOW;
            break;
        }
        mActivity.mSetLineColor(color);
        dismiss();
    }
}
