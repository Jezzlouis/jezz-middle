package com.jezz.classloader;

import org.junit.Test;

public class ClassLoadingTest {

//    什么时候需要开始类加载的第一个阶段：加载？
//
//    虚拟机规范严格规定了有且只有五种情况必须立即对类进行“初始化”：
//
//    使用new关键字实例化对象的时候、读取或设置一个类的静态字段的时候，已经调用一个类的静态方法的时候。
//    使用java.lang.reflect包的方法对类进行反射调用的时候，如果类没有初始化，则需要先触发其初始化。
//    当初始化一个类的时候，如果发现其父类没有被初始化就会先初始化它的父类。
//    当虚拟机启动的时候，用户需要指定一个要执行的主类（就是包含main()方法的那个类），虚拟机会先初始化这个类；
//    使用Jdk1.7动态语言支持的时候的一些情况。
//    而对于接口，当一个接口在初始化时，并不要求其父接口全部都完成了初始化，只有在真正使用到父接口时（如引用父接口中定义的常量）才会初始化。
//
//    所有引用类的方式都不会触发初始化称为被动引用，下面是3个被动引用例子

    @Test
    public void test1(){
       // 1:通过子类调用父类的静态字段不会导致子类初始化
       System.out.println(SubClass.value);
    }

    @Test
    public void test2(){
        // 2:通过数组定义引用类，不会触发此类的初始化
        SuperClass[] superClasses = new SuperClass[3];
    }

    @Test
    public void test3(){
        System.out.println(SubClass.value);
        // 通过new 创建对象,可以实现类初始化
        SuperClass superClass = new SuperClass();
    }

    @Test
    public void test4(){
        // 3.常量在编译阶段会存入调用类的常量池中，本质上并没有直接引用定义常量的类，因此不会触发定义常量的类的初始化
        System.out.println(ConstClass.HELLO);
    }

    // 空方法 虚拟机参数-XX:TraceClassLoading
    @Test
    public void test5(){

    }

}


