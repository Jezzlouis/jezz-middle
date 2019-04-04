package com.jezz.thread;

import org.junit.Test;

public class ThreadDomain13 {
    private int num = 0;

    public void addNum(String userName) {
        try {
            if ("a".equals(userName)) {
                num = 100;
                System.out.println("a set over!");
                Thread.sleep(2000);
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
    public void test1(){
        ThreadDomain13 td = new ThreadDomain13();
        MyThread13_0 mt0 = new MyThread13_0(td);
        MyThread13_1 mt1 = new MyThread13_1(td);
        mt0.start();
        mt1.start();
    }


}

class MyThread13_0 extends Thread {
    private ThreadDomain13 td;

    public MyThread13_0(ThreadDomain13 td) {
        this.td = td;
    }

    @Override
    public void run() {
        td.addNum("a");
    }
}

class MyThread13_1 extends Thread {
    private ThreadDomain13 td;

    public MyThread13_1(ThreadDomain13 td) {
        this.td = td;
    }

    @Override
    public void run() {
        td.addNum("b");
    }
}
