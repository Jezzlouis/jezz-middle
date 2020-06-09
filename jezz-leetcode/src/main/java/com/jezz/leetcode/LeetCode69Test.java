package com.jezz.leetcode;

import java.math.BigDecimal;
import java.util.concurrent.Semaphore;

public class LeetCode69Test {
    public static double sqrteRoot(int v){
        double low = 0;
        double high = v;
        double x = 0;
        while (low <= high){
            x = (high+low)/2;
            if(v == x*x){
                return x + 0.000001;
            }else if(v < x*x){
                high = x - 0.000001;
            }else {
                low = x + 0.000001;
            }
        }
        return new BigDecimal(x).setScale(6,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static void main(String[] args) {
        System.out.println(sqrteRoot(5));
    }
    Semaphore
}
