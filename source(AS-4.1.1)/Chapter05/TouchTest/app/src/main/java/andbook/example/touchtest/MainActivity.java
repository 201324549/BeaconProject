package andbook.example.touchtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyView(this));
    }

    private static class MyView extends View {
        private static final int R = 50;
        private ArrayList<Float> mPoints;
        private Paint mPaint;

        public MyView(Context context) {
            super(context);
            mPoints = new ArrayList<>();
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setColor(Color.MAGENTA);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            for (int i = 0; i < mPoints.size(); i += 2) {
                float x = mPoints.get(i);
                float y = mPoints.get(i + 1);
                canvas.drawRect(x - R, y - R, x + R, y + R, mPaint);
            }
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPoints.add(event.getX());
                mPoints.add(event.getY());
                Log.d("test", event.getX() + "," + event.getY());
                invalidate(); // 안드로이드 시스템이 ondDraw()를 자동으로 호출한다!!!
                return true;
            case MotionEvent.ACTION_MOVE:
                mPoints.add(event.getX());
                mPoints.add(event.getY());
                Log.d("test", event.getX() + "," + event.getY());
                invalidate();
                return true;
            case MotionEvent.ACTION_UP:
                return true;
            }
            return super.onTouchEvent(event);
        }
    }
}
