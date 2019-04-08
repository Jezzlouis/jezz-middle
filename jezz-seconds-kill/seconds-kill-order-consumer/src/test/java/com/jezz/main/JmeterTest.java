package com.jezz.main;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JmeterTest {

    public JmeterTest() {
        System.out.println("con...");
    }

    @Before
    public void setUp() throws Exception {
        System.out.println("tetup...");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("down...");
    }

    @Test
    public void test111(){
        System.out.println("test111...");
    }
}
