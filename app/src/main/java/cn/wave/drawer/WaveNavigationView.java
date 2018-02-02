package cn.wave.drawer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Region;
import android.support.design.widget.NavigationView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * created by sfx on 2018/1/31.
 */

public class WaveNavigationView extends NavigationView {

    public WaveNavigationView(Context context) {
        super(context);
    }

    public WaveNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WaveNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        //绘制波浪
        drawWavePath(canvas);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                mCenterY = (int) y;
                if (!checkCenterY()) {
                    postInvalidate();
                }

                break;
        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

    }

    // 计算波浪贝塞尔曲线的角弧长值
    private static final double ANGLE = Math.PI * 45 / 180;
    private static final double ANGLE_R = Math.PI * 90 / 180;
    // 波浪路径
    private Path mWavePath = new Path();
    // 手指滑动的Y点作为中心点
    private int mCenterY; //中心点Y

    private int mWidth;
    private int mHeight;
    // 用于绘制贝塞尔曲线的比率
    private float mOffset = 0.5f;

    public void setOffset(float offset) {
        mOffset = offset;
        postInvalidate();
    }

    /**
     * @return true 没有改变
     */
    private boolean checkCenterY() {
        final int min = (int) (mHeight * 0.1);
        final int max = (int) (mHeight * 0.9);
        final int temp = mCenterY;

        if (mCenterY <= min) {
            mCenterY = min;
        } else if (mCenterY >= max) {
            mCenterY = max;
        } else {
            return false;
        }
        return temp == mCenterY;
    }

    /**
     * 绘制波浪
     *
     * @param canvas
     */
    private void drawWavePath(Canvas canvas) {
        mWavePath.reset();
        mWavePath.moveTo(0, 0);
        float x1, y1, x2, y2, xe, ye;
        //
        x1 = mOffset * mWidth;
        y1 = mCenterY;

        //
        xe = 0;
        ye = 1.5f * mCenterY;
        ye = ye >= mHeight ? ye : mHeight;
        //
        x2 = mOffset * x1;
        y2 = mCenterY;
        //
        mWavePath.cubicTo(x1, y1, x2, y2, xe, ye);


        mWavePath.close();
        paint.setColor(Color.RED);
        canvas.drawPath(mWavePath, paint);
        drawPoint(canvas, "1", x1, y1, Color.BLUE);
        drawPoint(canvas, "2", x2, y2, Color.DKGRAY);
        drawPoint(canvas, "p", mWidth - 16, mCenterY, Color.CYAN);
    }

    Paint paint = new Paint();

    private void drawPoint(Canvas canvas, String text, float x, float y, int color) {

        paint.setStrokeWidth(5);
        paint.setTextSize(48);
        paint.setColor(Color.BLACK);
        canvas.drawText(text, x, y, paint);
        paint.setColor(color);
        canvas.drawCircle(x, y, 8, paint);
    }
}
