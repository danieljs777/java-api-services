/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.resources;

import br.com.zillius.api.dao.ConciliacaoDAO;
import br.com.zillius.api.dao.ConciliacaoDAOImpl;
import br.com.zillius.api.model.DrillDownType;
import br.com.zillius.api.model.ProcessoResponse;
import br.com.zillius.api.model.ResourceType;
import br.com.zillius.api.resources.entradas.EntradasResource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Daniel
 */
@Path("conciliacoes")
@RequestScoped
public class ConciliacoesResource extends RoutingResource {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(EntradasResource.class.getName());

    /**
     * Contexto do recurso
     */
    @Context
    private ResourceContext resourceContext;

    

    @Path("recebimentos")
    public DrillDownResource getRecebimentos() {

        DrillDownResource conciliacoesDrillDownResource = resourceContext.getResource(DrillDownResource.class);
        
        conciliacoesDrillDownResource.setDrillDownType(DrillDownType.RECEBIMENTOS);
        conciliacoesDrillDownResource.setResourceType(ResourceType.CONCILIACOES);

        return conciliacoesDrillDownResource;
    }

    @Path("vendas")
    @Consumes({"application/json"})
    @Produces(MediaType.APPLICATION_JSON)
    public DrillDownResource getVendas() {

        DrillDownResource conciliacoesDrillDownResource = resourceContext.getResource(DrillDownResource.class);

        conciliacoesDrillDownResource.setDrillDownType(DrillDownType.VENDAS);
        conciliacoesDrillDownResource.setResourceType(ResourceType.CONCILIACOES);

        return conciliacoesDrillDownResource;

    }
}
