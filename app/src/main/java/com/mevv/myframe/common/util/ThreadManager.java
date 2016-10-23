package com.mevv.myframe.common.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by VV on 2016/9/21.
 */

public class ThreadManager {
    private static final ExecutorService threadPool = Executors.newCachedThreadPool();

    public static void execute(Runnable runnable){
        threadPool.execute(runnable);
    }
}
