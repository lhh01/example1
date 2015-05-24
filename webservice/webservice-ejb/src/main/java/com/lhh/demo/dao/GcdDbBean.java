/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhh.demo.dao;

import com.lhh.demo.model.Gcd;
import com.lhh.demo.model.IntegerRequest;
import java.util.List;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.apache.log4j.Logger;

/**
 *
 * @author LiH
 */
@Stateless
public class GcdDbBean implements GcdDbBeanLocal {

    private static final Logger LOG = Logger.getLogger(GcdDbBean.class.getName());
    
    @PersistenceContext(unitName = "webservice-ejbPU")
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public void storeIntegerRequest(int integer1, int integer2) {
        IntegerRequest intReq = new IntegerRequest();

        intReq.setInteger1(integer1);
        intReq.setInteger2(integer2);

        em.persist(intReq);

    }

    @Override
    public IntegerRequest getIntegerRequest(int integer1, int integer2) {
        IntegerRequest integerRequest = null;
        TypedQuery<IntegerRequest> findIntegerRequest = em.createNamedQuery("IntegerRequest.findByIntegers", IntegerRequest.class);
        findIntegerRequest.setParameter("integer1", integer1);
        findIntegerRequest.setParameter("integer2", integer2);
        try {
            integerRequest = findIntegerRequest.getSingleResult();
        } catch (NoResultException e) {
           
            LOG.debug(String.format("Request: integer1 = %d and integer2 = %d do not exist in table [integer_request]", integer1, integer2));
        }
     
        return integerRequest;
    }

    @Override
    public List<IntegerRequest> listIntegerRequest() {

        TypedQuery<IntegerRequest> findAllIntegerRequest = em.createNamedQuery("IntegerRequest.findAll", IntegerRequest.class);

        return findAllIntegerRequest.getResultList();

    }

    @Override
    public void storeGcd(Gcd gcd) {
        em.persist(gcd);
    }

    @Override
    public List<Gcd> listGcd() {
        TypedQuery<Gcd> findAllInputIntegers = em.createNamedQuery("Gcd.findAll", Gcd.class);

        return findAllInputIntegers.getResultList();
    }
}
