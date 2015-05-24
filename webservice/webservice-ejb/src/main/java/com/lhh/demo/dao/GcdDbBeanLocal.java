/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhh.demo.dao;

import com.lhh.demo.model.Gcd;
import com.lhh.demo.model.IntegerRequest;
import java.util.List;
import javax.ejb.Local;
import javax.persistence.EntityManager;

/**
 *
 * @author LiH
 */
@Local
public interface GcdDbBeanLocal {

    void storeIntegerRequest(int integer1, int integer2);

    IntegerRequest getIntegerRequest(int integer1, int integer2);

    List<IntegerRequest> listIntegerRequest();

    void storeGcd(Gcd gcd);

    List<Gcd> listGcd();


}
