package com.jezz.thread;

import org.junit.Test;

public class MyThread1Test extends Thread{

    public void run(){
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + "在运行!");
        }
    }

    @Test
    public void test1(){
        MyThread1Test myThread1Test = new MyThread1Test();
        myThread1Test.start();
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + "在运行!");
        }
    }
}
