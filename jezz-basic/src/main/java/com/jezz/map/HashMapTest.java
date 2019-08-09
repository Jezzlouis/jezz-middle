package com.jezz.map;

import org.junit.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.*;

public class HashMapTest {
    @Test
    public void test1(){
        Map<String,Object> map = new HashMap<>();
        map.put("1",1);
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

    @Test
    public void test4(){
        final HashMap<String, String> map = new HashMap<String, String>(2);
        for (int i = 0; i < 10000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    map.put(UUID.randomUUID().toString(), "");
                }
            }).start();
        }
    }

    @Test
    public void test5(){
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            System.out.println((Unsafe)f.get(null));
        } catch (Exception e) {
            /* ... */
        }
    }
}
