/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhh.demo.util;

import java.math.BigInteger;

/**
 *
 * @author LiH
 */
public class Calculator {
    
     public int calcGcd(int interger1, int integer2) {
        BigInteger b1 = BigInteger.valueOf(interger1);
        BigInteger b2 = BigInteger.valueOf(integer2);
        BigInteger gcd = b1.gcd(b2);
        return gcd.intValue();
    }
    
}
