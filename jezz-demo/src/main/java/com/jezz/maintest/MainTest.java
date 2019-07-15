package com.jezz.maintest;

import org.junit.Test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainTest {

    @Test
    public void test1(){
        System.out.println(5/2);
        System.out.println(3%4);
        System.out.println(7%4);

        String a = "123\\12\\a";
        System.out.println(a.substring(0,a.lastIndexOf("\\")));
    }
    @Test
    public void test2(){
        String a = "0";
        String[] r = a.split(",");
        System.out.println(r[r.length-1]);

        StringBuilder sb = new StringBuilder();
        System.out.println("====" + sb.toString());
    }
    @Test
    public void test3() throws Exception {
        int a = 1;
        int b = 0;
        try{
           int c = a/b;
        }catch (Exception e){
            throw new Exception("失败",e);
        }

    }

    @Test
    public void test4(){
        long time = 1558428581;
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time * 1000));
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = null;
        try {
            d = df.parse(format);
            System.out.println(d.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis() - d.getTime());


        long time1 = 1527767665;
        String result1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time1 * 1000));
        System.out.println("10位数的时间戳（秒）--->Date:" + result1);
        Date date1 = new Date(time1*1000);


        Date o = new Date(time*1000);
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");
        System.out.println(sd.format(o));
    }

    @Test
    public void test5(){
       int a =  new Random().nextInt(100);
        System.out.println(1%3);
        System.out.println(2%3);
        System.out.println(3%3);
        System.out.println(4%3);
        System.out.println(5%3);
        System.out.println(6%3);
        System.out.println(a);

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        System.out.println(list.size() + "----");

        Collections.sort(list, (o1, o2) -> o2.compareTo(o1));

        for (Integer integer : list) {
            System.out.println(integer);
        }
    }
}
