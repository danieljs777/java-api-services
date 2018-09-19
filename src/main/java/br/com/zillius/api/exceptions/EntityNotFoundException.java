/*
 * Copyright © 2014 zillius. All rights reserved.
 *
 * Created by a.accioly on 10/31/14 2:14 PM
 */
package br.com.zillius.api.exceptions;

import br.com.zillius.api.model.APIFault;
import br.com.zillius.api.model.Url;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Indica que uma entidade não foi encontrada no banco de dados.
 */
public class EntityNotFoundException extends WebApplicationException {

    public EntityNotFoundException(String message) {
        super(Response.status(Response.Status.NOT_FOUND).entity(new APIFault(message)).type("application/json").build());
    }

    /**
     * Constrói a exceção com uma mensagem de erro e uma causa.
     *
     * @param message mensagem de erro.
     * @param cause exceção de causa.
     */
    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause, Response.status(Response.Status.NOT_FOUND).entity(new APIFault(message)).type("application/json").build());

    }
}
