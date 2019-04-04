package com.jezz.thread;

import org.junit.Test;

public class ThreadDomain14 {
    private int num = 0;

    public synchronized void addNum_1(String userName) {
        try {
            if ("a".equals(userName)) {
                num = 100;
                System.out.println("a set over!");
                Thread.sleep(5000);
            } else {
                num = 200;
                System.out.println("b set over!");
            }
            System.out.println(userName + " num = " + num);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {
        ThreadDomain14 td0 = new ThreadDomain14();
        ThreadDomain14 td1 = new ThreadDomain14();
        MyThread14_2 mt0 = new MyThread14_2(td0);
        MyThread14_3 mt1 = new MyThread14_3(td1);
        mt0.start();
        mt1.start();
    }
}

class MyThread14_2 extends Thread {
    private ThreadDomain14 td;

    public MyThread14_2(ThreadDomain14 td) {
        this.td = td;
    }

    @Override
    public void run() {
        td.addNum_1("a");
    }
}

class MyThread14_3 extends Thread {
    private ThreadDomain14 td;

    public MyThread14_3(ThreadDomain14 td) {
        this.td = td;
    }

    @Override
    public void run() {
        td.addNum_1("b");
    }
}
