package com.huadong.musicvideoplayer.threadpool;

import android.util.Log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by xl on 2017/6/22.
 */

public class MyThreadPool extends ThreadPoolExecutor{

    public MyThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        Log.d("DHD", "beforeExecute: 开始执行任务！");
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        Log.d("DHD", "beforeExecute: 任务执行结束！");
    }

    @Override
    protected void terminated() {
        super.terminated();
        //当调用shutDown()或者shutDownNow()时会触发该方法
        Log.d("DHD", "terminated: 线程池关闭！");
    }
}
