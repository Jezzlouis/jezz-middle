package com.jezz.leetcode.tanxinsuanfa;

import org.junit.Test;

public class Tanxin {
    @Test
    public void maxProfit(){
        //int[] prices = {7, 1, 5, 3, 6, 4};
        int[] prices = {1, 7, 2, 3, 6, 7, 6, 7};
        int maxprofit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                maxprofit += prices[i] - prices[i - 1];
            }
        }
        System.out.println(maxprofit);
    }

}
