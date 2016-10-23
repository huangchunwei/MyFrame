package com.mevv.myframe.common.hybrid;

/**
 * Created by VV on 2016/9/12.
 * WebHandler工厂类
 */
public class WebHandlerFactory {
    private WebBaseActivity activity;

    public WebHandlerFactory(WebBaseActivity activity) {
        this.activity = activity;
    }

    public IWebHandler createHybridHandler(String str) {
        //获取缓存
        IWebHandler iWebHandler = activity.getWebHandlerMap().get(str);
        if (iWebHandler != null)
            return iWebHandler;
        if (str.equals(WebConstants.URL_TASK)) {//Url处理
            UrlWebHandler urlHandler = new UrlWebHandler(activity);
            activity.addHybridHandler(urlHandler.getHandlerName(), urlHandler);
            return urlHandler;
        }
        if (str.equals(WebConstants.CUSTOM_MESSAGE_TASK)) {//自定义消息处理,主要处理H5和本地交互事件
            CustomWebHandler customWebHandler = new CustomWebHandler(activity);
            activity.addHybridHandler(customWebHandler.getHandlerName(), customWebHandler);
            return customWebHandler;
        }
        return null;
    }
}
