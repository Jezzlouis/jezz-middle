package com.jezz.concurrency;

import org.junit.Test;

import java.util.concurrent.*;

public class ClassLoaderTestTest {
    static CountDownLatch downLatch = new CountDownLatch(2);
    static CyclicBarrier cyclicBarrier = new CyclicBarrier(2);

    @Test
    public void countDownLauchTest() throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(1);
                downLatch.countDown();
                System.out.println(2);
                downLatch.countDown();
            }
        }).start();
        downLatch.await();
        System.out.println(3);
    }

    @Test
    public void CyclicBarrierTest(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(1);
            }
        }).start();
        try {
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println(2);
    }

    private static final int THREAD_COUNT = 30;

    private static ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);

    private static Semaphore s = new Semaphore(10);

    @Test
    public void SemaphoreTest(){
        for (int i = 0; i < THREAD_COUNT; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        s.acquire();
                        System.out.println("save data...");
                        s.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        executorService.shutdown();
    }

    private static final Exchanger<String> exgr = new Exchanger<>();

    private static ExecutorService threadPool = Executors.newFixedThreadPool(2);

    @Test
    public void ExchangerTest(){
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String A = "银行流水 A";
                    exgr.exchange(A);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String B = "银行流水 B";
                    String A = exgr.exchange(B);
                    System.out.println("A和B数据是否一致 : " + A.equals(B));
                    System.out.println("A 录入的是 :" + A);
                    System.out.println("B 录入的是 :" + B);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        executorService.shutdown();
    }
}
