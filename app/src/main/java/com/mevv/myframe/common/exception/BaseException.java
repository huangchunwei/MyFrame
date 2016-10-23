package com.mevv.myframe.common.exception;

/**
 * Created by VV on 2016/9/21.
 * 自定义异常基类
 */

public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BaseException() {
        super();
    }

    public BaseException(String exceptionMsg) {
        super(exceptionMsg);
    }

    public BaseException(Throwable throwable) {
        super(throwable);
    }

    public BaseException(String exceptionMsg, Throwable throwable) {
        super(exceptionMsg, throwable);
    }

}
