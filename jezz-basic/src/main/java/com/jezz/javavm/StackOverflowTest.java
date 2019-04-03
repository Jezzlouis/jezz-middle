package com.jezz.javavm;

import org.junit.Test;

public class StackOverflowTest {

    private int stackLength = 1;

    public void stackLeak()
    {
        stackLength++;
        stackLeak();
    }

    @Test
    public void test1(){
        StackOverflowTest stackOverflow = new StackOverflowTest();
        try {
            stackOverflow.stackLeak();
        } catch (Throwable e) {
            System.out.println("stack length:" + stackOverflow.stackLength);
            throw e;
        }
    }

}
