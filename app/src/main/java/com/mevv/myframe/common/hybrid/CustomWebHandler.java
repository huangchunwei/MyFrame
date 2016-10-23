package com.mevv.myframe.common.hybrid;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by VV on 2016/9/12.
 * 自定义消息处理
 */
public class CustomWebHandler implements IWebHandler {
    private WebBaseActivity activity;
    private final String CODE = "code";//事件类型(包含错误码)
    private final String DATA = "data";//事件内容

    public CustomWebHandler(WebBaseActivity activity) {
        this.activity = activity;
    }

    @Override
    public String getHandlerName() {
        return WebConstants.CUSTOM_MESSAGE_TASK;
    }

    @Override
    public boolean handleTask(Context context, String result) {
        //自定义处理H5与原生交互逻辑,result 为交互数据,暂定义为json格式
        int code = -1;//事件码,默认为本地错误码-1
        String data = null;//事件内容
        try {
            //解析事件
            JSONObject jsonObject = new JSONObject(result);
            code = jsonObject.getInt(CODE);
            data = jsonObject.getString(DATA);

            //TODO 根据code类型进行相对应事件处理
            switch (code) {
                default:
                    break;
            }

            return true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean handleTask(Context context, int code) {
        return false;
    }
}
