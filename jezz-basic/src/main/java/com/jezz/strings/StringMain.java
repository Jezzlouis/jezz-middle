package com.jezz.strings;

import org.junit.Test;

import java.util.StringTokenizer;

public class StringMain {
//    public static void main(String[] args) {
//        String a = "";
//        StringBuilder sb = new StringBuilder();
//        StringTokenizer st = new StringTokenizer("hello world");
//        while (st.hasMoreTokens()){
//            System.out.println(st.nextToken());
//        }
//        System.out.println(System.getProperty("sun.arch.data.model"));
//
//
//    }
    @Test
    public void test1(){
        String a1 = new String("AA");
        a1.intern();
        String a2 = "AA";
        System.out.println(a1 == a2);
    }
    @Test
    public void test2(){
        String a1 = new String("AA");
        String a2 = new String("AA");
        System.out.println(a1 == a2);
    }
     @Test
    public void test3(){
        String a1 = new String("AA");
        String a2 = a1.intern();
        System.out.println(a1 == a2);
    }
    @Test
    public void test4(){
        String a4 = new String("AA") + new String("BB"); //在堆上创建对象AA、BB和AABB，在常量池上创建常量AA和BB
        a4.intern();
        String a5 = "AA" + "BB";
        System.out.println(a4 == a5);
    }

}
