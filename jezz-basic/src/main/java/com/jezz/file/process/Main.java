package com.jezz.file.process;

public class Main {
    public static void main(String[] args) {
        String msg = "内容内容内容==" ;
        MsgProcessChain chain = new MsgProcessChain()
                .addChain(new SensitiveWordProcess())
                .addChain(new TypoProcess())
                .addChain(new CopyrightProcess()) ;
        chain.process(msg) ;
    }
}