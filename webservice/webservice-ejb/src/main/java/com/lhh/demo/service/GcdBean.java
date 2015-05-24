/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhh.demo.service;

import com.lhh.demo.dao.GcdDbBeanLocal;
import com.lhh.demo.enumeration.Status;
import com.lhh.demo.exception.GetGcdRequestException;
import com.lhh.demo.exception.PushIntegerRequestException;
import com.lhh.demo.jms.GcdJmsBeanLocal;
import com.lhh.demo.model.Gcd;
import com.lhh.demo.model.IntegerRequest;
import com.lhh.demo.util.Calculator;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import javax.ejb.EJB;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.Session;

import org.apache.log4j.Logger;

/**
 *
 * @author LiH
 */
@Stateless
public class GcdBean implements GcdBeanLocal {

    private static final Logger LOG = Logger.getLogger(GcdBean.class.getName());

    @EJB
    private GcdDbBeanLocal gcdDbBean;

    @EJB
    private GcdJmsBeanLocal gcdJmsBean;

    @Override
    public String pushInteger(int integer1, int integer2, boolean transacted, int acknowledgeMode) {
        String result = Status.FAIL.getStatus();

        try {

            IntegerRequest integerRequest = gcdDbBean.getIntegerRequest(integer1, integer2);

            if (integerRequest == null) {

                gcdJmsBean.sendInteger(integer1, integer2, transacted, acknowledgeMode);

                gcdDbBean.storeIntegerRequest(integer1, integer2);

                result = Status.SUCCESS.getStatus();
            } else {
                result = Status.DUPLICATE.getStatus();
                LOG.debug(String.format("Request: integer1 = %d and integer2 = %d is already existed in table [integer_request]. Return duplicate status.", integer1, integer2));
            }
        } catch (Exception e) {

            LOG.debug("Exception occurred in pushInteger method", e);
            throw new PushIntegerRequestException(e);
        }

        return result;
    }

    @Override
    //@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public int retrieveHeadMsgAndCalcGcd(boolean transacted, int acknowledgeMode) {
        int gcdValue = -1;

        try {

            List<Integer> integers = gcdJmsBean.retrieveHeadMsg(transacted, acknowledgeMode);
            if (integers.size() == 2) {
                int firstInt = integers.get(0);
                int secondInt = integers.get(1);

                IntegerRequest intReq = gcdDbBean.getIntegerRequest(firstInt, secondInt);
                if (intReq != null) {
                    LOG.debug(String.format("Loaded IntegerRequest: integer1 = %d, integer2 = %d", intReq.getInteger1(), intReq.getInteger2()));

                    Calculator calc = new Calculator();

                    gcdValue = calc.calcGcd(firstInt, secondInt);

                    Gcd gcd = new Gcd();
                    gcd.setGcd(gcdValue);
                    gcd.setIntegerRequest(intReq);
                    gcdDbBean.storeGcd(gcd);

                } else {
                    LOG.debug(String.format("Could not load IntegerRequest by integer1 = %d and integer2 = %d", firstInt, secondInt));
                    throw new RuntimeException(String.format("No matching integer request (integer1 = %d , integer2 = %d) that synchronizes with head JMS message in DB.", firstInt, secondInt));
                }

            }

        } catch (Exception e) {
            LOG.debug("Exception occurred in retrieveHeadMsgAndCalcGcd method", e);
            throw new GetGcdRequestException(e);
        }
        return gcdValue;
    }

    @Override
    public List<Integer> listInteger() {
        ArrayList<Integer> list = new ArrayList<Integer>();

        List<IntegerRequest> integerRequestList = gcdDbBean.listIntegerRequest();
        for (IntegerRequest intReq : integerRequestList) {
            list.add(intReq.getInteger1());
            list.add(intReq.getInteger2());
        }

        return list;
    }

    @Override
    public List<Integer> listGcd() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        List<Gcd> gcdList = gcdDbBean.listGcd();
        for (Gcd gcd : gcdList) {
            list.add(gcd.getGcd());
        }
        return list;
    }

    @Override
    public int gcdSum() {
        Integer sum = 0;
        List<Gcd> gcdList = gcdDbBean.listGcd();
        for (Gcd gcd : gcdList) {
            sum = sum + gcd.getGcd();
        }
        return sum;
    }

    public GcdDbBeanLocal getGcdDbBean() {
        return gcdDbBean;
    }

    public void setGcdDbBean(GcdDbBeanLocal gcdDbBean) {
        this.gcdDbBean = gcdDbBean;
    }

    public GcdJmsBeanLocal getGcdJmsBean() {
        return gcdJmsBean;
    }

    public void setGcdJmsBean(GcdJmsBeanLocal gcdJmsBean) {
        this.gcdJmsBean = gcdJmsBean;
    }

}
