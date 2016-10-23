package com.mevv.myframe.common.util;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.mevv.myframe.BaseApplication;


/**
 * Created by VV on 2016/9/21.
 */

public class TipUtil {
    private static Handler sHandler = new Handler();
    public static boolean isShow = true;

    /*cannot be instantiated*/
    private TipUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static void showToast(final String msg) {
        if (isShow) {
            sHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(BaseApplication.getApplication(), msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public static void showLongToast(final String msg) {
        if (isShow) {
            sHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(BaseApplication.getApplication(), msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public static void showToast(final Activity activity, final String msg) {
        if (activity == null || activity.isFinishing()) {
            return;
        }
        if (isShow) {
            Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
        }
    }

    public static void showToast(final Context context, final String msg) {
        if (context == null) {
            return;
        }
        if (isShow) {
            sHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

//    public static void showSnackbar(@NonNull final View view, final String msg) {
//        if (isShow) {
//            sHandler.post(new Runnable() {
//                @Override
//                public void run() {
//                    Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
//                }
//            });
//        }
//    }
}
