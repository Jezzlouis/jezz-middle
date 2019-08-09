package com.jezz.list;

import org.junit.Test;
import sun.misc.LRUCache;

import java.util.LinkedHashMap;
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
        new LinkedHashMap<>();
    }

    @Test
    public void lruTest(){
        LRU<Character, Integer> lru = new LRU<Character, Integer>(
                16, 0.75f, true);

        String s = "abcdefghijkl";
        for (int i = 0; i < s.length(); i++) {
            lru.put(s.charAt(i), i);
        }
        System.out.println("LRU中key为h的Entry的值为： " + lru.get('h'));
        System.out.println("LRU的大小 ：" + lru.size());
        System.out.println("LRU ：" + lru);
    }

}
