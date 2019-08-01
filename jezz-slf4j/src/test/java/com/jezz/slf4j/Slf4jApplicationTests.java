package com.jezz.slf4j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Slf4jApplication.class)
public class Slf4jApplicationTests {

    @Test
    public void contextLoads() {
        Logger logger = LoggerFactory.getLogger(Slf4jApplicationTests.class);
        logger.error("123");
    }

}
