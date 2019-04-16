package com.jezz.maintest;

import org.junit.Test;

public class MainTest {

    @Test
    public void test1(){
        System.out.println(5/2);
        System.out.println(3%4);
        System.out.println(7%4);

        String a = "123\\12\\a";
        System.out.println(a.substring(0,a.lastIndexOf("\\")));
    }
    @Test
    public void test2(){
        String a = "0";
        String[] r = a.split(",");
        System.out.println(r[r.length-1]);
    }
    @Test
    public void test3() throws Exception {
        int a = 1;
        int b = 0;
        try{
           int c = a/b;
        }catch (Exception e){
            throw new Exception("失败",e);
        }

    }
}
