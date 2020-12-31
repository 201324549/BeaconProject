package andbook.example.bitmapbasics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
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
        private Paint mPaint;
        private Bitmap mBitmap1;
        private Canvas mCanvas1;
        private Bitmap mBitmap2;
        private Canvas mCanvas2;

        public MyView(Context context) {
            super(context);
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(1);
            // 자바 코드로 비트맵을 직접 생성하고 그 위에 도형 그리기
            mBitmap1 = Bitmap.createBitmap(400, 300, Bitmap.Config.ARGB_8888);
            mCanvas1 = new Canvas(mBitmap1);
            mCanvas1.drawRect(0, 0, 400, 300, mPaint);
            mCanvas1.drawCircle(150, 100, 50, mPaint);
            // 그래픽 이미지를 읽어서 비트맵을 생성하고 그 위에 도형 그리기
            mBitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.test1);  // 읽기 전용 비트맵이다!!!
            mBitmap2 = mBitmap2.copy(mBitmap2.getConfig(), true);  // 읽기 쓰기 형태의 복사본이여야 한다.
            mCanvas2 = new Canvas(mBitmap2);
            mPaint.setColor(Color.RED);
            mPaint.setStrokeWidth(10);
            mCanvas2.drawLine(0, 0, mBitmap2.getWidth(), mBitmap2.getHeight(), mPaint);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawColor(Color.YELLOW);
            // (10, 10) 지점에 mBitmap1을 원래 크기(400x300)로 출력
            canvas.translate(10, 10);
            canvas.drawBitmap(mBitmap1, 0, 0, null);
            // y 방향으로 10 픽셀 이동 후 mBitmap2를 400x300 크기로 리사이징하여 출력
            canvas.translate(0, mBitmap1.getHeight() + 10);
            canvas.drawBitmap(mBitmap2, null, new Rect(0, 0, 400, 800), null);
        }
    }
}
