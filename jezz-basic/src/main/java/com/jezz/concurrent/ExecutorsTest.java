package com.jezz.concurrent;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ExecutorsTest {
    public static void main(String[] args) {
        ExecutorService executor1 = Executors.newFixedThreadPool(1);
        ExecutorService executor2 = Executors.newCachedThreadPool();
        ExecutorService executor3 = Executors.newScheduledThreadPool(1);
        ExecutorService executor4 = Executors.newWorkStealingPool(1);
        ExecutorService executor5 = Executors.newSingleThreadExecutor();
        executor1.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println(Thread.currentThread().getName() +"====="+ i);
                }
            }
        });
        executor1.shutdown();
        executor2.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println(Thread.currentThread().getName() +"====="+ i);
                }
            }
        });
        executor2.shutdown();
        executor3.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println(Thread.currentThread().getName() +"=====" + i);
                }
            }
        });
        executor3.shutdown();
        executor4.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println(Thread.currentThread().getName() +"=====" + i);
                }
            }
        });
        executor4.shutdown();
        executor5.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println(Thread.currentThread().getName() +"=====" + i);
                }
            }
        });
        executor5.shutdown();
    }
}
