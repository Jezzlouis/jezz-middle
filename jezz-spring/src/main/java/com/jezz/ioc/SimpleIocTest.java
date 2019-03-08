package com.jezz.ioc;

import org.junit.Test;

public class SimpleIocTest {
    @Test
    public void getBean() throws Exception {
        String location = SimpleIoc.class.getClassLoader().getResource("ioc.xml").getFile();
        SimpleIoc bf = new SimpleIoc(location);
        Wheel wheel = (Wheel) bf.getBean("wheel");
        System.out.println(wheel);
        Car car = (Car) bf.getBean("car");
        System.out.println(car.getName());
    }
}
