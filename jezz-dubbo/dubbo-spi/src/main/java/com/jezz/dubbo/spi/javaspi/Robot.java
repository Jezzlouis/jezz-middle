package com.jezz.dubbo.spi.javaspi;

import com.alibaba.dubbo.common.extension.SPI;

@SPI
public interface Robot {
    void sayHello();
}
