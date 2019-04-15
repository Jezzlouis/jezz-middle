package com.jezz.dubbo.spi.rpc.service.impl;

import com.jezz.dubbo.spi.rpc.pojo.Car;
import com.jezz.dubbo.spi.rpc.pojo.RaceCar;
import com.jezz.dubbo.spi.rpc.pojo.Wheel;
import com.jezz.dubbo.spi.rpc.service.CarMaker;
import com.jezz.dubbo.spi.rpc.service.WheelMaker;

public class RaceCarMaker implements CarMaker {

    WheelMaker wheelMaker;

//    public setWheelMaker(WheelMaker wheelMaker) {
//        this.wheelMaker = wheelMaker;
//    }

    @Override
    public Car makeCar() {
        // ...
        Wheel wheel = wheelMaker.makeWheel();
        // ...
        return new RaceCar();
    }
}