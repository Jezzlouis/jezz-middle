package com.jezz.suanfa;

public class DianyingyuanTest {

    public static int f(int n){
        int res = 1;
        for (int i = 2; i <= n; ++i) {
            res = res + 1;
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(f(1));
        System.out.println(f(2));
        System.out.println(f(3));
        System.out.println(f(10));
    }
}
