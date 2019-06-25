package com.jezz.strings;

import java.util.StringTokenizer;

public class StringMain {
    public static void main(String[] args) {
        String a = "";
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer("hello world");
        while (st.hasMoreTokens()){
            System.out.println(st.nextToken());
        }
        System.out.println(System.getProperty("sun.arch.data.model"));
    }
}
