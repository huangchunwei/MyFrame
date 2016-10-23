package com.mevv.myframe.widget.edittext;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

import com.mevv.myframe.R;


/**
 * Created by VV on 2016/9/22.
 */

public class DelEditText extends EditText implements View.OnTouchListener, TextWatcher, View.OnFocusChangeListener {

    private Context mContext;
    private Drawable mRightDrawable;
    private static SetTextWatchListener listener;

    public DelEditText(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public DelEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }

    public DelEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        mRightDrawable = getCompoundDrawables()[2];
        if (mRightDrawable == null) {
            mRightDrawable = mContext.getResources().getDrawable(R.drawable.selector_del_edittext);
            mRightDrawable.setBounds(0, 0, mRightDrawable.getIntrinsicWidth(), mRightDrawable.getIntrinsicHeight());
        }

        // setBackgroundResource(R.drawable.bg_edittext);
        setTextColor(getResources().getColor(R.color.color_selfedit_text));
        setHintTextColor(getResources().getColor(R.color.color_selfedit_hint));
        setCursorVisible(false);
        // android:textCursorDrawable="@null
        manageClearButton();
        setOnTouchListener(this);
        addTextChangedListener(this);
        setOnFocusChangeListener(this);

        // setHeight(dip2px(LoginActivity.getDensity(), 40));
    }

    void manageClearButton() {
        if (this.getText().toString().equals("")) {
            removeClearButton();
        } else {
            addClearButton();
        }
    }

    public static int dip2px(final float density, final float dipValue) {
        return (int) (dipValue * density + 0.5f);
    }

    public void addClearButton() {
        this.setCompoundDrawables(this.getCompoundDrawables()[0], this.getCompoundDrawables()[1], mRightDrawable, this.getCompoundDrawables()[3]);
    }

    public void removeClearButton() {
        this.setCompoundDrawables(this.getCompoundDrawables()[0], this.getCompoundDrawables()[1], null, this.getCompoundDrawables()[3]);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        DelEditText et = DelEditText.this;
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            et.setCursorVisible(true);
        }
        if (et.getCompoundDrawables()[2] == null)
            return false;
        if (event.getAction() != MotionEvent.ACTION_UP) {

            return false;
        }

        if (event.getX() > et.getWidth() - et.getPaddingRight() - mRightDrawable.getIntrinsicWidth() - 10) {
            et.setText("");
            DelEditText.this.removeClearButton();
        }
        return false;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        super.onTextChanged(s, start, before, count);
        DelEditText.this.manageClearButton();
        if (listener != null) {
            listener.onTextChanged(s, start, before, count);
        }

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (listener != null) {
            listener.afterTextChanged(s);
        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (listener != null) {
            listener.beforeTextChanged(s, start, count, after);
        }

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            if (length() > 0) {
                addClearButton();
            }
        } else {
            removeClearButton();
        }
    }

    public static void setListner(SetTextWatchListener textlistener) {
        listener = textlistener;

    }

    public void hideRigthPic(){
        this.setCompoundDrawables(this.getCompoundDrawables()[0], this.getCompoundDrawables()[1], null, this.getCompoundDrawables()[3]);
    }

    public void showRightPic(){
        this.setCompoundDrawables(this.getCompoundDrawables()[0], this.getCompoundDrawables()[1], mRightDrawable, this.getCompoundDrawables()[3]);
    }

    /**
     * @Description 刷新回调接口
     *
     */
    public interface SetTextWatchListener {
        void onTextChanged(CharSequence s, int start, int before, int count);

        void afterTextChanged(Editable s);

        void beforeTextChanged(CharSequence s, int start, int count, int after);
    }

    /**
     * 设置默认抖动动画
     */
    public void setShakeAnimation() {
        this.startAnimation(shakeAnimation(5));
    }

    /**
     * 自定义抖动动画效果
     * @param shakeAnimation
     */
    public void setShakeAnimation(Animation shakeAnimation){
        this.startAnimation(shakeAnimation);
    }

    /**
     * 晃动动画
     *
     * @param counts
     *            1秒钟晃动多少下
     * @return
     */
    public static Animation shakeAnimation(int counts) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
        //设置一个循环加速器，使用传入的次数就会出现摆动的效果。
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(500);
        return translateAnimation;
    }

}
