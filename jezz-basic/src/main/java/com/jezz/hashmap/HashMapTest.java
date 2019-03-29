package com.jezz.hashmap;

import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class HashMapTest {
    @Test
    public void test1(){
        Map<String,Object> map = new HashMap<>();
    }

    @Test
    public void test2(){
        LinkedHashMap linkedHashMap = new LinkedHashMap();
    }

    @Test
    public void test3(){
        TreeMap<Integer, String> treeMap = new TreeMap<Integer, String>();
        treeMap.put(10, "10");
        treeMap.put(85, "85");
        treeMap.put(15, "15");
        treeMap.put(70, "70");
        treeMap.put(20, "20");
        treeMap.put(60, "60");
        treeMap.put(30, "30");
        treeMap.put(50, "50");

        for (Map.Entry<Integer, String> entry : treeMap.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }
}
