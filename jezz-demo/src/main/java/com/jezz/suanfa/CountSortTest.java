package com.jezz.suanfa;

import java.util.Arrays;

public class CountSortTest {
    public static void main(String[] args) {
        int[] arr = {2,3,2,3,7,1,1,0,0,5,6,9,8,5,7,4,0,9};

        int[] result = countSort(arr);
        System.out.println(Arrays.toString(result));

        int[] array = new int[] {95,94,91,98,99,90,99,93,91,92};
        int[] sortedArray = countSort(array);
        System.out.println(Arrays.toString(sortedArray));
    }

    public static int[] countSort(int[] arr){
        // 1.得到数列的最大值和最小值，并算出差值d
        int max = arr[0];
        int min = arr[0];
        for (int i = 0; i < arr.length; i++) {
            if(max < arr[i]){
                max = arr[i];
            }
            if(min > arr[i]){
                min = arr[i];
            }
        }
        int d = max - min;

        // 2.创建统计数组并统计对应元素个数
        int[] count = new int[d+1];
        for (int i = 0; i < arr.length; i++) {
            count[arr[i]-min]++;
        }

        // 3.统计数组做变形，后面的元素等于前面的元素之和
        int sum = 0;
        for (int i = 0; i < count.length; i++) {
            sum += count[i];
            count[i] = sum;
        }

        // 4.倒序遍历原始数列，从统计数组找到正确位置，输出到结果数组
        int[] result = new int[arr.length];
        for (int i = arr.length-1; i >=0 ; i--) {
            result[count[arr[i]-min]-1] = arr[i];
            count[arr[i]-min]--;
        }

        return result;
    }
}
