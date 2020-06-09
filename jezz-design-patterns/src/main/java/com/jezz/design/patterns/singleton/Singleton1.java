package com.jezz.design.patterns.singleton;

public class Singleton1 {

    private static class SingletonHolder {
        private static Singleton1 INSTANCE = new Singleton1();
    }
    private Singleton1 (){}

    public static Singleton1 getInstance() {
        return SingletonHolder.INSTANCE;
    }

}
