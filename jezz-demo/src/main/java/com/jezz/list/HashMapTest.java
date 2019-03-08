package com.jezz.list;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class HashMapTest {

    @Test
    public void test1(){
        Map<String,Object> map = new HashMap<>();
        map.put(null,null);
        System.out.println(map.get(null));
        map.put(null,1);
        System.out.println(map.get(null));
        map.put(null,2);
        System.out.println(map.get(null));
    }

    @Test
    public void test2(){
        Map<String,Object> map = new HashMap<>();
        map.put("a",null);
        System.out.println(map.get("a"));
        map.put("b",null);
        System.out.println(map.get("b"));
        map.put("c",null);
        System.out.println(map.get("c"));
    }

    @Test
    public void test3(){
        Map<String,Object> map = new HashMap<>();
        map.put("a",1);
        System.out.println(map.get("a"));
        map.put("b",2);
        System.out.println(map.get("b"));
        map.put("c",3);
        System.out.println(map.get("c"));
    }


}
