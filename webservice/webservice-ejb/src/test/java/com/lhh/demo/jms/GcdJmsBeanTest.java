/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhh.demo.jms;

import com.lhh.demo.fixture.ContextFixture;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.Context;

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
public class GcdJmsBeanTest {


    private static Context context;
    private static ConnectionFactory connectionFactory;
    private static Queue queue;

    private GcdJmsBean gcdJmsBean;

    public GcdJmsBeanTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {

        
        context = ContextFixture.getEjbContext();
        connectionFactory = (ConnectionFactory) context.lookup("jms/TestConnectionFactory");
        queue = (Queue) context.lookup("jms/TestQueue");

    }

    @AfterClass
    public static void tearDownClass() {

    }

    @Before
    public void setUp() {

        this.gcdJmsBean = new GcdJmsBean();

    }

    @After
    public void tearDown() {
        this.gcdJmsBean = null;
    }

    /**
     * Test of sendInteger method, of class GcdJmsBean.
     */
    @Test
    public void testSendInteger() throws Exception {

        int integer1 = 3;
        int integer2 = 9;

        try {

            gcdJmsBean.setConnectionFactory(connectionFactory);
            gcdJmsBean.setQueue(queue);
            gcdJmsBean.sendInteger(integer1, integer2, false, Session.AUTO_ACKNOWLEDGE);

        } catch (Exception e) {
            fail("Should not have thrown any exception");
        }

        gcdJmsBean.retrieveHeadMsg(false, Session.AUTO_ACKNOWLEDGE);

    }

    /**
     * Test of retrieveHeadMsg method, of class GcdJmsBean.
     */
    @Test
    public void testRetrieveHeadMsg() throws Exception {

        List<Integer> expResult = new ArrayList<Integer>();
        expResult.add(-1);
        expResult.add(-5);
        gcdJmsBean.setConnectionFactory(connectionFactory);
        gcdJmsBean.setQueue(queue);
        gcdJmsBean.sendInteger(-1, -5, false, Session.AUTO_ACKNOWLEDGE);
        List<Integer> result = gcdJmsBean.retrieveHeadMsg(false, Session.AUTO_ACKNOWLEDGE);
        assertEquals(expResult, result);

    }

}
