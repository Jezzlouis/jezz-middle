package com.jezz.design.patterns.responsibility;

import com.jezz.design.patterns.responsibility.impl.ProjectManageHandle;

import java.math.BigDecimal;

public class HandleClient {
    public static void main(String[] args) {
        Handle handle = new ProjectManageHandle();
        handle.dealFeeRequest("jezz",new BigDecimal("1000"));
    }
}
