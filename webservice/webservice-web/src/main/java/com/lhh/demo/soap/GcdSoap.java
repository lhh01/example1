/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhh.demo.soap;


import com.lhh.demo.service.GcdBeanLocal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebService;
import org.apache.log4j.Logger;

/**
 *
 * @author LiH
 */
@WebService
public class GcdSoap {

    private static final Logger LOG = Logger.getLogger(GcdSoap.class.getName());
    @EJB
    private GcdBeanLocal gcdBean;

    /*
     return the greatest common divisor of the two integers at the head of the queue
     */
    @WebMethod
    public int gcd() {
        int gcd = -1;
        try {
            gcd = gcdBean.retrieveHeadMsgAndCalcGcd(true, 0);
        } catch (Exception e) {
            LOG.debug("Exception occurred in SOAP gcd", e);
        }
        return gcd;
    }

    @WebMethod
    public List<Integer> gcdList() {
        List<Integer> result = new ArrayList<Integer>();
        try {
            result = gcdBean.listGcd();
        } catch (Exception e) {
            LOG.debug("Exception occurred in SOAP gcdList", e);
        }
        return result;

    }

    @WebMethod
    public int gcdSum() {
        int sum = 0;
        try {
            sum = gcdBean.gcdSum();
        } catch (Exception e) {
            LOG.debug("Exception occurred in SOAP gcdSum", e);
        }

        return sum;
    }

}
