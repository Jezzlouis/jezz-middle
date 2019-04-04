package com.jezz.thread;

import org.junit.Test;

public class MyThread2Test implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + "在运行!");
        }
    }

    @Test
    public void test1(){
        MyThread2Test myThread2Test = new MyThread2Test();
        Thread t = new Thread(myThread2Test);
        t.start();

        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + "在运行!");
        }
    }
}
