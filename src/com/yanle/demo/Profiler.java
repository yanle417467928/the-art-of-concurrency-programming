package com.yanle.demo;

import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: Le Yan
 * @date: 2018/11/26 16:05
 */
public class Profiler {

    private static final ThreadLocal<Long> TIME_THREADLOCAL = new ThreadLocal<>();

    public static final void begin() {
        TIME_THREADLOCAL.set(System.currentTimeMillis());
    }

    public static final long end() {
        return System.currentTimeMillis() - TIME_THREADLOCAL.get();
    }

    public static void main(String[] args) throws InterruptedException {
        Profiler.begin();

        TimeUnit.SECONDS.sleep(1);

        System.out.println("Cost:" + Profiler.end() + "mills");
    }
}
