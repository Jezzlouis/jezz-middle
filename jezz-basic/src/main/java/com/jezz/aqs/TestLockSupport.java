package com.jezz.aqs;

import java.util.concurrent.locks.LockSupport;

public class TestLockSupport {
    public static void main(String[] args) {
        Thread thread=new Thread(new Thread_park());
        thread.start();//阻塞当前线程
        Thread thread2=new Thread(new Thread_unpark(thread));
        thread2.start();//唤醒被阻塞的线程
    }
}

class Thread_park implements  Runnable{
    @Override
    public void run() {
        System.out.println("Thread_park开始");
        LockSupport.park(this);//阻塞当前线程
        System.out.println("Thread_park结束");
    }
}

class Thread_unpark implements  Runnable{
    private Thread thread;

    public Thread_unpark(Thread thread) {
        this.thread = thread;
    }

    @Override
    public void run() {
        System.out.println("Thread_unpark开始");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LockSupport.unpark(thread);//唤醒被阻塞的线程
        System.out.println("Thread_unpark结束");
    }
}
