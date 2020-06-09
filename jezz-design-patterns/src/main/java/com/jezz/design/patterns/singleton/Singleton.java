package com.jezz.design.patterns.singleton;

public class Singleton {
    private static Singleton singleton;
    private Singleton(){}
    public static Singleton getInstance(){
        singleton = new Singleton();
        return singleton;
    }
    public void showMessage(){
        System.out.println("Hello World!");
    }

    public static void main(String[] args) {
        //获取唯一可用的对象
        Singleton object = Singleton.getInstance();
        //显示消息
        object.showMessage();
    }
}

