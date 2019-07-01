package com.jezz.concurrent;

import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SuoTest {

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        lock.lock();
        vectorTest();
    }
    public static void vectorTest(){
        Vector<String> vector = new Vector<String>();
        for(int i = 0 ; i < 10 ; i++){
            vector.add(i + "");
        }

        System.out.println(vector);
    }

}
