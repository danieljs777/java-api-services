/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.exceptions;

import br.com.zillius.api.model.APIFault;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author Daniel
 */
public class BusinessException extends WebApplicationException {

    /**
     * Constrói a exceção com uma mensagem de erro e uma causa.
     *
     * @param message mensagem de erro.
     * @param cause exceção de causa.
     */
     public BusinessException(String message) {
        super(Response.status(422).entity(new APIFault(message)).type("application/json").build());
    }

    /**
     * Constrói a exceção com uma mensagem de erro e uma causa.
     *
     * @param message mensagem de erro.
     * @param cause exceção de causa.
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause, Response.status(422).entity(new APIFault(message)).type("application/json").build());

    }
}
