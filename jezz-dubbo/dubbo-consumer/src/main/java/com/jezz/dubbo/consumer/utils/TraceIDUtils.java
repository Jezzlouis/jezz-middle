package com.jezz.dubbo.consumer.utils;

import java.util.UUID;

public class TraceIDUtils {
    private static final ThreadLocal<String> TRACE_ID = new ThreadLocal<>();

    public static String getTraceId() {
        if(TRACE_ID.get() == null) {
            String s = UUID.randomUUID().toString();
            setTraceId(s);
        }
        return TRACE_ID.get();
    }

    public static void setTraceId(String traceId) {
        TRACE_ID.set(traceId);
    }
}
