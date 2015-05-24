/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhh.demo.jms;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.StreamMessage;
import org.apache.log4j.Logger;

/**
 *
 * @author LiH
 */
@Stateless
public class GcdJmsBean implements GcdJmsBeanLocal {

    private static final Logger LOG = Logger.getLogger(GcdJmsBean.class.getName());
    @Resource(lookup = "java:module/jms/ProdConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(lookup = "java:module/jms/ProdQueue")
    private Queue queue;

    @Override
    public void sendInteger(int integer1, int integer2, boolean transacted, int acknowledgeMode) throws JMSException {

        Connection connection = null;
        Session session = null;
        try {

            connection = connectionFactory.createConnection();
            session = connection.createSession(transacted, acknowledgeMode);

            Destination dest = (Destination) queue;
            MessageProducer producer = session.createProducer(dest);
            StreamMessage message = session.createStreamMessage();
            message.writeInt(integer1);
            message.writeInt(integer2);
            producer.send(message);

        } finally {

            if (session != null) {
                try {
                    session.close();
                } catch (JMSException ex) {
                    LOG.debug("Can not close JMS session", ex);
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException ex) {
                    LOG.debug("Can not close JMS connection", ex);
                }
            }

        }

    }

    @Override
    public List<Integer> retrieveHeadMsg(boolean transacted, int acknowledgeMode) throws JMSException {

        List<Integer> result = new ArrayList<Integer>();

        Connection connection = null;
        Session session = null;
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(transacted, acknowledgeMode);
            Destination dest = (Destination) queue;
            MessageConsumer consumer = session.createConsumer(dest);

            Message m = consumer.receive(1000);

            if (m != null) {

                if (m instanceof StreamMessage) {
                    StreamMessage message = (StreamMessage) m;
                    int firstInt = message.readInt();
                    int secondInt = message.readInt();

                    result.add(0, firstInt);
                    result.add(1, secondInt);

                }

            } else {
                LOG.debug("Could not get message from the queue, m is null");
            }

        } finally {

            if (session != null) {
                try {
                    session.close();
                } catch (JMSException ex) {
                    LOG.debug("Can not close JMS session", ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException ex) {
                    LOG.debug("Can not close JMS connection", ex);
                }

            }

        }
        return result;
    }

    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public Queue getQueue() {
        return queue;
    }

    public void setQueue(Queue queue) {
        this.queue = queue;
    }

}
