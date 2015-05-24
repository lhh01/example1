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
public class PushIntegerRequestException extends RuntimeException {

    private static final long serialVersionUID = -4055523811681599212L;

    public PushIntegerRequestException() {
        super();
    }

    public PushIntegerRequestException(String message) {
        super(message);
    }

    public PushIntegerRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public PushIntegerRequestException(Throwable cause) {
        super(cause);
    }
}
