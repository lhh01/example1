/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhh.demo.exception;

import com.sun.jersey.api.Responses;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author LiH
 */
public class PushIntegerRequestMissingParameterException extends WebApplicationException{
    private static final long serialVersionUID = 8169203240206568070L;
    
    
    public PushIntegerRequestMissingParameterException(){
        super(Responses.clientError().build());
    }
    
    
    public PushIntegerRequestMissingParameterException(String message){
         super(Response.status(Responses.CLIENT_ERROR).entity(message).type("text/plain").build());
    }
    
}




