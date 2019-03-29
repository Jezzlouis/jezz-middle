package com.jezz.list;

import org.junit.Test;

import java.util.LinkedList;

public class LinkedListTest {

    @Test
    public void test1(){
        LinkedList<Integer> a =  new LinkedList<>();
        a.add(1);
        for (Integer integer : a) {
            System.out.println(integer);
        }
        System.out.println("----");
        a.add(0,3);
        for (Integer di : a) {
            System.out.println(di);
        }
    }

}
