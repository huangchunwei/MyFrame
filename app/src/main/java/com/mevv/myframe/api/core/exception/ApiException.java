package com.mevv.myframe.api.core.exception;


/**
 * Created by VV on 2016/10/14.
 * 自定义api请求异常
 */

public class ApiException extends RuntimeException {

    public ApiException(int resultCode, String errorMsg) {
        this(getApiExceptionMessage(resultCode, errorMsg));
    }

    public ApiException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
     * 需要根据错误码对错误信息进行一个转换，在显示给用户
     *
     * @param code
     * @param errorMsg
     * @return
     */
    private static String getApiExceptionMessage(int code, String errorMsg) {
        ErrorEngine.getInstance().processError(code, errorMsg);
        return errorMsg+"==>"+code;
    }
}
