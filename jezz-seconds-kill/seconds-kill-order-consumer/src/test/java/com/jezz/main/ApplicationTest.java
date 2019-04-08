package com.jezz.main;

import com.jezz.service.OrderService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ApplicationTest {

    @Autowired
    private OrderService orderService;

    public ApplicationTest() {
        System.out.println("构造器 ...");
    }

    @Before
    public void setUp() throws Exception {
        System.out.println("before ....");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("after ....");
    }

    @Test
    public void test1(){
        //orderService.createWrongOrder(1);
    }


}