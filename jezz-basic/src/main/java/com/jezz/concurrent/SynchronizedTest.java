package com.jezz.concurrent;

public class SynchronizedTest {

    public static synchronized void set(){

    }

    public synchronized void method(){

    }

    public void code(){
        synchronized (this){

        }
    }
}