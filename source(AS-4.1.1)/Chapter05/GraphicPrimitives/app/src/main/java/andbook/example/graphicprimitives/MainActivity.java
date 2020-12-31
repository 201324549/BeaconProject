package andbook.example.graphicprimitives;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
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
        private Paint mPaint;

        public MyView(Context context) {
            super(context);
            mPaint = new Paint();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            // 페인트 객체 초기화
            mPaint.reset();
            mPaint.setAntiAlias(true);
            // (1) 점 찍기
            mPaint.setStrokeWidth(20);
            canvas.drawPoint(30, 30, mPaint);
            // (2) 선 그리기
            mPaint.setStrokeWidth(8);
            canvas.drawLine(50, 50, 200, 100, mPaint);
            // (3) 도형 그리기: 직사각형, 원, 타원
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(Color.RED);
            canvas.drawRect(250, 50, 450, 100, mPaint);
            mPaint.setColor(Color.GREEN);
            canvas.drawCircle(100, 200, 50, mPaint);
            mPaint.setColor(Color.BLUE);
            canvas.drawOval(new RectF(200, 150, 400, 250), mPaint);
            // (4) 경로 그리기
            mPaint.setColor(Color.CYAN);
            Path path = new Path();
            path.moveTo(50, 300);
            path.lineTo(90, 310);
            path.lineTo(110, 290);
            path.lineTo(130, 330);
            path.lineTo(160, 310);
            canvas.drawPath(path, mPaint);
            // (5) 텍스트 출력
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(Color.MAGENTA);
            mPaint.setTextSize(100);
            mPaint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText("안녕하세요",200, 400, mPaint);
        }
    }
}
