package com.yanle.demo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WaitNotifyCycle {
    static boolean flag = true;

    static Object lock = new Object();

    volatile static int number = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread waitThread = new Thread(new Wait(), "WaitThread");
        waitThread.start();
        Thread.sleep(1);

        Thread notifyThread = new Thread(new Notify(), "NotifyThread");
        notifyThread.start();
    }

    static class Wait implements Runnable {
        @Override
        public void run() {
            while (true) {
                synchronized (lock) {
                    while (flag && number<=20) {
                        try {
                            System.out.println(Thread.currentThread() + "flag is true.wait@" + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                            System.out.println(number);
                            number = number + 1;
                            flag = false;
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

    }

    static class Notify implements Runnable {

        @Override
        public void run() {
            while (true) {
                synchronized (lock) {
                    while (!flag && number<=20){
                        System.out.println(Thread.currentThread() + "hold lock .notify@" + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                        System.out.println(number);
                        number = number + 1;
                        lock.notifyAll();
                        flag = true;
                    }
                }
            }
        }
    }
}
