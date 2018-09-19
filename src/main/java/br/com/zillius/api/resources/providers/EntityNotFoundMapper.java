/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.resources.providers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Daniel
 */
@Provider
public class EntityNotFoundMapper implements ExceptionMapper<javax.persistence.EntityNotFoundException> {
  public Response toResponse(javax.persistence.EntityNotFoundException ex) {
    return Response.status(404).
      entity(ex.getMessage()).
      type("aplication/json").
      build();
  }
}
