/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhh.demo.enumeration;

/**
 *
 * @author LiH
 */
public enum Status {

    SUCCESS("Success"),
    DUPLICATE("Duplicate"),
    FAIL("Fail");

    Status(String status) {
        this.status = status;

    }

    private String status;
    
    
    public String getStatus(){
        return status;
    }
}
