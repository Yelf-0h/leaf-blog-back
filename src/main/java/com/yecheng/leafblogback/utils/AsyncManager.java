package com.yecheng.leafblogback.utils;

import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 异步管理
 *
 * @author Yelf
 * @date 2023/06/25
 */
public class AsyncManager {
    /**
     * 操作延迟10毫秒
     */
    private final int OPERATE_DELAY_TIME = 10;

    /**
     * 异步操作任务调度线程池
     */
    private ScheduledExecutorService executor = SpringUtils.getBean("scheduledExecutorService");

    /**
     * 单例模式
     */
    private AsyncManager() {
    }

    private static AsyncManager me = new AsyncManager();

    public static AsyncManager me() {
        return me;
    }

    /**
     * 执行任务
     *
     * @param task 任务
     */
    public void execute(TimerTask task) {
        executor.schedule(task, OPERATE_DELAY_TIME, TimeUnit.MILLISECONDS);
    }

    /**
     * 延迟执行任务
     *
     * @param task      任务
     * @param delayTime 延迟执行 单位：毫秒
     */
    public void execute(TimerTask task, int delayTime) {
        executor.schedule(task, delayTime, TimeUnit.MILLISECONDS);
    }

    /**
     * 停止任务线程池
     */
    public void shutdown() {
        Threads.shutdownAndAwaitTermination(executor);
    }


    /**
     * 根据线程名称操作指定线程
     *
     * @param name 名称
     * @param type 操作类型: 1 = 终止 ，2 = 唤醒
     * @return
     */
    public boolean operateThreadByName(String name, int type) {
        // 获取所有线程
        ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
        int noThreads = currentGroup.activeCount();
        Thread[] lstThreads = new Thread[noThreads];
        currentGroup.enumerate(lstThreads);

        for (int i = 0; i < noThreads; i++) {
            String threadName = lstThreads[i].getName();
            // 操作指定的线程
            if (threadName.equals(name)) {
                switch (type) {
                    case 1:
                        lstThreads[i].interrupt();
                        return true;
                    case 2:
                        synchronized (lstThreads[i]) {
                            lstThreads[i].notify();
                        }
                        return true;
                    default:
                }
            }
        }
        return false;
    }
}