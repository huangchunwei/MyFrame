package com.mevv.myframe.common.util;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by VV on 2016/9/21.
 * Activity任务栈管理
 */
public class ActManager {
    private static ActManager sActivityManager;
    private Stack<Activity> mStack = new Stack<Activity>();

    private ActManager() {
    }

    public static ActManager getInstances() {
        if (sActivityManager == null) {
            synchronized (ActManager.class) {
                if (sActivityManager == null)
                    sActivityManager = new ActManager();
            }
        }
        return sActivityManager;
    }

    /**
     * 添加Activity到栈顶
     *
     * @param activity
     */
    public void add(Activity activity) {
        if (!mStack.contains(activity)) {
            mStack.push(activity);
        }
    }

    /**
     * 从任务栈中移除并不关闭Activity
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        if (mStack.size() == 0)
            return;
        if (activity != null)
            mStack.remove(activity);
    }

    /**
     * 移除栈顶Activity
     */
    public void removeTop() {
        if (mStack.size() == 0)
            return;
        mStack.pop();//取出栈顶元素并移除
    }

    /**
     * 关闭栈顶Activity
     */
    public void finishTop(){
        if (mStack.size() == 0)
            return;
        Activity activity = mStack.pop();//取出栈顶元素并移除
        if (activity != null && !activity.isFinishing())
            activity.finish();
    }

    /**
     * 获取栈顶任务引用
     *
     * @return
     */
    public Activity getTopActivity() {
        if (mStack.size() == 0)
            return null;
        return mStack.peek();
    }

    /**
     * 取出栈底activity引用
     *
     * @return
     */
    public Activity getLastActivity() {
        if (mStack.size() == 0)
            return null;
        return mStack.lastElement();
    }

    /**
     * 关闭所有activity并从任务栈中移除所有任务
     */
    public void finishAll() {
        if (mStack.size() == 0)
            return;
        for (Activity activity : mStack) {
            if (activity != null && !activity.isFinishing())
                activity.finish();
        }
        mStack.clear();
    }

    public void finishAllBut(Activity activity){
        if (mStack.contains(activity)) {//包含指定Activity
            //先从任务栈中移除
            removeActivity(activity);
            //关闭所有
            finishAll();
            //再添加
            add(activity);
        }else{//不包含指定Activity
            //关闭所有
            finishAll();
            //再添加
            add(activity);
        }
    }

}
