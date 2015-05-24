/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhh.demo.dao;

import com.lhh.demo.model.Gcd;
import com.lhh.demo.model.IntegerRequest;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.Persistence;
import org.apache.log4j.Logger;
import org.hamcrest.CoreMatchers;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 *
 * @author LiH
 */
public class GcdDbBeanTest {
    
    private static Logger LOG = Logger.getLogger(GcdDbBeanTest.class);
    private static EntityManagerFactory emf;
    
    private EntityManager em;
    private EntityTransaction et;
    private GcdDbBean gcdDbBean;
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    public GcdDbBeanTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
        emf = Persistence.createEntityManagerFactory("webservice_test-ejbPU");
        
    }
    
    @AfterClass
    public static void tearDownClass() {
        if (emf != null) {
            emf.close();
        }
        
    }
    
    @Before
    public void setUp() {
        
        this.em = emf.createEntityManager();
        this.gcdDbBean = new GcdDbBean();
        this.gcdDbBean.setEm(em);
        this.et = em.getTransaction();
        this.et.begin();
    }
    
    @After
    public void tearDown() {
        this.et.rollback();
    
        this.em.close();
        this.gcdDbBean = null;
        
    }

    /**
     * Test of storeInteger and listInteger methods, of class GcdDbBean.
     */
    @Test
    public void testStoreAndListIntegers() throws Exception {
        System.out.println("testing storeInteger and listInteger");
        
        int integer1 = 1;
        int integer2 = 3;
        
        gcdDbBean.storeIntegerRequest(integer1, integer2);
        em.flush();
        IntegerRequest intReq = gcdDbBean.getIntegerRequest(1, 3);
        
        assertEquals(integer1, intReq.getInteger1().intValue());
        
        assertEquals(integer2, intReq.getInteger2().intValue());
        
    }
    
    @Test
    public void testListIntegerRequest() {
        System.out.println("testing listIntegerRequest");
//        thrown.expect(javax.persistence.PersistenceException.class);
//        thrown.expectMessage("org.hibernate.exception.ConstraintViolationException: could not insert");
        em.setFlushMode(FlushModeType.COMMIT);
        gcdDbBean.storeIntegerRequest(3, 5);
        em.flush();
        gcdDbBean.storeIntegerRequest(3, 1);
        em.flush();
        
        try {
            gcdDbBean.storeIntegerRequest(3, 1);
            em.flush();
        } catch (Exception e) {
            Assert.assertThat(e.getMessage(), CoreMatchers.containsString("could not insert"));
        }
        
        List<IntegerRequest> result = gcdDbBean.listIntegerRequest();
        
        int length = result.size();
        assertEquals(2, length);
        
        for (int i = 0; i < length; i++) {
            IntegerRequest intReq = result.get(i);
            
            if (i == 0) {
                assertEquals(3, intReq.getInteger1().intValue());
                
                assertEquals(5, intReq.getInteger2().intValue());
            } else {
                assertEquals(3, intReq.getInteger1().intValue());
                
                assertEquals(1, intReq.getInteger2().intValue());
            }
            
        }
        
    }
    
    @Test
    public void testIntegerRequestNotExist() {
        System.out.println("testIntegerRequestNotExist");
        IntegerRequest intReq = gcdDbBean.getIntegerRequest(3, 5);
        
        assertNull(intReq);
    }
    
    @Test
    public void testStoreGcdAndListGcd() {
        System.out.println("testing storeGcd and listGcd");
        
        gcdDbBean.storeIntegerRequest(10, 5);
        em.flush();
        IntegerRequest intReq = gcdDbBean.getIntegerRequest(10, 5);
        
        Gcd gcd = new Gcd();
        gcd.setGcd(5);
        gcd.setIntegerRequest(intReq);
        gcdDbBean.storeGcd(gcd);
        em.flush();
        List<Gcd> result = gcdDbBean.listGcd();
        
        int length = result.size();
        assertEquals(1, length);
        Gcd gcdResult = result.get(0);
        
        assertEquals(5, gcdResult.getGcd().intValue());
        
    }
}
