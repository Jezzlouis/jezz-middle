package com.jezz.design.patterns.responsibility.impl;



import com.jezz.design.patterns.responsibility.Handle;

import java.math.BigDecimal;

public class BossManageHandle implements Handle {
    public BossManageHandle() {
    }

    @Override
    public void dealFeeRequest(String user, BigDecimal fee) {
        if (user.equalsIgnoreCase("jezz") && fee.compareTo(new BigDecimal("100")) == 1){
            System.out.println("老板:....同意...");
        }
    }
}
