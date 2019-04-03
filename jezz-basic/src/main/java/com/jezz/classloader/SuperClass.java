package com.jezz.classloader;

public class SuperClass extends SSClass
{
    static
    {
        System.out.println("SuperClass（父类）被初始化了。。。");
    }

    public static int value = 123;

    public SuperClass()
    {
        System.out.println("init SuperClass 构造器...");
    }
}