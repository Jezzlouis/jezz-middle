package com.jezz.suanfa;


import java.util.ArrayList;
import java.util.List;

// 红包算法
public class RedPacket {

    private static final int MIN_MONEY = 1;

    private static final int MAX_MONEY = 200 * 100;

    private static final int LESS = -1;

    private static final int OK = 0;

    private static final int MORE = 1;

    private static final double TIMES = 2.1d;

    private int remainCount = 0;

    public List<Integer> splitRedPacket(Integer money,int count){

        List<Integer> moneys = new ArrayList<>();

        if(MAX_MONEY * count < money){
            return moneys;
        }

        int max = (int) ((money/count) * TIMES);
        max = max > MAX_MONEY ? MAX_MONEY : max;

        for (int i = 0; i < count ; i++) {
            int redPacket = randomRedPacket(money,MIN_MONEY,max,count - i);
            moneys.add(redPacket);
            money -= redPacket;
        }

        return moneys;
    }

    public int randomRedPacket(int totalMoney,int minMoney,int maxMoney,int count){
        if(count == 1){
            return totalMoney;
        }
        if(minMoney == maxMoney){
            return minMoney;
        }
        maxMoney = maxMoney > totalMoney ? totalMoney : maxMoney;

        int redPacket = (int) (Math.random() * (maxMoney - minMoney) + minMoney);

        int lastMoney = totalMoney - redPacket;

        int status = checkMoney(lastMoney,count);

        if(OK == status){
            return redPacket;
        }

        if(LESS == status){
            remainCount++ ;
            return randomRedPacket(totalMoney,minMoney,redPacket,count);
        }

        if(MORE == status){
            remainCount++;
            return randomRedPacket(totalMoney,redPacket,maxMoney,count);
        }

        return redPacket;
    }

    public int checkMoney(int lastMoney,int count){
        int avgMoney = lastMoney / count;
        if(avgMoney < MIN_MONEY){
            return LESS;
        }
        if(avgMoney > MAX_MONEY){
            return MORE;
        }
        return OK;
    }

    public static void main(String[] args) {
        RedPacket redPacket = new RedPacket();
        List<Integer> redPackets = redPacket.splitRedPacket(100*100, 2);
        System.out.println(redPackets);

        int sum = 0;
        for (Integer red : redPackets) {
            sum += red;
        }
        System.out.println(sum);
    }
}
