package com.jezz.design.patterns.proxy;
public class Car {

    public static void main(String[] args) {
        Car car = new CarProxy1();
        car.move();
    }

    public void move() {
        System.out.println("1.汽车开始跑步");
        System.out.println("2.汽车跑到了终点");
    }

}
class CarProxy1 extends Car {

    @Override
    public void move() {
        System.out.println("日志开始记录....");
        super.move();
        System.out.println("日志记录完成....");
    }

}