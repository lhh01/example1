/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhh.demo.jms;

import java.util.List;
import javax.ejb.Local;
import javax.jms.JMSException;

/**
 *
 * @author LiH
 */
@Local
public interface GcdJmsBeanLocal {

    public void sendInteger(int integer1, int integer2, boolean transacted, int acknowledgeMode) throws JMSException;

    public List<Integer> retrieveHeadMsg(boolean transacted, int acknowledgeMode) throws JMSException;
}
