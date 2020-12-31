package andbook.example.xfermode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));
    }

    private static class MyView extends View {
        private static final int WIDTH = 240;
        private static final int HEIGHT = 240;
        private Paint mPaint;
        private Bitmap mBitmapDst;
        private Bitmap mBitmapSrc;

        public MyView(Context context) {
            super(context);
            setBackgroundResource(R.drawable.flower);
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            // destination 비트맵
            mBitmapDst = Bitmap.createBitmap(WIDTH, HEIGHT, Bitmap.Config.ARGB_8888);
            Canvas canvasDst = new Canvas(mBitmapDst);
            mPaint.setColor(Color.argb(255, 255, 0, 0));
            canvasDst.drawOval(new RectF(0, 0, WIDTH * 3 / 4, HEIGHT * 3 / 4), mPaint);
            // source 비트맵
            mBitmapSrc = Bitmap.createBitmap(WIDTH, HEIGHT, Bitmap.Config.ARGB_8888);
            Canvas canvasSrc = new Canvas(mBitmapSrc);
            mPaint.setColor(Color.argb(255, 0, 255, 0));
            canvasSrc.drawRect(WIDTH / 3, HEIGHT / 3, WIDTH, HEIGHT, mPaint);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            // 전송 모드를 원하는 값으로 설정
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));
            //mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            //mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST));
            //mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));
            //mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            //mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
            //mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
            //mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
            //mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
            //mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            //mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
            //mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));

            // destination 비트맵에 source 비트맵 출력
            Canvas canvasDst = new Canvas(mBitmapDst);
            canvasDst.drawBitmap(mBitmapSrc, 0, 0, mPaint);

            // 전송 모드를 기본값으로 재설정하고 destination 비트맵 출력
            mPaint.setXfermode(null);
            canvas.drawBitmap(mBitmapDst, 0, 0, mPaint);
        }
    }
}
