package com.jezz.file.process;

public class TypoProcess implements Process {
    @Override
    public void doProcess(String msg) {
        System.out.println(msg + "复印处理");
    }

}
