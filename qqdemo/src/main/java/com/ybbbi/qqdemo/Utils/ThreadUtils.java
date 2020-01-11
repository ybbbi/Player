package com.ybbbi.qqdemo.Utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ThreadUtils {
    private static Executor executor = Executors.newSingleThreadExecutor();
    private static Handler handler=new Handler(Looper.getMainLooper());
    /**
     * 在子线程中运行
     */
    public static void runOnChildThread(Runnable r) {
        executor.execute(r);
    }

    /**
     * 在主线程运行
     */
    public static void runOnMainThread(Runnable r) {
        handler.post(r);
    }
}
