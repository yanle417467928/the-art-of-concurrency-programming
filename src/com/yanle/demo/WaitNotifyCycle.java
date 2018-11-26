package com.yanle.demo;

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
            outer:while (number <= 20) {
                synchronized (lock) {
                    while (flag) {
                        try {
                            if (number > 20) {
                                lock.notifyAll();
                                break outer;
                            }
                            System.out.println(Thread.currentThread() + ":" + number);
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
            outer:
            while (true) {
                synchronized (lock) {
                    while (!flag) {
                        if (number > 20) {
                            lock.notifyAll();
                            break outer;
                        }
                        System.out.println(Thread.currentThread() + ":" + number);
                        number = number + 1;
                        lock.notifyAll();
                        flag = true;
                    }
                }
            }
        }
    }
}
