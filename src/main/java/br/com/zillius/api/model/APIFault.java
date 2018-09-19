/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.model;

import java.io.Serializable;
import javax.ws.rs.core.Response.Status;

/**
 *
 * @author Daniel
 */
public class APIFault implements Serializable{
    
    private String message;
    private Integer errorCode;
    
    public APIFault(){
        
    }

    public APIFault(String message){
        this.message = message;
    }
    
    public APIFault(String message, Integer errorCode){
        this.message = message;
        this.errorCode = errorCode;
    }
    
    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the errorCode
     */
    public Integer getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode the errorCode to set
     */
    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
}
