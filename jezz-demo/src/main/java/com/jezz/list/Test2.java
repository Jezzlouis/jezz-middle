package com.jezz.list;

import java.util.*;

public class Test2 {
    public static Map<String,Integer> test2(String s){
        Map<String,Integer> map = new HashMap<>();
        Character[] arr = new Character[]{'0','1','2','3','4','5','6','7','8','9','0'};
        Set<Character> set = new HashSet<>();
        set.addAll(Arrays.asList(arr));
        int start = 0;int end;int i=0;int len = s.length()-1;int ls;
        int resLen = 0,resStart=0,resEnd=0;
        while (i<len){
            // 判断当前是字母，下一个是数字，就继续往下走，记录下开始索引
            if(!set.contains(s.charAt(i)) && set.contains(s.charAt(i+1))){
                start = i + 1;
            }else if(set.contains(s.charAt(i)) && set.contains(s.charAt(i+1))){
                ++i;
                if(i == len){
                    end = i;
                    ls = end - start + 1;
                    if(ls < resLen){
                        ++i;
                        continue;
                    }
                    else {
                        resLen = ls;
                        resStart = start;
                        resEnd = end;
                    }
                }else {
                    continue;
                }
            }else if(set.contains(s.charAt(i)) && !set.contains(s.charAt(i+1))){
                // 判断当前是数字，下一个是字母，结束，记录下end索引
                end = i;
                ls = end - start + 1;
                if(ls < resLen){
                    ++i;
                    continue;
                }
                else {
                    resLen = ls;
                    resStart = start;
                    resEnd = end;
                }
            }
            ++i;
        }
        map.put(s.substring(resStart,resEnd),resLen);
        return map;

    }

    public static void main(String[] args) {
        String a = "abcd12345ed125ss123058789";
        System.out.println(test2(a).keySet());
    }
}
