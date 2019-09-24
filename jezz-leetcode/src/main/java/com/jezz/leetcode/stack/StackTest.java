package com.jezz.leetcode.stack;

import org.junit.Test;

import java.util.Stack;

public class StackTest {

    public String removeDuplicates(String S) {
        Stack<Character> stack = new Stack();
        for(int i = 0;i < S.length();i++){
            if(!stack.isEmpty()){
                if(stack.peek().equals(S.charAt(i))){
                    stack.pop();
                    continue;
                }
            }
            stack.push(S.charAt(i));
        }
        StringBuilder sb = new StringBuilder();
        stack.forEach(character -> sb.append(character));
        return sb.toString();
    }

    public String removeOuterParentheses(String S) {
        Stack<Character> stack = new Stack<>();
        StringBuilder sb = new StringBuilder();
        int start = 0;
        for (int i = 0; i < S.length(); i++) {
            if('(' == S.charAt(i)){
                stack.push('(');
            }else {
                stack.pop();
                if(stack.isEmpty()){
                    sb.append(S, start + 1, i);
                    start = i + 1;
                }
            }
        }
        return sb.toString();
    }

    private String getString(String S){
        StringBuilder sb = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < S.length(); i++) {
            if(S.charAt(i) == '#'){
                if(!stack.isEmpty()) {
                    stack.pop();
                    continue;
                }
            }else {
                stack.push(S.charAt(i));
            }
        }
        stack.forEach(character -> sb.append(character));
        return sb.toString();
    }

    public boolean backspaceCompare(String S, String T) {
        String s1 = getString(S);
        String t1 = getString(T);
        boolean flag = false;
        System.out.println(s1  +  "------"  +t1);
        if(s1.equals(t1)){
            flag = true;
        }
        return flag;
    }

    @Test
    public void test1047(){
       String sb = removeDuplicates("abbaca");
        System.out.println(sb);
    }

    @Test
    public void test1021(){
        System.out.println(removeOuterParentheses("(()())(())"));
        System.out.println(removeOuterParentheses("(()())(())(()(()))"));
        System.out.println(removeOuterParentheses("()()"));
    }

    @Test
    public void test844(){
        System.out.println(backspaceCompare("ab#c","ad#c"));
        System.out.println(backspaceCompare("ab##","c#d#"));
        System.out.println(backspaceCompare("a##c","#a#c"));
        System.out.println(backspaceCompare("a#c","b"));
        System.out.println(backspaceCompare("y#fo##f","y#f#o##f"));
    }

    @Test
    public void test682(){

    }
}
