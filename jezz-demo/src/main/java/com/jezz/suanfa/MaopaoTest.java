package com.jezz.suanfa;

public class MaopaoTest {

    public static int[] order(int[] arr){
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length-i-1; j++) {
                int temp = 0;
                if(arr[j+1] > arr[j]){
                    temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
        return arr;
    }

    public static void main(String[] args) {
        int[] arr = {2,7,4,5,3,6,9};
        for (int i : order(arr)) {
            System.out.println(i);
        }
    }
}
