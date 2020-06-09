package com.jezz.suanfa;

public class InsertionSortTest {

    public static void insertionSort(int[] arr,int n){
        if(n <= 1){
            return;
        }
        for (int i = 0; i < n; i++) {
            int value = arr[i];
            int j = i-1;
            for (; j >=0 ; j--) {
                if(arr[j] > value){
                    arr[j+1] = arr[j];
                }else {
                    break;
                }
            }
            arr[j+1] = value;
        }
    }


    public static void main(String[] args) {
        int[] arr = {2,7,4,5,3,6,9};
        insertionSort(arr,4);
        for (int i : arr) {
            System.out.println(i);
        }
    }
}
