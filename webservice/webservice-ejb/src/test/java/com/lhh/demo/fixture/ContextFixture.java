/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhh.demo.fixture;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;

/**
 *
 * @author LiH
 */
public class ContextFixture {

    private static EJBContainer ejbContainer;
    private static Context context;

    public static void startEjbContanier() {
        ejbContainer = javax.ejb.embeddable.EJBContainer.createEJBContainer();

        context = ejbContainer.getContext();
    }

    public static void stopEjbContanier() {
        if (ejbContainer != null) {
            ejbContainer.close();

        }
    }

    public static Context getEjbContext() {
        return context;
    }
}
