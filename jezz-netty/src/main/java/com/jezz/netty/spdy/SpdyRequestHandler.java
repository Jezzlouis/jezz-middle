package com.jezz.netty.spdy;

public class SpdyRequestHandler extends HttpRequestHandler {
    @Override
    protected String getContent() {
        return "This content is transmitted via SPDY\r\n";
    }
}