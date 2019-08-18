package com.jezz.leetcode.eightorder;

import org.junit.Test;

import java.util.Arrays;

public class SortTest {

    /**
     *
     * 直接插入排序
     */
    @Test
    public void insertionSort(){
        int[] arr = {2,4,1,3};
        // 默认第一个是已经排序的 从第一个开始
        for (int i = 1; i < arr.length; i++) {
            int temp = arr[i];
            System.out.println(temp);
            for (int j = i; j >= 0; j--) {
                if(j > 0 && arr[j-1] > temp){
                    arr[j] = arr[j-1];
                    System.out.println("Temping:  " + Arrays.toString(arr));
                }else {
                    arr[j] = temp;
                    System.out.println("---"+arr[j]);
                    System.out.println("Sorting:  " + Arrays.toString(arr));
                    break;
                }
            }
        }
    }

    @Test
    public void shellSort(){
        int[] arr = {2,4,1,3,5};
        int gap = arr.length / 2;
        for (; gap > 0; gap /= 2) {      //不断缩小gap，直到1为止
            for (int j = 0; (j+gap) < arr.length; j++){     //使用当前gap进行组内插入排序
                for(int k = 0; (k+gap)< arr.length; k += gap){
                    if(arr[k] > arr[k+gap]) {
                        int temp = arr[k+gap];      //交换操作
                        arr[k+gap] = arr[k];
                        arr[k] = temp;
                        System.out.println("    Sorting:  " + Arrays.toString(arr));
                    }
                }
            }
        }
    }

    @Test
    public void shell_sort(){
        int[] arr = {2,4,1,3};
        int gap = 1, i, j, len = arr.length;
        int temp;
        while (gap < len / 3)
            gap = gap * 3 + 1;      // <O(n^(3/2)) by Knuth,1973>: 1, 4, 13, 40, 121, ...
        for (; gap > 0; gap /= 3) {
            for (i = gap; i < len; i++) {
                temp = arr[i];
                for (j = i - gap; j >= 0 && arr[j] > temp; j -= gap) {
                    arr[j + gap] = arr[j];
                }
                arr[j + gap] = temp;
                System.out.println("    Sorting:  " + Arrays.toString(arr));
            }
        }
    }


    @Test
    public void selectionSort(){
        int[] arr = {2,4,1,3};
        for(int i = 0; i < arr.length-1; i++){
            int min = i;
            for(int j = i+1; j < arr.length; j++){    //选出之后待排序中值最小的位置
                if(arr[j] < arr[min]){
                    min = j;
                }
            }
            if(min != i){
                int temp = arr[min];      //交换操作
                arr[min] = arr[i];
                arr[i] = temp;
                System.out.println("Sorting:  " + Arrays.toString(arr));
            }
        }
    }

    @Test
    public void bubbleSort(){
        int[] arr = {2,4,1,3};
        for (int i = 0; i < arr.length - 1; i++) {
            for(int j = 0; j < arr.length-i-1; j++){
                if(arr[j] < arr[j+1]){
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                    System.out.println("Sorting: " + Arrays.toString(arr));
                }
            }
        }
    }

    @Test
    public void quickSorttest(){

    }

    public static void quickSort(int[] arr, int low, int high){
        if(arr.length <= 0) return;
        if(low >= high) return;
        int left = low;
        int right = high;

        int temp = arr[left];   //挖坑1：保存基准的值
        while (left < right){
            while(left < right && arr[right] >= temp){  //坑2：从后向前找到比基准小的元素，插入到基准位置坑1中
                right--;
            }
            arr[left] = arr[right];
            while(left < right && arr[left] <= temp){   //坑3：从前往后找到比基准大的元素，放到刚才挖的坑2中
                left++;
            }
            arr[right] = arr[left];
        }
        arr[left] = temp;   //基准值填补到坑3中，准备分治递归快排
        System.out.println("Sorting: " + Arrays.toString(arr));
        quickSort(arr, low, left-1);
        quickSort(arr, left+1, high);
    }



}
