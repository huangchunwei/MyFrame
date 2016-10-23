package com.mevv.myframe.api.core.api;


import com.mevv.myframe.common.util.ThreadManager;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import rx.Subscription;

/**
 * Created by VV on 2016/10/14.
 * api请求任务栈管理
 * 由于Retrofit的请求一旦发出就不可取消,所以这里变相取消观察者的监听来实现取消网络请求操作
 * 目的只是让观察者收不到订阅消息,但网络请求一旦发出会一直进行,直至请求结束.
 */

public class ApiTaskStack {

    private static ApiTaskStack sApiStack;
    private static LinkedHashMap<Object, Subscription> sSubsStack;
    private static LinkedHashMap<Object, Call> sCallStack;
    /**
     * 统一tag订阅者如果存在,前一个订阅者是否取消订阅,
     * 如果为false,覆盖同tag的订阅者,但不取消前一个订阅者的订阅
     * 如果为true,覆盖同tag的订阅者,前一个并取消订阅者的订阅
     */
    public static boolean SUBS_TASK_SINGLE = false;//订阅者
    public static boolean CALL_TASK_SINGLE = true;//Call, 默认同一个tag的Call会取消前一个

    private ApiTaskStack() {
        sSubsStack = new LinkedHashMap<>();
        sCallStack = new LinkedHashMap<>();
    }

    public static ApiTaskStack getInstance() {
        if (sApiStack == null) {
            synchronized (ApiTaskStack.class) {
                if (sApiStack == null)
                    sApiStack = new ApiTaskStack();
            }
        }
        return sApiStack;
    }

    public void subsEnqueue(Subscription subscription, Object tag) {
        if (SUBS_TASK_SINGLE) {//取消同tag前一个订阅者的订阅
            if (sSubsStack.containsKey(tag)) {
                Subscription localSub = sSubsStack.get(tag);
                if (!localSub.isUnsubscribed())
                    localSub.unsubscribe();
            }
        }
        sSubsStack.put(tag, subscription);
    }

    public void callEnqueue(Call call, Object tag) {
        if (CALL_TASK_SINGLE) {//取消同tag前一个网络请求
            if (sCallStack.containsKey(tag)) {
                Call inCall = sCallStack.get(tag);
                if (inCall != null)
                    inCall.cancel();
            }
        }
        sCallStack.put(tag, call);
    }


    public void cancelSubs(Object tag) {
        if (sSubsStack.containsKey(tag)) {
            Subscription subscription = sSubsStack.get(tag);
            if (!subscription.isUnsubscribed())
                subscription.unsubscribe();
            sSubsStack.remove(tag);
        }
    }

    public void cancelCall(Object tag) {
        if (sCallStack.containsKey(tag)) {
            Call call = sCallStack.get(tag);
            if (call != null)
                call.cancel();
            sCallStack.remove(tag);
        }
    }

    public void removeSubs(Object tag) {
        if (tag != null && sSubsStack.containsKey(tag))
            sSubsStack.remove(tag);
    }

    public void removeCall(Object tag) {
        if (tag != null && sCallStack.containsKey(tag))
            sCallStack.remove(tag);
    }

    public void cancelTask(Object tag) {
        if (sSubsStack.containsKey(tag)) {
            cancelSubs(tag);
            sSubsStack.remove(tag);
        }
        if (sCallStack.containsKey(tag)) {
            cancelCall(tag);
            sCallStack.remove(tag);
        }
    }

    public void removeTask(Object tag) {
        if (sSubsStack.containsKey(tag))
            removeSubs(tag);
        if (sCallStack.containsKey(tag))
            removeCall(tag);
    }


    public void cancelAll() {
        ThreadManager.execute(new Runnable() {
            @Override
            public void run() {
                //取消所有订阅者
                Set<Map.Entry<Object, Subscription>> entrySet = sSubsStack.entrySet();
                for (Map.Entry<Object, Subscription> entry : entrySet) {
                    Subscription subscription = entry.getValue();
                    if (subscription != null) {
                        if (!entry.getValue().isUnsubscribed())
                            entry.getValue().unsubscribe();
                    }
                }
                sSubsStack.clear();

                //取消所有网络请求
                Set<Map.Entry<Object, Call>> callEntrySet = sCallStack.entrySet();
                for (Map.Entry<Object, Call> callEntry : callEntrySet) {
                    Call call = callEntry.getValue();
                    if (call != null)
                        call.cancel();
                }
                sCallStack.clear();
            }
        });
    }
}
