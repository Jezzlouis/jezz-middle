package com.jezz.suanfa;

public class BsearchTest {

    public static int singleBsearch(int[] arr,int n,int value){
        int low = 0;
        int high = n-1;
        while (low <= high){
            int mid = low + (high-low)>>1;
            if(arr[mid] == value){
                return mid;
            }else if(arr[mid] < value){
                low = mid + 1;
            }else {
                high = mid - 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {

    }
}
