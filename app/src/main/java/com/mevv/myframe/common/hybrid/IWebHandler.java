package com.mevv.myframe.common.hybrid;

import android.content.Context;

/**
 * Created by VV on 2016/9/12.
 */
public interface IWebHandler {
    /**
     * 处理事件对象的名称
     *
     * @return
     */
    String getHandlerName();

    /**
     * 对应的实现类 处理对应的事件任务  返回true 代表处理了， false 则是没有处理
     * @param context
     * @param string
     * @return
     */
    boolean handleTask(Context context, String string);

    /**
     * 对应的实现类 处理对应的事件任务  返回true 代表处理了， false 则是没有处理
     * @param context
     * @param code
     * @return
     */
    boolean handleTask(Context context, int code);
}
