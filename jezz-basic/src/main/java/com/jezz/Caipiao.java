package com.jezz;

import java.util.ArrayList;
import java.util.List;

public class Caipiao {
    public static void main(String[] args) {
        List<Integer> reList = new ArrayList<Integer>();
        int[] num = new int[35];// 创建一个35长度的int数组
        boolean[] flag = new boolean[35];
        int count = 0;
        for (int i = 0; i < 35; i++)
        {
            num[i] = i + 1;
            flag[i] = true;
        }
// 判断抓取到的数字够不够7个，不够的话，继续抓取
        while (count != 7) {
            int k = (int) (Math.random() * 35);
            if (flag[k]) {
                reList.add(num[k]);
                flag[k] = false;
                count++;
            }
        }
// 抓取结束后输出抓取结果
        Object[] result = reList.toArray();


        System.out.print("[\t");


        for (int i = 0; i < result.length; i++) {


            System.out.print(result[i] + "\t");
        }


        System.out.print("]");
    }
}
