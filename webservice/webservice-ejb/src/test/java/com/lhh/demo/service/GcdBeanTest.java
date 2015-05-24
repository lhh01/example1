/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhh.demo.service;

import com.lhh.demo.dao.GcdDbBean;
import com.lhh.demo.dao.GcdDbBeanLocal;
import com.lhh.demo.enumeration.Status;
import com.lhh.demo.exception.GetGcdRequestException;
import com.lhh.demo.fixture.ContextFixture;

import com.lhh.demo.jms.GcdJmsBean;

import com.lhh.demo.model.Gcd;
import com.lhh.demo.model.IntegerRequest;

import java.util.ArrayList;

import java.util.List;

import javax.ejb.embeddable.EJBContainer;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.jms.Session;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import javax.naming.Context;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import org.mockito.Mockito;

/**
 *
 * @author LiH
 */
public class GcdBeanTest {


    private static Context context;
    private static ConnectionFactory connectionFactory;
    private static Queue queue;
    private static EntityManagerFactory emf;
    private GcdJmsBean gcdJmsBean;
    private GcdDbBean gcdDbBean;
    private GcdBean gcdBean;
    private EntityManager em;
    private EntityTransaction et;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    public GcdBeanTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {

        
        context = ContextFixture.getEjbContext();
        connectionFactory = (ConnectionFactory) context.lookup("jms/TestConnectionFactory");
        queue = (Queue) context.lookup("jms/TestQueue");

        emf = Persistence.createEntityManagerFactory("webservice_test-ejbPU");

    }

    @AfterClass
    public static void tearDownClass() {

        if (emf != null) {
            emf.close();
        }

    }

    @Before
    public void setUp() throws Exception {

        this.gcdBean = new GcdBean();
        this.gcdJmsBean = new GcdJmsBean();
        this.gcdDbBean = new GcdDbBean();

        this.em = emf.createEntityManager();
        this.et = em.getTransaction();
        et.begin();
    }

    @After
    public void tearDown() throws Exception {
        et.rollback();
        this.gcdBean = null;
        this.gcdJmsBean = null;
        this.gcdDbBean = null;
        this.em.close();
    }

    /**
     * Test of pushInteger method, of class GcdBean.
     */
    @Test
    public void testPushInteger() throws Exception {
        System.out.println("testPushInteger");
        int integer1 = 3;
        int integer2 = 60;
//        Map<String, Object> properties = new HashMap<String, Object>();
//        String currentPath = GcdBeanTest.class.getProtectionDomain().getCodeSource().getLocation().getPath();
//        properties.put("org.glassfish.ejb.embedded.glassfish.skip-client-module", Boolean.TRUE);
//        properties.put(EJBContainer.MODULES, new File("target\\class"));

        gcdDbBean.setEm(em);
        gcdJmsBean.setConnectionFactory(connectionFactory);
        gcdJmsBean.setQueue(queue);
        gcdBean.setGcdDbBean(gcdDbBean);
        gcdBean.setGcdJmsBean(gcdJmsBean);

        String expResult = Status.SUCCESS.getStatus();

        String result = gcdBean.pushInteger(integer1, integer2, false, Session.AUTO_ACKNOWLEDGE);
        assertEquals(expResult, result);
        //clean queue
        gcdJmsBean.retrieveHeadMsg(false, Session.AUTO_ACKNOWLEDGE);

    }

    /**
     * Test of retrieveHeadMsgAndCalcGcd method, of class GcdBean.
     */
    @Test
    public void testRetrieveHeadMsgAndCalcGcd() throws Exception {
        System.out.println("testRetrieveHeadMsgAndCalcGcd");
        gcdDbBean.setEm(em);
        gcdJmsBean.setConnectionFactory(connectionFactory);
        gcdJmsBean.setQueue(queue);
        gcdBean.setGcdDbBean(gcdDbBean);
        gcdBean.setGcdJmsBean(gcdJmsBean);
        int expResult = 3;

        gcdBean.pushInteger(3, 6, false, Session.AUTO_ACKNOWLEDGE);
        int result = gcdBean.retrieveHeadMsgAndCalcGcd(false, Session.AUTO_ACKNOWLEDGE);
        assertEquals(expResult, result);

    }

    /**
     * Test of retrieveHeadMsgAndCalcGcd method, of class GcdBean.
     */
    @Test
    public void testRetrieveHeadMsgAndCalcGcdReturnNegative1ByNoMessageInTheQueue() throws Exception {

        System.out.println("testRetrieveHeadMsgAndCalcGcdReturnNegative1ByNoMessageInTheQueue");
        gcdDbBean.setEm(em);
        gcdJmsBean.setConnectionFactory(connectionFactory);
        gcdJmsBean.setQueue(queue);
        gcdBean.setGcdDbBean(gcdDbBean);
        gcdBean.setGcdJmsBean(gcdJmsBean);

        int result = gcdBean.retrieveHeadMsgAndCalcGcd(false, Session.AUTO_ACKNOWLEDGE);
        assertEquals(-1, result);
    }

    /**
     * Test of retrieveHeadMsgAndCalcGcd method, of class GcdBean.
     */
    @Test
    public void testRetrieveHeadMsgAndCalcGcdThrowExceptionByNoMatchingIntegerRequest() throws Exception {

        System.out.println("testRetrieveHeadMsgAndCalcGcdThrowExceptionByNoMatchingIntegerRequest");
        thrown.expect(GetGcdRequestException.class);
        thrown.expectMessage("No matching integer request");

        gcdDbBean.setEm(em);
        gcdJmsBean.setConnectionFactory(connectionFactory);
        gcdJmsBean.setQueue(queue);
        gcdBean.setGcdDbBean(gcdDbBean);
        gcdBean.setGcdJmsBean(gcdJmsBean);
        gcdJmsBean.sendInteger(12, 19, false, Session.AUTO_ACKNOWLEDGE);
        gcdBean.retrieveHeadMsgAndCalcGcd(false, Session.AUTO_ACKNOWLEDGE);

    }

    /**
     * Test of listInteger method, of class GcdBean.
     */
    @Test
    public void testListInteger() throws Exception {
        System.out.println("testListInteger");
        GcdDbBeanLocal gcdDbBean = Mockito.mock(GcdDbBeanLocal.class);
        List<IntegerRequest> integerRequestList = new ArrayList<IntegerRequest>();
        IntegerRequest intReq1 = new IntegerRequest(4, 8);
        IntegerRequest intReq2 = new IntegerRequest(6, 9);
        integerRequestList.add(intReq1);
        integerRequestList.add(intReq2);

        Mockito.when(gcdDbBean.listIntegerRequest()).thenReturn(integerRequestList);

        gcdBean.setGcdDbBean(gcdDbBean);

        List<Integer> expResult = new ArrayList<Integer>();
        expResult.add(4);
        expResult.add(8);
        expResult.add(6);
        expResult.add(9);
        List<Integer> result = gcdBean.listInteger();

        assertArrayEquals(expResult.toArray(), result.toArray());
        int resultSize = result.size();

        assertEquals(4, resultSize);
        assertArrayEquals(expResult.toArray(), result.toArray());

    }

    /**
     * Test of listGcd method, of class GcdBean.
     */
    @Test
    public void testListGcd() throws Exception {
        System.out.println("testListGcd");
        GcdDbBeanLocal gcdDbBean = Mockito.mock(GcdDbBeanLocal.class);
        List<Gcd> gcdList = new ArrayList<Gcd>();
        IntegerRequest intReq1 = new IntegerRequest(4, 8);
        IntegerRequest intReq2 = new IntegerRequest(6, 9);

        gcdList.add(new Gcd(4, intReq1));
        gcdList.add(new Gcd(3, intReq2));
        Mockito.when(gcdDbBean.listGcd()).thenReturn(gcdList);

        gcdBean.setGcdDbBean(gcdDbBean);

        List<Integer> expResult = new ArrayList<Integer>();
        expResult.add(4);
        expResult.add(3);
        List<Integer> result = gcdBean.listGcd();

        assertArrayEquals(expResult.toArray(), result.toArray());
        int resultSize = result.size();

        assertEquals(2, resultSize);
        assertArrayEquals(expResult.toArray(), result.toArray());
    }

    /**
     * Test of gcdSum method, of class GcdBean.
     */
    //@Test
    public void testGcdSum() throws Exception {
        System.out.println("testGcdSum");
        GcdDbBeanLocal gcdDbBean = Mockito.mock(GcdDbBeanLocal.class);
        List<Gcd> gcdList = new ArrayList<Gcd>();
        IntegerRequest intReq1 = new IntegerRequest(4, 8);
        IntegerRequest intReq2 = new IntegerRequest(6, 9);

        gcdList.add(new Gcd(4, intReq1));
        gcdList.add(new Gcd(3, intReq2));
        Mockito.when(gcdDbBean.listGcd()).thenReturn(gcdList);

        gcdBean.setGcdDbBean(gcdDbBean);

        int result = gcdBean.gcdSum();

        assertEquals(7, result);

    }

}
