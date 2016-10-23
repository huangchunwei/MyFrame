package com.mevv.myframe.common.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.mevv.myframe.R;
import com.mevv.myframe.base.BaseActivity;
import com.mevv.myframe.base.BaseSwipeBackActivity;


/**
 * Created by VV on 2016/9/21.
 */
public class SceneManager {

    public static void toScene(Context context, Class<? extends Activity> target, Bundle data) {
        Intent intent = new Intent();
        intent.setClass(context, target);
        if (data != null) {
            intent.putExtras(data);
        }
        context.startActivity(intent);
        if (context instanceof BaseActivity || context instanceof BaseSwipeBackActivity) {
            ((Activity) context).overridePendingTransition(R.anim.b_enter_anim, R.anim.b_exit_anim);
        }
    }
}
