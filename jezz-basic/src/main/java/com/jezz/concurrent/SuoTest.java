package com.jezz.concurrent;

import java.util.Vector;

public class SuoTest {

    public static void main(String[] args) {
        vectorTest();
    }
    public static void vectorTest(){
        Vector<String> vector = new Vector<String>();
        for(int i = 0 ; i < 10 ; i++){
            vector.add(i + "");
        }

        System.out.println(vector);
    }

}
