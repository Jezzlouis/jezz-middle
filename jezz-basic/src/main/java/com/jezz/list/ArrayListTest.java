package com.jezz.list;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayListTest {
    @Test
    public void test1(){
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
         list.add(i);
        }
    }
    @Test
    public void test2(){
        int[] list = {1,2,4};
        int[] a = Arrays.copyOf(list,2);
        //System.out.println(a[1]);
        int[] b = {3,6,9,8,5,6};
        System.arraycopy(list,1,b,4,2);
        for (int i : b){
            System.out.println(i);
        }
    }

    @Test
    public void test3(){
        ArrayList<Object> list = new ArrayList<Object>();
        final int N = 10000000;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {
            list.add(i);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("使用ensureCapacity方法前："+(endTime - startTime));

        list = new ArrayList<Object>();
        long startTime1 = System.currentTimeMillis();
        list.ensureCapacity(N);
        for (int i = 0; i < N; i++) {
            list.add(i);
        }
        long endTime1 = System.currentTimeMillis();
        System.out.println("使用ensureCapacity方法后："+(endTime1 - startTime1));
    }
}
