package com.jezz.javavm;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class HeapOverflowTest {

    /**
     * 测试内容：堆溢出
     *  *
     *  * 虚拟机参数：-Xms20M -Xmx20M -XX:+HeapDumpOnOutOfMemoryError
     */
    @Test
    public void test1(){
        List<HeapOverflowTest> list = new ArrayList<>();
            while (true) {
                 list.add(new HeapOverflowTest());
            }
    }

}
