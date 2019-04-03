package com.jezz.classloader;

public class NativeMethodTest {
    public native void print();

    static {
        System.out.println("hello....");
    }

    public static void main(String[] args) {
        new NativeMethodTest().print();
    }
}
