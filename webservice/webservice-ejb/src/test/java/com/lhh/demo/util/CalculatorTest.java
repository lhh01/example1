/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhh.demo.util;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author LiH
 */
public class CalculatorTest {
    
    public CalculatorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of calcGcd method, of class Calculator.
     */
    @Test
    public void testCalcGcdWith0And0() {
       
        int interger1 = 0;
        int integer2 = 0;
        Calculator instance = new Calculator();
        int expResult = 0;
        int result = instance.calcGcd(interger1, integer2);
        assertEquals(expResult, result);
      
    }
    
    
    
     /**
     * Test of calcGcd method, of class Calculator.
     */
    @Test
    public void testCalcGcdWith2And12() {
       
        int interger1 = 2;
        int integer2 = 12;
        Calculator instance = new Calculator();
        int expResult = 2;
        int result = instance.calcGcd(interger1, integer2);
        assertEquals(expResult, result);
      
    }
    
    
    
    
     /**
     * Test of calcGcd method, of class Calculator.
     */
    @Test
    public void testCalcGcdWithNegative1And8() {
   
        int interger1 = -1;
        int integer2 = 8;
        Calculator instance = new Calculator();
        int expResult = 1;
        int result = instance.calcGcd(interger1, integer2);
        assertEquals(expResult, result);
      
    }
    
}
