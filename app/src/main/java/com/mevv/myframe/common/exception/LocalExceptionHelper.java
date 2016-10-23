package com.mevv.myframe.common.exception;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import com.mevv.myframe.common.util.ThreadManager;


/**
 * Created by VV on 2016/9/21.
 */

public class LocalExceptionHelper extends ExceptionHelper {

    private Context mContext;

    public LocalExceptionHelper(Context context) {
        this.mContext = context;
    }

    @Override
    public boolean handleException(Throwable ex) {
        if (ex == null)
            return false;

        ThreadManager.execute(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "很抱歉, 应用出现异常...", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        });
        saveLog(new SaveOnCacheDir(mContext), ex);
        return true;
    }

    /**
     * 保存异常日志
     * @param saveModel
     * @param throwable
     */
    private void saveLog(SaveModel saveModel, Throwable throwable) {
        saveModel.onSave(throwable);
    }
}
