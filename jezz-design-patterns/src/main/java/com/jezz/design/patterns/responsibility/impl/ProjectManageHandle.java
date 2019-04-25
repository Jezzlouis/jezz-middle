package com.jezz.design.patterns.responsibility.impl;


import com.jezz.design.patterns.responsibility.Handle;

import java.math.BigDecimal;

public class ProjectManageHandle implements Handle {

    @Override
    public void dealFeeRequest(String user, BigDecimal fee) {
        if(fee.compareTo(new BigDecimal("100")) == 1){
            System.out.println("...大于100块...交给老板审批");
            new BossManageHandle().dealFeeRequest(user,fee);
        }
    }
}
