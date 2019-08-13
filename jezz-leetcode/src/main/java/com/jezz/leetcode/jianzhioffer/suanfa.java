package com.jezz.leetcode.jianzhioffer;

import java.util.ArrayList;
import java.util.Stack;

public class suanfa {

    public boolean find(int[][] array,int target) {
        if (array == null) {
            return false;
        }
        int row = 0;
        int column = array[0].length-1;
        while (row < array.length && column >= 0) {
            if(array[row][column] == target) {
                return true;
            }
            if(array[row][column] > target) {
                column--;
            } else {
                row++;
            }
        }
        return false;
    }

    public String replaceSpace(StringBuffer str) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (String.valueOf(str.charAt(i)).equals(" ")) {
                sb.append("%20");
            }else {
                sb.append(str.charAt(i));
            }
        }
        return String.valueOf(sb);
    }


    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        ArrayList<Integer> list = new ArrayList<>();
        if (listNode == null) {
            return list;
        }
        Stack<ListNode> stack = new Stack<>();
        while (listNode != null) {
            stack.push(listNode);
            listNode = listNode.next;
        }
        while (!stack.isEmpty()) {
            list.add(stack.pop().val);
        }
        return list;
    }

}
class ListNode {
    int val;
    ListNode next;   // 下一个链表对象
    ListNode(int x) { val = x; }  //赋值链表的值
}


