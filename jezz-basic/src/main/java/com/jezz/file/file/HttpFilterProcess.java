package com.jezz.file.file;

public class HttpFilterProcess implements FilterProcess{
    @Override
    public String process(String msg) {
        msg = msg.replaceAll("^((https|http|ftp|rtsp|mms)?:\\/\\/)[^\\s]+","");
        return msg ;
    }
}
