/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhh.demo.service;

import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author LiH
 */
@Local
public interface GcdBeanLocal {

    String pushInteger(int integer1, int integer2, boolean transacted, int acknowledgeMode);

    int retrieveHeadMsgAndCalcGcd(boolean transacted, int acknowledgeMode);

    public List<Integer> listInteger();

    public List<Integer> listGcd();

    public int gcdSum();
}
