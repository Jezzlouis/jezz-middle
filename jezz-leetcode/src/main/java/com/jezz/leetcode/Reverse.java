package com.jezz.leetcode;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class Reverse {
    public static int reverse(int x) {
        String a = String.valueOf(x);
        boolean flag = false;
        List<String> r = new ArrayList<>();
        for (int i = 0; i < a.length(); i++) {
            if("-".equals(String.valueOf(a.charAt(i)))){
                 flag = true;
                 continue;
            }
            r.add(String.valueOf(a.charAt(i)));
        }
        StringBuilder b = new StringBuilder();
        if (flag){
            b.append("-");
        }
        for (int i = r.size()-1; i >= 0; i--) {
            if ("0".equals(r.get(i))) {
                break;
            }
            b.append(r.get(i));
        }
        int result = Integer.valueOf(b.toString());
        if(result > (1L << 32) || result < (-1L << 32)){
            return 0;
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(reverse(-12345689));
    }
}
