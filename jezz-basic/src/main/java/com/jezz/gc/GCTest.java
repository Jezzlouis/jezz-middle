package com.jezz.gc;

import org.junit.Test;

public class GCTest {

    private Object instance = null;

    private static final int _1MB = 1024 * 1024;

    private byte[] bigSize = new byte[2 * _1MB];

    private void doGC(){
        GCTest objectA = new GCTest();
        GCTest objectB = new GCTest();
        objectA.instance = objectB;
        objectB.instance = objectA;
        objectA = null;
        objectB = null;
        System.gc();
    }

    /**
     * -verbose:gc
     */
    @Test
    public void test1(){
        doGC();
    }


    // -XX:+PrintGCDetails -XX:+UseSerialGC
    @Test
    public void serial_serialOld(){
        doGC();
    }

    // -XX:+PrintGCDetails -XX:+UseParNewGC
    @Test
    public void parNew_serialOld(){
        doGC();
    }

    // -XX:+PrintGCDetails -XX:+UseParallelGC
    @Test
    public void parallel_serialOld(){
        doGC();
    }

    // -XX:+PrintGCDetails -XX:+UseConcMarkSweepGC
    @Test
    public void parNew_Cms_SerialOld(){
        doGC();
    }

    @Test
    public void parallel_parallelOld(){
        doGC();
    }
}
