/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.resources;

import br.com.zillius.api.dao.BaseDestinationDAO;
import br.com.zillius.api.dao.EntradasDAO;
import br.com.zillius.api.dao.ProcessoDAO;
import br.com.zillius.api.model.DrillDownType;
import br.com.zillius.api.model.ProcessoResponse;
import br.com.zillius.api.model.Processo;
import br.com.zillius.api.model.ResourceType;
import java.util.Arrays;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Daniel
 */
@Path("/processos")
@RequestScoped
public class ProcessoResource extends RoutingResource {

    @Inject
    private ProcessoDAO processoDAO;
    
    public ProcessoResource() {
        
    }
        
    @GET
    @Consumes({"application/json"})
    @Produces(MediaType.APPLICATION_JSON)        
    @Path("status")
    public Response getStatus(@QueryParam("processoId") int processoId) throws Exception {

        Processo processo = processoDAO.getProcessStatus(processoId);

        GenericEntity retornos = new GenericEntity<Processo>(processo) {};

        return Response.ok(retornos).build();          

    }
    
    @Override
    public List<? extends BaseDestinationDAO> getDAOS() {
        return Arrays.asList(this.processoDAO);
    }    
    
    
}
