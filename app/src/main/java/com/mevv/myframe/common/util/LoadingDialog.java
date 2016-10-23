package com.mevv.myframe.common.util;

import android.app.Dialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mevv.myframe.R;
import com.mevv.myframe.api.core.api.ApiTaskStack;
import com.mevv.myframe.widget.view.LVCircularSmile;


/**
 * Created by VV on 2016/10/19.
 */

public class LoadingDialog {

    private static Dialog sLoadingDialog;
    private static final String TIP_STR = "加载中";

    /**
     * 自定义progressDialog
     *
     * @param msg
     */
    public static void showLoadingDialog(String msg, Object tag) {
        if (sLoadingDialog != null && sLoadingDialog.isShowing())
            sLoadingDialog.dismiss();
        createLoadingDialog(msg, tag).show();
    }

    public static void showLoadingDialog(Object tag){
        if (sLoadingDialog != null && sLoadingDialog.isShowing())
            sLoadingDialog.dismiss();
        createLoadingDialog(TIP_STR, tag).show();
    }

    public static void showLoadingDialog(String msg){
        if (sLoadingDialog != null && sLoadingDialog.isShowing())
            sLoadingDialog.dismiss();
        createLoadingDialog(msg, null).show();
    }

    /**
     * 得到自定义的progressDialog
     *
     * @param
     * @param msg
     * @return
     */
    private static Dialog createLoadingDialog(String msg, final Object tag) {
        LayoutInflater inflater = LayoutInflater.from(ActManager.getInstances().getTopActivity());
        View v = inflater.inflate(R.layout.layout_dialog_loading, null);
        LVCircularSmile lvCircularSmile = (LVCircularSmile) v.findViewById(R.id.lv_loading_view);
        lvCircularSmile.startAnim();
        if (!TextUtils.isEmpty(msg)) {
            TextView textView = (TextView) v.findViewById(R.id.tv_loading_tip_msg);
            textView.setText(msg);
            textView.setVisibility(View.VISIBLE);
        }
        // 创建自定义样式dialog
        sLoadingDialog = new Dialog(ActManager.getInstances().getTopActivity(), R.style.style_loading_dialog);
        sLoadingDialog.setCancelable(true);// 返回键是否可以取消
        sLoadingDialog.setContentView(v, new LinearLayout.LayoutParams(UIUtils.dip2px(ActManager.getInstances().getTopActivity(), 100), UIUtils.dip2px(ActManager.getInstances().getTopActivity(), 100)));// 设置布局
        sLoadingDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        if (tag != null){
            sLoadingDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    cancelTask(tag);
                }
            });
        }
        return sLoadingDialog;
    }

    /**
     * 隐藏自定义progressDialog
     */
    public static void dismissLoadingDialog() {
        if (sLoadingDialog != null && sLoadingDialog.isShowing())
            sLoadingDialog.dismiss();
    }

    /**
     * 和Dialog绑定,当Dialog消失则取消网络任务
     * @param tag
     */
    private static void cancelTask(Object tag){
        ApiTaskStack.getInstance().cancelTask(tag);
    }

}
