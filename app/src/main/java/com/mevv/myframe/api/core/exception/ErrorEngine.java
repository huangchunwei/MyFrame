package com.mevv.myframe.api.core.exception;


import com.mevv.myframe.api.core.api.ApiTaskStack;

/**
 * Created by VV on 2016/10/19.
 * 错误码驱动器
 */

public class ErrorEngine {

    public static final int SERVICE_EXCEPTION = -0x12600;

    private static ErrorEngine sErrorEngine;

    private ErrorEngine() {
    }

    public static ErrorEngine getInstance() {
        if (sErrorEngine == null) {
            synchronized (ApiTaskStack.class) {
                if (sErrorEngine == null)
                    sErrorEngine = new ErrorEngine();
            }
        }
        return sErrorEngine;
    }

    public void processError(int code, String msg){
        //TODO 具体错误码待处理
        if (code == SERVICE_EXCEPTION){//非Api异常,服务器异常,网路异常等.

        }else{//Api异常

        }
    }


}
