package com.jezz.classloader;

public class ConstClass {
    static {
        System.out.println("ConstClass被初始化了。。。");
    }
    public static final String HELLO = "hello world";
}
