/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhh.demo;

import com.lhh.demo.dao.GcdDbBeanTest;
import com.lhh.demo.fixture.ContextFixture;
import com.lhh.demo.jms.GcdJmsBeanTest;
import com.lhh.demo.service.GcdBeanTest;
import com.lhh.demo.util.CalculatorTest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author LiH
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({CalculatorTest.class, GcdDbBeanTest.class, GcdJmsBeanTest.class, GcdBeanTest.class})
public class TestSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("TestSuite.setUpClass()");
        ContextFixture.startEjbContanier();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        System.out.println("TestSuite.tearDownClass()");
        ContextFixture.stopEjbContanier();
    }

    @Before
    public void setUp() throws Exception {
        System.out.println("TestSuite.setUp()");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("TestSuite.tearDown()");
    }

}
