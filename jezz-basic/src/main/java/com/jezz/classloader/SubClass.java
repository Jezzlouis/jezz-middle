package com.jezz.classloader;

public class SubClass extends SuperClass
{
    static
    {
        System.out.println("Subclass（子类）被初始化了。。。");
    }

    static int a;

    public SubClass()
    {
        System.out.println("init SubClass");
    }
}