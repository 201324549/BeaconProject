package andbook.example.painter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private MyView mView;
    private boolean mIsEmboss;
    private boolean mIsBlur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mView = (MyView) findViewById(R.id.MyView);
        ((Button) findViewById(mView.mGetType())).setTextColor(Color.RED);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.menu_emboss).setChecked(mIsEmboss);
        menu.findItem(R.id.menu_blur).setChecked(mIsBlur);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.menu_line_width:
            new LinePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog).show();
            return true;
        case R.id.menu_line_color:
            new ColorPickerDialog(this, android.R.style.Theme_Holo_Light_Dialog).show();
            return true;
        case R.id.menu_emboss:
            mIsEmboss = !mIsEmboss;
            if (mIsEmboss) mIsBlur = false;
            mView.mSetFilter(mIsEmboss, mIsBlur);
            break;
        case R.id.menu_blur:
            mIsBlur = !mIsBlur;
            if (mIsBlur) mIsEmboss = false;
            mView.mSetFilter(mIsEmboss, mIsBlur);
            break;
        default:
            return super.onOptionsItemSelected(item);
        }
        Button btnEmboss = (Button) findViewById(R.id.btnEmboss);
        Button btnBlur = (Button) findViewById(R.id.btnBlur);
        btnEmboss.setTextColor(mIsEmboss ? Color.RED : Color.BLACK);
        btnBlur.setTextColor(mIsBlur ? Color.RED : Color.BLACK);
        return true;
    }

    public void mSetLineWidth(int width) {
        mView.mSetLineWidth(width);
    }

    public void mSetLineColor(int color) {
        mView.mSetLineColor(color);
    }

    public void mOnClick(View v) {
        switch (v.getId()) {
        case R.id.btnLine:
        case R.id.btnRect:
        case R.id.btnCirc:
        case R.id.btnCurve:
        case R.id.btnErase:
            ((Button) findViewById(R.id.btnLine)).setTextColor(Color.BLACK);
            ((Button) findViewById(R.id.btnRect)).setTextColor(Color.BLACK);
            ((Button) findViewById(R.id.btnCirc)).setTextColor(Color.BLACK);
            ((Button) findViewById(R.id.btnCurve)).setTextColor(Color.BLACK);
            ((Button) findViewById(R.id.btnErase)).setTextColor(Color.BLACK);
            ((Button) v).setTextColor(Color.RED);
            mView.mSetType(v.getId());
            return;
        case R.id.btnClear:
            new AlertDialog.Builder(this).setTitle("Do you want to clear?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mView.mSetClear();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
            return;
        case R.id.btnEmboss:
            mIsEmboss = !mIsEmboss;
            if (mIsEmboss) mIsBlur = false;
            mView.mSetFilter(mIsEmboss, mIsBlur);
            break;
        case R.id.btnBlur:
            mIsBlur = !mIsBlur;
            if (mIsBlur) mIsEmboss = false;
            mView.mSetFilter(mIsEmboss, mIsBlur);
            break;
        }
        Button btnEmboss = (Button) findViewById(R.id.btnEmboss);
        Button btnBlur = (Button) findViewById(R.id.btnBlur);
        btnEmboss.setTextColor(mIsEmboss ? Color.RED : Color.BLACK);
        btnBlur.setTextColor(mIsBlur ? Color.RED : Color.BLACK);
    }

    public static class MyView extends View {
        private int mType = R.id.btnCurve;
        private int mLineWidth = 4;
        private int mLineColor = Color.MAGENTA;
        private int mEraseRadius = 16;
        private boolean mIsEmboss = false;
        private boolean mIsBlur = false;

        private Paint mPaint;
        private Bitmap mBitmapSrc;
        private Canvas mCanvasSrc;
        private Bitmap mBitmapDst;
        private Canvas mCanvasDst;

        private PorterDuffXfermode mModeXOR;
        private PorterDuffXfermode mModeCLEAR;

        private MaskFilter mEmboss;
        private MaskFilter mBlur;

        private float mStartX, mStartY;
        private float mOldX, mOldY;
        private Path mPath;

        public MyView(Context context) {
            super(context);
            init();
        }

        public MyView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init();
        }

        private void init() {
            mPaint = new Paint();
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeJoin(Paint.Join.ROUND);
            mPaint.setStrokeCap(Paint.Cap.ROUND);
            mPaint.setStrokeWidth(mLineWidth);
            mPaint.setColor(mLineColor);

            mModeXOR = new PorterDuffXfermode(PorterDuff.Mode.XOR);
            mModeCLEAR = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);

            mEmboss = new EmbossMaskFilter(new float[]{ 1, 1, 1 }, 0.4f, 6, 3.5f);
            mBlur = new BlurMaskFilter(4, BlurMaskFilter.Blur.NORMAL);

            mPath = new Path();
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            mBitmapSrc = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            mCanvasSrc = new Canvas(mBitmapSrc);
            mBitmapDst = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            mCanvasDst = new Canvas(mBitmapDst);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawBitmap(mBitmapDst, 0, 0, null);
            canvas.drawBitmap(mBitmapSrc, 0, 0, null);
        }

        public void mSetLineWidth(int width) {
            mLineWidth = width;
            mPaint.setStrokeWidth(mLineWidth);
        }

        public void mSetLineColor(int color) {
            mLineColor = color;
            mPaint.setColor(mLineColor);
        }

        public int mGetType() {
            return mType;
        }

        public void mSetType(int type) {
            mType = type;
        }

        public void mSetClear() {
            mBitmapSrc.eraseColor(0);
            mBitmapDst.eraseColor(0);
            invalidate();
        }

        public void mSetFilter(boolean isEmboss, boolean isBlur) {
            mIsEmboss = isEmboss;
            mIsBlur = isBlur;
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                _actionDown(x, y);
                return true;
            case MotionEvent.ACTION_MOVE:
                _actionMove(x, y);
                return true;
            case MotionEvent.ACTION_UP:
                _actionUp(x, y);
                return true;
            }
            return super.onTouchEvent(event);
        }

        private void _actionDown(float x, float y) {
            mStartX = x;
            mStartY = y;
            mOldX = x;
            mOldY = y;

            switch (mType) {
            case R.id.btnCurve:
                mPath.moveTo(mStartX, mStartY);
                break;
            }
        }

        private void _actionMove(float x, float y) {
            mPaint.setAntiAlias(false);
            mPaint.setMaskFilter(null);

            switch (mType) {
            case R.id.btnLine:
                mPaint.setXfermode(mModeXOR);
                mCanvasSrc.drawLine(mStartX, mStartY, mOldX, mOldY, mPaint);
                mCanvasSrc.drawLine(mStartX, mStartY, x, y, mPaint);
                mOldX = x;
                mOldY = y;
                break;
            case R.id.btnRect:
                mPaint.setXfermode(mModeXOR);
                mCanvasSrc.drawRect(mStartX, mStartY, mOldX, mOldY, mPaint);
                mCanvasSrc.drawRect(mStartX, mStartY, x, y, mPaint);
                mOldX = x;
                mOldY = y;
                break;
            case R.id.btnCirc:
                mPaint.setXfermode(mModeXOR);
                mCanvasSrc.drawOval(new RectF(mStartX, mStartY, mOldX, mOldY), mPaint);
                mCanvasSrc.drawOval(new RectF(mStartX, mStartY, x, y), mPaint);
                mOldX = x;
                mOldY = y;
                break;
            case R.id.btnCurve:
                mPaint.setXfermode(null);
                if (mIsEmboss) mPaint.setMaskFilter(mEmboss);
                else if (mIsBlur) mPaint.setMaskFilter(mBlur);
                mPath.lineTo(x, y);
                mCanvasSrc.drawPath(mPath, mPaint);
                break;
            case R.id.btnErase:
                mPaint.setXfermode(mModeCLEAR);
                mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                mCanvasDst.drawCircle(x, y, mEraseRadius, mPaint);
                mPaint.setStyle(Paint.Style.STROKE);
                break;
            }
            invalidate();
        }

        private void _actionUp(float x, float y) {
            mPaint.setAntiAlias(true);
            mPaint.setMaskFilter(null);
            if (mIsEmboss) mPaint.setMaskFilter(mEmboss);
            else if (mIsBlur) mPaint.setMaskFilter(mBlur);
            mPaint.setXfermode(null);

            mBitmapSrc.eraseColor(0);

            switch (mType) {
            case R.id.btnLine:
                mCanvasDst.drawLine(mStartX, mStartY, x, y, mPaint);
                break;
            case R.id.btnRect:
                mCanvasDst.drawRect(mStartX, mStartY, x, y, mPaint);
                break;
            case R.id.btnCirc:
                mCanvasDst.drawOval(new RectF(mStartX, mStartY, x, y), mPaint);
                break;
            case R.id.btnCurve:
                mPath.lineTo(x, y);
                mCanvasDst.drawPath(mPath, mPaint);
                mPath.reset();
                break;
            }
            invalidate();
        }
    } // MyView 클래스 끝
} // MainActivity 클래스 끝
