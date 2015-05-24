/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhh.demo.rest;

import com.lhh.demo.data.IntegerResponse;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.api.json.JSONJAXBContext;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;

/**
 *
 * @author LiH
 */
@Provider
public class JAXBContextResolver implements ContextResolver<JAXBContext> {

    private JAXBContext context;
    private Class<?>[] types = {IntegerResponse.class};

    public JAXBContextResolver() throws Exception {
        this.context
                = new JSONJAXBContext(
                        JSONConfiguration.natural().build(), types);
    }

    @Override
    public JAXBContext getContext(Class<?> objectType) {
        for (Class<?> type : types) {
            if (type == objectType) {
                return context;
            }
        }
        return null;
    }
}
