package vvme.camerademo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by VV on 2016/11/11.
 */

public class RectView extends View {
    private Context mContext;
    private int mScreenWidth;
    private int mScreenHeight;
    private Paint mPaint;
    private Rect mRect;
    private int radio;
    private Point mCenterPoint;
    private IAutoFocus mIAutoFocus;


    public RectView(Context context) {
        this(context, null);
    }

    public RectView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        getScreenMetric();
        initView();

    }

    private void initView() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setDither(true);//防抖动
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);//空心
        int marginLeft = (int) (mScreenWidth * 0.15);
        int marginTop = (int) (mScreenHeight * 0.25);
        mRect = new Rect(marginLeft, marginTop, mScreenWidth - marginLeft, mScreenHeight - marginTop);
        mCenterPoint = new Point(mScreenWidth / 2, mScreenHeight / 2);
        radio = (int) (mScreenWidth * 0.1);
    }

    private void getScreenMetric() {
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        mScreenWidth = displayMetrics.widthPixels;
        mScreenHeight = displayMetrics.heightPixels;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.RED);
        canvas.drawRect(mRect, mPaint);
        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(mCenterPoint.x, mCenterPoint.y, radio, mPaint);//外圆
        canvas.drawCircle(mCenterPoint.x, mCenterPoint.y, radio - 5, mPaint);//内圆
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                int x = (int) event.getX();
                int y = (int) event.getY();
                mCenterPoint = new Point(x,y);
                invalidate();
                if (mIAutoFocus != null && event.getAction() == MotionEvent.ACTION_UP) {
                    mIAutoFocus.autoFocus();
                }
                break;
        }
        return true;
    }

    public interface IAutoFocus {
        void autoFocus();
    }

    public void setAutoFocusListener(IAutoFocus autoFocusListener){
        mIAutoFocus = autoFocusListener;
    }
}
