package com.jezz.thread;

import org.junit.Test;

public class MyThread3Test extends Thread {

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep((long) (Math.random() * 1000));
                System.out.println("run = " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * CPU执行哪个线程的代码具有不确定性
     */
    @Test
    public void test1() {
        MyThread3Test myThread1Test = new MyThread3Test();
        myThread1Test.start();
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep((long) (Math.random() * 1000));
                System.out.println("run = " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void test2() {
        Thread3 t1 = new Thread3();
        Thread3 t2 = new Thread3();
        Thread3 t3 = new Thread3();
        t2.start();
        t3.start();
        t1.start();
    }

    @Test
    public void test3() {
        MyThread3Test myThread3Test = new MyThread3Test();
        myThread3Test.run();
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep((long) (Math.random() * 1000));
                System.out.println("run = " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void test4() {
        Thread4 thread4 = new Thread4();
        System.out.println("begin == " + thread4.isAlive());
        thread4.start();
        //System.out.println("end == " + thread4.isAlive());
        try {
            Thread.sleep(100);
            System.out.println("end == " + thread4.isAlive());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*
     * 线程默认优先级为5，如果不手动指定，那么线程优先级具有继承性，比如线程A启动线程B，那么线程B的优先级和线程A的优先级相同
     */
    @Test
    public void test5() {
        System.out.println("main thread begin, priority = " +
                Thread.currentThread().getPriority());
        System.out.println("main thread end, priority = " +
                Thread.currentThread().getPriority());
        Thread5_1 thread = new Thread5_1();
        thread.start();
    }

    //看到黑色菱形（线程优先级高的）先打印完,CPU会尽量将执行资源让给优先级比较高的线程
    @Test
    public void test6() {
        for (int i = 0; i < 5; i++) {
            Thread6_0 mt0 = new Thread6_0();
            mt0.setPriority(5);
            mt0.start();
            Thread6_1 mt1 = new Thread6_1();
            mt1.setPriority(4);
            mt1.start();
        }
    }

    @Test
    public void test7() {
        try {
            Thread7 mt = new Thread7();
            mt.setDaemon(true);
            mt.start();
            Thread.sleep(5000);
            System.out.println("我离开thread对象再也不打印了，我停止了！");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //在线程受到阻塞时抛出一个中断信号，这样线程就得以退出阻塞状态
    @Test
    public void test8() {
        try {
            Thread8 mt = new Thread8();
            mt.start();
            Thread.sleep(2000);
            mt.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    //sleep(2000)不释放锁，join(2000)释放锁
    @Test
    public void test9() {
        Thread9 mt = new Thread9();
        mt.start();
        try {
            mt.join();
            System.out.println("我想当mt对象执行完毕之后我再执行，我做到了");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Thread3 extends Thread {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }
}

class Thread4 extends Thread {
    @Override
    public void run() {
        System.out.println(this.getName());
        System.out.println("run = " + this.isAlive());
    }
}

class Thread5_0 extends Thread {
    @Override
    public void run() {
        System.out.println("Thread5_0 run priority = " +
                this.getPriority());
    }
}

class Thread5_1 extends Thread {

    @Override
    public void run() {
        System.out.println("Thread5_1 run priority = " +
                this.getPriority());
        Thread5_0 thread = new Thread5_0();
        thread.start();
    }
}

class Thread6_0 extends Thread {
    @Override
    public void run() {
        long beginTime = System.currentTimeMillis();
        for (int j = 0; j < 100000; j++) {
        }
        long endTime = System.currentTimeMillis();
        System.out.println("◆◆◆◆◆ thread0 use time = " +
                (endTime - beginTime));
    }
}

class Thread6_1 extends Thread {
    @Override
    public void run() {
        long beginTime = System.currentTimeMillis();
        for (int j = 0; j < 100000; j++) {
        }
        long endTime = System.currentTimeMillis();
        System.out.println("◇◇◇◇◇ thread1 use time = " +
                (endTime - beginTime));
    }
}

class Thread7 extends Thread {

    private int i = 0;

    @Override
    public void run() {
        try {
            while (true) {
                i++;
                System.out.println("i = " + i);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Thread8 extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 50000; i++) {
            System.out.println("i = " + (i + 1));
        }
    }
}

class Thread9 extends Thread {
    @Override
    public void run() {
        try {
            long secondValue = (long) (Math.random() * 10000);
            System.out.println(secondValue);
            Thread.sleep(secondValue);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
