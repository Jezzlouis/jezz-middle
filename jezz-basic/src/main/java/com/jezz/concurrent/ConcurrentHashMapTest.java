package com.jezz.concurrent;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentHashMapTest {
    public static void main(String[] args) {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        concurrentHashMap.put("1","2");
        System.out.println(0x7fffffff);
        System.out.println(Integer.numberOfLeadingZeros(1));
        ConcurrentSkipListMap<String,Object> skipListMap = new ConcurrentSkipListMap<>();
    }
}
