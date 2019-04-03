package com.jezz.classloader;

import org.junit.Test;

public class ClassLoaderTest {

    @Test
    public void test1(){
        // extension classloader
        System.out.println(System.getProperty("java.ext.dirs"));
    }

    @Test
    public void test2(){
        // appclassloader
        System.out.println(ClassLoader.getSystemClassLoader());
        System.out.println(ClassLoader.getSystemClassLoader().getParent());
        System.out.println(ClassLoader.getSystemClassLoader().getParent().getParent());
        System.out.println(System.getProperty("java.class.path"));
    }
}
