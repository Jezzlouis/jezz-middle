package com.jezz.file.process;

public class CopyrightProcess implements Process {
    @Override
    public void doProcess(String msg) {
        System.out.println(msg + "版权处理");
    }

}
