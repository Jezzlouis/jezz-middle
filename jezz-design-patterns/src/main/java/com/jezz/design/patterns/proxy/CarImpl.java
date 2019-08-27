package com.jezz.design.patterns.proxy;

public class CarImpl implements Moveable {

    @Override
    public void move() {
        System.out.println("汽车行驶中....");
    }

    public static void main(String[] args)  {
        Moveable m =new CarProxy();
        m.move();
    }
}

