/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.resources;

import br.com.zillius.api.model.DrillDownType;
import br.com.zillius.api.model.ResourceType;
import javax.enterprise.context.RequestScoped;
import javax.naming.NamingException;
import javax.ws.rs.Path;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;

/**
 *
 * @author Daniel
 */
@Path("lancamentos")
@RequestScoped
public class LancamentosResource extends RoutingResource {

    /**
     * Contexto do recurso
     */
    @Context
    private ResourceContext resourceContext;

    private DrillDownResource configureResource(DrillDownResource lancamentosDrillDownResource) {

        lancamentosDrillDownResource.setResourceType(ResourceType.LANCAMENTOS);

        return lancamentosDrillDownResource;
    }

    @Path("recebimentos")
    public DrillDownResource getRecebimentos() throws NamingException {

        DrillDownResource lancamentosDrillDownResource = resourceContext.getResource(DrillDownResource.class);

        lancamentosDrillDownResource.setDrillDownType(DrillDownType.RECEBIMENTOS);

        lancamentosDrillDownResource = configureResource(lancamentosDrillDownResource);

        return lancamentosDrillDownResource;
    }

    @Path("vendas")
    public DrillDownResource getVendas() throws NamingException {

        DrillDownResource lancamentosDrillDownResource = resourceContext.getResource(DrillDownResource.class);

        lancamentosDrillDownResource.setDrillDownType(DrillDownType.VENDAS);

        lancamentosDrillDownResource = configureResource(lancamentosDrillDownResource);

        return lancamentosDrillDownResource;
    }

    @Path("previsoes")
    public DrillDownResource getPrevisoes() throws NamingException {

       DrillDownResource lancamentosDrillDownResource = resourceContext.getResource(DrillDownResource.class);

        lancamentosDrillDownResource.setDrillDownType(DrillDownType.PREVISOES);

        lancamentosDrillDownResource = configureResource(lancamentosDrillDownResource);

        return lancamentosDrillDownResource;
    }
}
