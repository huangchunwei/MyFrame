package com.mevv.myframe.common.exception;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Date;

/**
 * Created by VV on 2016/9/21.
 */

public class SaveOnCacheDir implements ExceptionHelper.SaveModel {

    private final Context mContext;
    protected static final DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);

    public SaveOnCacheDir(Context context) {
        mContext = context;
    }
    @Override
    public void onSave(Throwable throwable) {
        PrintWriter pw = null;
        try {
//            File path = new File(FileUtil.getDiskCacheDir(mContext) + "/log");
            File path = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/ZHCrashLog");
            if (!path.exists())
                path.mkdirs();
            File errorFile = new File(path + "/crash.log");
            if (!errorFile.exists())
                errorFile.createNewFile();
            pw = new PrintWriter(new BufferedWriter(new FileWriter(errorFile,true)));
            pw.print("\n\n\n-----错误分割线 START " + dateFormat.format(new Date()) + "-----");
            pw.println();
            dumpPhoneInfo(pw);
            pw.println();
            throwable.printStackTrace(pw);
            pw.print("\n\n----------错误分割线 END --------\n\n");
            pw.flush();
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pw != null)
                pw.close();
        }
    }


    private void dumpPhoneInfo(PrintWriter pw) throws PackageManager.NameNotFoundException {
        PackageManager pm = this.mContext.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(this.mContext.getPackageName(), 1);
        pw.print("App Version: ");
        pw.print(pi.versionName);
        pw.print('_');
        pw.println(pi.versionCode);
        pw.println();

        pw.print("OS Version: ");
        pw.print(Build.VERSION.RELEASE);
        pw.print("_");
        pw.println(Build.VERSION.SDK_INT);
        pw.println();

        pw.print("Vendor: ");
        pw.println(Build.MANUFACTURER);
        pw.println();

        pw.print("Model: ");
        pw.println(Build.MODEL);
        pw.println();

        pw.print("CPU ABI: ");
        pw.println(Build.CPU_ABI);
        pw.println();
    }

}
