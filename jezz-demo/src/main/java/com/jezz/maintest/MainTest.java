package com.jezz.maintest;

import org.junit.Test;

public class MainTest {

    @Test
    public void test1(){
        System.out.println(5/2);
        System.out.println(3%4);
        System.out.println(7%4);
    }
    @Test
    public void test2(){
        String a = "0";
        String[] r = a.split(",");
        System.out.println(r[r.length-1]);
    }
}
