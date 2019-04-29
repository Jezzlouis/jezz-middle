package com.jezz.leetcode;

import java.util.HashMap;
import java.util.Map;

public class TwoSum {
    public static int[] twoSum(int[] nums, int target) {
        Map<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i],i);
        }
        for (int i = 0; i < nums.length; i++) {
            int a = target - nums[i];
            if(map.containsKey(a) && map.get(a) != i){
               return new int[]{i, map.get(a)};
            }
        }
        return null;
    }

    public static void main(String[] args) {
        int[] a = twoSum(new int[]{2,7,11,15},9);
        for (int i : a) {
            System.out.println(i);
        }
    }

}
