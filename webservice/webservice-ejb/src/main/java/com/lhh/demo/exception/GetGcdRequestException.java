/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhh.demo.exception;

/**
 *
 * @author LiH
 */
public class GetGcdRequestException extends RuntimeException{
    private static final long serialVersionUID = 683736320747874174L;
     public GetGcdRequestException() {
        super();
    }

    public GetGcdRequestException(String message) {
        super(message);
    }

    public GetGcdRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public GetGcdRequestException(Throwable cause) {
        super(cause);
    }
}
