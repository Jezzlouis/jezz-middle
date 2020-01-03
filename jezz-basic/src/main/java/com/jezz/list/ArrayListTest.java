package com.jezz.list;

import com.sun.security.auth.UserPrincipal;
import org.junit.Test;

import java.util.*;

public class ArrayListTest {
    @Test
    public void test1(){
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
         list.add(i);
        }
        list.add(11,7);
    }
    @Test
    public void test2(){
        int[] list = {1,2,4};
        int[] a = Arrays.copyOf(list,2);
        //System.out.println(a[1]);
        int[] b = {3,6,9,8,5,6};
        System.arraycopy(list,1,b,4,2);
        for (int i : b){
            System.out.println(i);
        }
    }

    @Test
    public void test3(){
        ArrayList<Object> list = new ArrayList<Object>();
        final int N = 10000000;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {
            list.add(i);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("使用ensureCapacity方法前："+(endTime - startTime));

        list = new ArrayList<Object>();
        long startTime1 = System.currentTimeMillis();
        list.ensureCapacity(N);
        for (int i = 0; i < N; i++) {
            list.add(i);
        }
        long endTime1 = System.currentTimeMillis();
        System.out.println("使用ensureCapacity方法后："+(endTime1 - startTime1));
    }

    @Test
    public void test4(){
        System.out.println(System.currentTimeMillis());

    }

    @Test
    public void testBig(){
        List<String> smallList = new ArrayList<>();
        List<String> bigList = new ArrayList<>();

        for (int i = 0; i < 400000; i++) {
            if (i < 80000) {
                smallList.add(String.valueOf(i));
                bigList.add(String.valueOf(i));
            } else {
                bigList.add(String.valueOf(i));
            }
        }
        System.out.println("a1：" + smallList.size());
        System.out.println("a2：" + bigList.size());

        long start = System.currentTimeMillis();
        // arr2.removeAll(arr1);
        smallList.removeAll(bigList);
        // Set set = removeAll(bigList, samllList);
        long end = System.currentTimeMillis();

        // System.out.println("set：" + set.size());

        System.out.println(bigList.size());
        System.out.println("spend time：" + (end - start));
    }

    private static Set removeAll(List bigList, List smallList) {
        Set set = new HashSet(bigList);
        set.removeAll(smallList);
        return set;
    }

    public static List removeAll2(List src,List oth){
        LinkedList result = new LinkedList(src);//大集合用linkedlist
        HashSet othHash = new HashSet(oth);//小集合用hashset
        Iterator iter = result.iterator();//采用Iterator迭代器进行数据的操作
        while(iter.hasNext()){
            if(othHash.contains(iter.next())){
                iter.remove();
            }
        }
        return result;
    }

    @Test
    public void testBig3(){
        List<String> smallList = new ArrayList<>();
        List<String> bigList = new ArrayList<>();

        for (int i = 0; i < 400000; i++) {
            if (i < 80000) {
                smallList.add(String.valueOf(i));
                bigList.add(String.valueOf(i));
            } else {
                bigList.add(String.valueOf(i));
            }
        }
        System.out.println("a1：" + smallList.size());
        System.out.println("a2：" + bigList.size());

        long start = System.currentTimeMillis();
        // arr2.removeAll(arr1);
        removeAll(bigList,smallList);
        // Set set = removeAll(bigList, samllList);
        long end = System.currentTimeMillis();

        // System.out.println("set：" + set.size());

        System.out.println(bigList.size());
        System.out.println("spend time：" + (end - start));
    }

    @Test
    public void testBig4(){
        List<String> smallList = new ArrayList<>();
        List<String> bigList = new ArrayList<>();

        for (int i = 0; i < 400000; i++) {
            if (i < 80000) {
                smallList.add(String.valueOf(i));
                bigList.add(String.valueOf(i));
            } else {
                bigList.add(String.valueOf(i));
            }
        }
        System.out.println("a1：" + smallList.size());
        System.out.println("a2：" + bigList.size());

        long start = System.currentTimeMillis();
        // arr2.removeAll(arr1);
        removeAll2(bigList,smallList);
        // Set set = removeAll(bigList, samllList);
        long end = System.currentTimeMillis();

        // System.out.println("set：" + set.size());

        System.out.println(bigList.size());
        System.out.println("spend time：" + (end - start));
    }

    @Test
    public void test5(){
        String[] str = new String[]{"flower","flow","flight"};
        System.out.println(longestCommonPrefix(str));
    }
    public String longestCommonPrefix(String[] strs) {
        if(strs.length < 0) return "";
        String prefix = strs[0];
        for(int i = 1;i < strs.length ;i++){
            while(strs[i].indexOf(prefix) != 0){
                prefix = prefix.substring(0,prefix.length()-1);
                if(prefix.isEmpty()) return "";
            }
        }
        return prefix;
    }
}
