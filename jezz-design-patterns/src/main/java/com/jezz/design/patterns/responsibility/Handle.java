package com.jezz.design.patterns.responsibility;

import java.math.BigDecimal;

public interface Handle {

    void dealFeeRequest(String user, BigDecimal fee);

}
