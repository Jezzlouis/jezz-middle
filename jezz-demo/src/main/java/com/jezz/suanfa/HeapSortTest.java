package com.jezz.suanfa;

import java.util.Arrays;

public class HeapSortTest {

    public static void main(String[] args) {
        int[] arr = {2,4,10,5,7,3,9};
        heapSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void heapSort(int[] arr){
        if(arr == null || arr.length == 0){
            return;
        }
        int len = arr.length;
        // 构建大顶堆
        buildMaxHeap(arr,len);

        for (int i = len - 1; i >= 0 ; i--) {
            swap(arr,0,i);
            heapify(arr,i,0);
        }

    }

    public static void buildMaxHeap(int[] arr,int len){
        for (int i = len/2 -1; i >= 0 ; i--) {
            heapify(arr,len,i);
        }
    }

    public static void heapify(int[] arr,int len,int index){
        if(index >= len){
            return;
        }
        int left = 2 * index + 1;
        int right = 2 * index + 2;
        int max = index;
        if(left < len && arr[left] > arr[max]){
            max = left;
        }
        if(right < len && arr[right] > arr[max]){
            max = right;
        }
        if(max != index){
            swap(arr, index, max);
            heapify(arr,len,max);
        }
    }

    public static void swap(int[] arr,int i,int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
