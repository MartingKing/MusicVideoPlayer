package com.huadong.musicvideoplayer.threadpool;

import android.os.SystemClock;

import java.util.concurrent.Callable;

/**
 * Created by xl on 2017/6/22.
 */

public class MyTask implements Callable<String> {

    private int taskId;

    public MyTask(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public String call() throws Exception {
        SystemClock.sleep(1000);
        //返回每一个任务的执行结果
        return "call()方法被调用----" + Thread.currentThread().getName() + "-------" + taskId;
    }
}
