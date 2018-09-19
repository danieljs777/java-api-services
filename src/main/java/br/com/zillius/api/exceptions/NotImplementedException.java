/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author Daniel
 */
public class NotImplementedException extends WebApplicationException {

    /**
     * Constrói a exceção com uma mensagem de erro.
     *
     * @param message a mensagem de erro.
     */
    public NotImplementedException(String message) {
        super(Response.status(Response.Status.NOT_IMPLEMENTED).entity(message).type("text/plain").build());
    }

    /**
     * Constrói a exceção com uma mensagem de erro e uma causa.
     *
     * @param message mensagem de erro.
     * @param cause exceção de causa.
     */
    public NotImplementedException(String message, Throwable cause) {
        super(message, cause, Response.status(Response.Status.NOT_IMPLEMENTED).entity(message).type("text/plain").build());

    }
}
