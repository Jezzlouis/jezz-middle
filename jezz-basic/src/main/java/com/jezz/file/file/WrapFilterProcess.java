package com.jezz.file.file;

public class WrapFilterProcess implements FilterProcess{
    @Override
    public String process(String msg) {
        msg = msg.replaceAll("\\s*", "");
        return msg ;
    }
}
