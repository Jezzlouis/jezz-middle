package com.jezz.javavm;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ConstantPoolOverflowTest {

    /**
     * 虚拟机参数-XX:PermSize=10M -XX:MaxPermSize=10M
     */
    @Test
    public void test1(){
        List<String> list = new ArrayList<>();
        int i = 0;
        while (true)
        {
            list.add(String.valueOf(i++).intern());
        }
    }
    /**
     *
     Java HotSpot(TM) 64-Bit Server VM warning: ignoring option PermSize=10M; support was removed in 8.0
     Java HotSpot(TM) 64-Bit Server VM warning: ignoring option MaxPermSize=10M; support was removed in 8.0
     */
}
