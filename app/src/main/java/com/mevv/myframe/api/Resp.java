package com.mevv.myframe.api;

/**
 * Created by VV on 2016/10/14.
 * 统一数据返回基类
 */

public class Resp<T> {
    private boolean status;
    private String info;
    private int errcode;
    private T data;

    public boolean isStatus() {
        return status;
    }

    public String getInfo() {
        return info;
    }

    public int getErrcode() {
        return errcode;
    }

    public T getData() {
        return data;
    }
}
