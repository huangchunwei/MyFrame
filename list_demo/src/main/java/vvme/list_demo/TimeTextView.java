package vvme.list_demo;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by VV on 2016/11/17.
 * 验证码计时
 */

public class TimeTextView extends TextView {

    private Context mContext;

    private int mWidth;
    private int mHeight;

    private Paint mPaint;

    private String mStr;
    private ValueAnimator mValueAnimator;

    private Timer mTimer;
    private int mCurrentTime = 60;
    private boolean isStop = false;


    public TimeTextView(Context context) {
        this(context, null);
    }

    public TimeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        initPaint();
        initTimeTask();
    }

    private void initTimeTask() {
        mCurrentTime = 60;
        mStr = mCurrentTime + "S";
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mCurrentTime--;
                mStr = mCurrentTime + "S";
                Log.i("hate", "当前值:" + mStr);
                if (mCurrentTime <= 0) {
                    isStop = true;
                    mTimer.cancel();
                }
            }
        }, 1000, 1000);
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setDither(true);//抗抖动
        mPaint.setStyle(Paint.Style.STROKE);//画笔风格
        mPaint.setStrokeWidth(5);//画笔宽度
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        measureWidth(widthMeasureSpec);
//        measureHeight(heightMeasureSpec);
//        设置测量后的结果
//        setMeasuredDimension(mWidth, mHeight);
    }

    private void measureHeight(int heightMeasureSpec) {
        int heiMode = MeasureSpec.getMode(heightMeasureSpec);
        int hei = MeasureSpec.getSize(heightMeasureSpec);

        /**
         * MeasureSpec.AT_MOST  想要多大就多大 如 warp_content
         * MeasureSpec.EXACTLY  精确值 如200dp / match_parent / fill_parent
         * MeasureSpec.UNSPECIFIED  不确定的 需要给一个默认值
         */

        if (heiMode == MeasureSpec.EXACTLY) {
            mHeight = hei;
        } else {//设置默认值

        }
    }

    private void measureWidth(int widthMeasureSpec) {
        int widMode = MeasureSpec.getMode(widthMeasureSpec);
        int wid = MeasureSpec.getSize(widthMeasureSpec);
        /**
         * MeasureSpec.AT_MOST  想要多大就多大 如 warp_content
         * MeasureSpec.EXACTLY  精确值 如200dp / match_parent / fill_parent
         * MeasureSpec.UNSPECIFIED  不确定的 需要给一个默认值
         */

        if (widMode == MeasureSpec.EXACTLY) {
            mWidth = wid;
        } else {//这是默认值

        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void startTimeTask() {
        initTimeTask();
    }

    public void stopTimeTask() {
        if (mTimer != null && !isStop) {
            mTimer.cancel();
            mTimer = null;
        }
    }
}
