/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhh.demo.rest;

import com.lhh.demo.data.IntegerResponse;
import com.lhh.demo.enumeration.Status;
import com.lhh.demo.exception.PushIntegerRequestMissingParameterException;

import com.lhh.demo.service.GcdBeanLocal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import javax.ws.rs.core.MediaType;
import org.apache.log4j.Logger;

/**
 *
 * @author LiH
 */
@Stateless
@Path("resources")
public class GcdRest {

    private static final Logger LOG = Logger.getLogger(GcdRest.class.getName());
    @EJB
    private GcdBeanLocal gcdBean;

    @POST
    @Path("/integers")
    @Produces(MediaType.TEXT_PLAIN)
    public String push(@FormParam("integer1") Integer i1, @FormParam("integer2") Integer i2) {
        LOG.debug(String.format("REST push request integer1 = %d, integer2 = %d", i1, i2));
        
        if(i1== null || i2 == null){
            throw new PushIntegerRequestMissingParameterException("Missing one or more Parameters");
        }
        String status = Status.FAIL.getStatus();
        try {
            status = gcdBean.pushInteger(i1, i2, true, 0);
            
        } catch (Exception e) {
            LOG.debug("Exception occurred in REST push", e);
        }

        return status;

    }

    @GET
    @Path("/integers")
    @Produces(MediaType.APPLICATION_JSON)
    public List<IntegerResponse> list() {

        List<IntegerResponse> responseList = new ArrayList<IntegerResponse>();
        List<Integer> intList = gcdBean.listInteger();

        for (Integer num : intList) {
            IntegerResponse respInt = new IntegerResponse();
            respInt.setInteger(num);
            responseList.add(respInt);
        }

        return responseList;

    }

}
