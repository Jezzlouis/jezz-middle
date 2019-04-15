package com.jezz.dubbo.spi.dubbospi;

import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.jezz.dubbo.spi.javaspi.Robot;
import org.junit.jupiter.api.Test;

public class DubboSPITest {

    @Test
    public void sayHello() throws Exception {
        ExtensionLoader<Robot> extensionLoader =
                ExtensionLoader.getExtensionLoader(Robot.class);
        Robot optimusPrime = extensionLoader.getExtension("optimusPrime");
        optimusPrime.sayHello();
        Robot bumblebee = extensionLoader.getExtension("bumblebee");
        bumblebee.sayHello();
    }
}