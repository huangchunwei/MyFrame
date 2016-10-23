package com.mevv.myframe.widget.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mevv.myframe.R;


/**
 * Created by VV on 2016/10/21.
 */

public class FooterView extends LinearLayout {

    private ViewSwitcher progressCon;
    public final static int STATE_LOADING = 0;
    public final static int STATE_COMPLETE = 1;
    public final static int STATE_NOMORE = 2;
    private TextView mText;


    public FooterView(Context context) {
        super(context);
        initView();
    }

    /**
     * @param context
     * @param attrs
     */
    public FooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public void initView() {
        setGravity(Gravity.CENTER);
        setLayoutParams(new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        progressCon = new ViewSwitcher(getContext());
        progressCon.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

       /* AVLoadingIndicatorView progressView = new AVLoadingIndicatorView(this.getContext());
        progressView.setIndicatorColor(0xffB5B5B5);
        progressView.setIndicatorId(ProgressStyle.BallSpinFadeLoader);
        progressCon.setView(progressView);*/

        setProgressStyle(1);

        addView(progressCon);
        mText = new TextView(getContext());
        mText.setText("正在加载...");

        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins((int) getResources().getDimension(R.dimen.textandiconmargin), 0, 0, 0);
        mText.setLayoutParams(layoutParams);
        mText.setPadding(10,10,10,10);
        addView(mText);

    }

    public void setProgressStyle(int style) {
//        if(style == ProgressStyle.SysProgress){
        progressCon.setView(new ProgressBar(getContext(), null, android.R.attr.progressBarStyle));
//        }else{
//            AVLoadingIndicatorView progressView = new AVLoadingIndicatorView(this.getContext());
//            progressView.setIndicatorColor(0xffB5B5B5);
//            progressView.setIndicatorId(style);
//            progressCon.setView(progressView);
//        }
    }

    public void setState(int state) {
        switch (state) {
            case STATE_LOADING:
                progressCon.setVisibility(View.VISIBLE);
//                mText.setText("加载中...");
                mText.setVisibility(View.GONE);
                this.setVisibility(View.VISIBLE);
                break;
            case STATE_COMPLETE:
//                mText.setText("加载中...");
                mText.setVisibility(View.GONE);
                this.setVisibility(View.GONE);
                break;
            case STATE_NOMORE:
                mText.setText("-----加载完成-----");
                mText.setVisibility(View.VISIBLE);
                progressCon.setVisibility(View.GONE);
                this.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void setState(int state, String tip) {
        switch (state) {
            case STATE_LOADING:
                progressCon.setVisibility(View.VISIBLE);
//                mText.setText("加载中...");
                mText.setVisibility(View.GONE);
                this.setVisibility(View.VISIBLE);
                break;
            case STATE_COMPLETE:
//                mText.setText("加载中...");
                mText.setVisibility(View.GONE);
                this.setVisibility(View.GONE);
                break;
            case STATE_NOMORE:
                mText.setText(tip);
                mText.setVisibility(View.VISIBLE);
                progressCon.setVisibility(View.GONE);
                this.setVisibility(View.VISIBLE);
                break;
        }
    }

    public TextView getText(){
        return mText;
    }
}
