package com.mevv.myframe.common.hybrid;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by VV on 2016/9/12.
 */
public class UrlWebHandler implements IWebHandler {

    private Context mContext;

    public UrlWebHandler(Context context) {
        mContext = context;
    }

    @Override
    public String getHandlerName() {
        return WebConstants.URL_TASK;
    }

    @Override
    public boolean handleTask(Context context, String url) {
        if (url.startsWith("tel:")) {//电话 是否需要弹窗提示
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(intent);
            return true;
        } else if (url.startsWith("smsto:")) {//发短信 是否需要弹窗提示
            Intent it = new Intent(Intent.ACTION_SENDTO, Uri.parse(url));
            it.putExtra("sms_body", "");
            context.startActivity(it);
            return true;
        } else if (url.startsWith("mailto:")) {//发邮件 是否需要弹窗提示
            Intent it = new Intent(Intent.ACTION_SENDTO, Uri.parse(url));
            context.startActivity(it);
            return true;
        } else if (url.startsWith("mqqwpa:")) {//是否需要弹窗提示
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(intent);
            return true;
        } else if (url.startsWith("http://") || url.startsWith("https://")) {//加载网页
//            if (context instanceof TransparentWebActivity)
//                ((TransparentWebActivity)context).getWebView().loadUrl(url);
            if (context instanceof WebBaseActivity)
                ((WebBaseActivity)context).getWebView().loadUrl(url);
            return true;

        }
        return false;
    }

    @Override
    public boolean handleTask(Context context, int code) {
        return false;
    }
}
