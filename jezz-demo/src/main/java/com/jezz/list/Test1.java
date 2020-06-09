package com.jezz.list;

import java.util.HashMap;
import java.util.Map;

public class Test1 {

    public static int test(int n,int[] arr){

        int mid = n/2;
        final int[] res = {-1};
        Map<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            if(map.containsKey(arr[i])){
                map.put(arr[i],map.get(arr[i]) + 1);
            }else {
                map.put(arr[i], 1);
            }
        }
        map.forEach((k,v) -> {
            if(v > mid){
                res[0] = k;
            }
        });
        return res[0];

    }

    public static void main(String[] args) {
        int[] arr = new int[]{2,2,3,3};
        int n = 4;
        System.out.println(test(5,arr));


    }
}
