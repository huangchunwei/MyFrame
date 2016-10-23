package com.mevv.myframe.common.util;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mevv.myframe.R;


/**
 * Created by VV on 2016/9/28.
 */

public class DialogManager {

    private static Dialog sOfflineDialog;

    /**
     * 显示token失效dialog
     *
     * @param context
     */
    public static void showOfflineDialog(final Context context) {
//        UserManager.getInstance().clearUserInfo();
        if (sOfflineDialog != null)
            return;
        sOfflineDialog = new Dialog(context, R.style.style_loading_dialog);
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.layout_dialog_offline, null);
        TextView textView = (TextView) v.findViewById(R.id.tv_dialog_offline_tip);
        v.findViewById(R.id.tv_dialog_offline_confirm_tip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sOfflineDialog.dismiss();
                sOfflineDialog = null;
//                SceneManager.toScene(context, LoginActivity.class, null);
            }
        });
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(UIUtils.dip2px(context, 240), UIUtils.dip2px(context, 120));
        layoutParams.weight = Gravity.CENTER;
        sOfflineDialog.setContentView(v, layoutParams);// 设置布局
        sOfflineDialog.setCancelable(false);// 不可以用“返回键”取消
        sOfflineDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        sOfflineDialog.show();
    }

}
