/*
 * Copyright © 2014 zillius. All rights reserved.
 *
 * Created by a.accioly on 10/31/14 2:41 PM
 */

package br.com.zillius.api.exceptions;


import br.com.zillius.api.model.APIFault;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Indica que um argumento de consulta é inválido.
 */
public class IllegalQueryArgumentException extends WebApplicationException {

    /**
     * Constrói a exceção com uma mensagem de erro.
     *
     * @param message a mensagem de erro.
     */
    public IllegalQueryArgumentException(String message) {
        super(Response.status(Response.Status.BAD_REQUEST).entity(new APIFault(message)).type("application/json").build());
    }

    /**
     * Constrói a exceção com uma mensagem de erro e uma causa.
     *
     * @param message mensagem de erro.
     * @param cause exceção de causa.
     */
    public IllegalQueryArgumentException(String message, Throwable cause) {
        super(message, cause, Response.status(Response.Status.BAD_REQUEST).entity(new APIFault(message)).type("application/json").build());

    }
}
