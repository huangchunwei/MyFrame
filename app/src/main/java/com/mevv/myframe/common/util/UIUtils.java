package com.mevv.myframe.common.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.mevv.myframe.BaseApplication;

import java.net.URL;


/**
 * Created by Wayne on 2016/7/28.
 * Email: loveuu715@163.com
 */
public class UIUtils {

    public static Context getContext() {
        return BaseApplication.getApplication();
    }

    /**
     * dip转换px
     */
    public static int dip2px(Context context, float f) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (f * scale + 0.5f);
    }

    /**
     * pxz转换dip
     */
    public static int px2dip(Context context, int px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    public static float sp2px(Context context, float sp) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    public static View inflate(Context context, int resId) {
        return LayoutInflater.from(context).inflate(resId, null);
    }

    /**
     * 获取资源
     */
    public static Resources getResources(Context context) {
        return context.getResources();
    }

    /**
     * 获取文字
     */
    public static String getString(Context context, int resId) {
        return context.getResources().getString(resId);
    }

    /**
     * 获取文字数组
     */
    public static String[] getStringArray(Context context, int resId) {
        return context.getResources().getStringArray(resId);
    }

    /**
     * 获取dimen
     */
    public static int getDimens(Context context, int resId) {
        return getResources(context).getDimensionPixelSize(resId);
    }

    /**
     * 获取drawable
     */
    public static Drawable getDrawable(Context context, int resId) {
        return getResources(context).getDrawable(resId);
    }

    /**
     * 获取颜色
     */
    public static int getColor(Context context, int resId) {
        return getResources(context).getColor(resId);
    }

    /**
     * 获取颜色选择器
     */
    public static ColorStateList getColorStateList(Context context, int resId) {
        return getResources(context).getColorStateList(resId);
    }


    public static void clickLock(final Activity acticity, int id) {
        final View mView = acticity.findViewById(id);
        if (mView == null) {
            return;
        }
        mView.setEnabled(false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i >= 0; i--) {
                    try {
                        Thread.sleep(1500);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    acticity.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            mView.setEnabled(true);
                        }
                    });
                }
            }

        }).start();
    }

    /**
     * 获取控件宽
     */
    public static int getWidth(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        return (view.getMeasuredWidth());
    }

    /**
     * 获取控件高
     */
    public static int getHeight(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        return (view.getMeasuredHeight());
    }


    public static void reSetListViewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public static void setSelocter(final Context context, final ImageView imageView, final String image1, final String image2) {
        final StateListDrawable listDrawable = new StateListDrawable();
        final Handler mHandler = new Handler();
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Drawable nomal = Drawable.createFromStream(new URL(image1).openStream(), "src");
                    Drawable after = Drawable.createFromStream(new URL(image2).openStream(), "src");
                    //Non focused states
                    listDrawable.addState(new int[]{-android.R.attr.state_focused, -android.R.attr.state_selected, -android.R.attr.state_pressed},
                            nomal);
                    listDrawable.addState(new int[]{-android.R.attr.state_focused, android.R.attr.state_selected, -android.R.attr.state_pressed},
                            nomal);
                    //Focused states
                    listDrawable.addState(new int[]{android.R.attr.state_focused, -android.R.attr.state_selected, -android.R.attr.state_pressed},
                            after);
                    listDrawable.addState(new int[]{android.R.attr.state_focused, android.R.attr.state_selected, -android.R.attr.state_pressed},
                            after);
                    //Pressed
                    listDrawable.addState(new int[]{android.R.attr.state_selected, android.R.attr.state_pressed},
                            after);
                    listDrawable.addState(new int[]{android.R.attr.state_pressed},
                            after);
                    mHandler.post(new Runnable() {

                        @Override
                        public void run() {
                            imageView.setBackgroundDrawable(listDrawable);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                super.run();
            }
        };
        thread.start();
    }

}
