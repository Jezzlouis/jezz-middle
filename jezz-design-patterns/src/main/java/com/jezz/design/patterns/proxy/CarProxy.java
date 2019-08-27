package com.jezz.design.patterns.proxy;

public class CarProxy implements Moveable{
    private Moveable move;

    @Override
    public void move() {
        if(move==null){
            move = new CarImpl();
        }
        System.out.println("开始记录日志：");
        move.move();
        System.out.println("记录日志结束！");

    }
}
