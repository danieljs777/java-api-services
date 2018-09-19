/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.resources.clientes;

import br.com.zillius.api.dao.BaseDestinationDAO;
import br.com.zillius.api.dao.FilialDAO;
import br.com.zillius.api.exceptions.EntityNotFoundException;
import br.com.zillius.api.exceptions.IllegalBodyArgumentException;
import br.com.zillius.api.exceptions.IllegalPathArgumentException;
import br.com.zillius.api.exceptions.InternalServerErrorException;
import br.com.zillius.api.model.cliente.Estabelecimento;
import br.com.zillius.api.model.cliente.Filial;
import br.com.zillius.api.model.cliente.Taxa;
import br.com.zillius.api.model.cliente.Terminal;
import br.com.zillius.api.resources.RoutingResource;
import java.util.Arrays;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Daniel
 */
@Path("/filiais")
@RequestScoped
public class FiliaisResource extends RoutingResource {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(FiliaisResource.class.getName());

    @Context
    private UriInfo context;

    @Inject
    private FilialDAO filialDAO;

    /**
     * Creates a new instance of VendasResource
     */
    public FiliaisResource() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getJson(@NotNull @PathParam("id") String id) throws Exception {

        try {

            Filial filial = filialDAO.obterFilialPorId(id);

            return Response.ok(filial).build();
        } catch (EntityNotFoundException ex) {
            throw ex;
        } catch (IllegalArgumentException ex) {
            logger.error("Illegal path argument id: {}", ex);
            throw new IllegalPathArgumentException(ex.getMessage());
        } catch (WebApplicationException ex) {
            throw ex;
        } catch (Exception ex) {
            //TODO: Mensagem genérica - distutir com o Marketing.
            logger.error("Ops! no donuts for you");
            throw new InternalServerErrorException("Ops! no donuts for you");
        }
    }

    @POST
    @Consumes({"application/json"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response postJson(Filial filial) {
        try {

            filialDAO.salvarFilial(filial);

            return Response.ok().build();

        } catch (IllegalArgumentException ex) {
            logger.error("Illegal Body argument: {}", ex);
            throw new IllegalBodyArgumentException(ex.getMessage());
        } catch (WebApplicationException ex) {
            throw ex;
        } catch (Exception ex) {
            //TODO: Mensagem genérica - distutir com o Marketing.
            logger.error("Ops! no donuts for you");
            throw new InternalServerErrorException("Ops! no donuts for you");
        }
    }

    @POST
    @Consumes({"application/json"})
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/estabelecimentos")
    public Response postJson(Estabelecimento estabelecimento) {
        try {

            filialDAO.salvarEstabelecimento(estabelecimento);

            return Response.ok().build();

        } catch (IllegalArgumentException ex) {
            logger.error("Illegal Body argument: {}", ex);
            throw new IllegalBodyArgumentException(ex.getMessage());
        } catch (WebApplicationException ex) {
            throw ex;
        } catch (Exception ex) {
            //TODO: Mensagem genérica - distutir com o Marketing.
            logger.error("Ops! no donuts for you");
            throw new InternalServerErrorException("Ops! no donuts for you");
        }
    }

    @POST
    @Consumes({"application/json"})
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/terminais")
    public Response postJson(Terminal terminal) {
        try {

            filialDAO.salvarTerminal(terminal);

            return Response.ok().build();

        } catch (IllegalArgumentException ex) {
            logger.error("Illegal Body argument: {}", ex);
            throw new IllegalBodyArgumentException(ex.getMessage());
        } catch (WebApplicationException ex) {
            throw ex;
        } catch (Exception ex) {
            //TODO: Mensagem genérica - distutir com o Marketing.
            logger.error("Ops! no donuts for you");
            throw new InternalServerErrorException("Ops! no donuts for you");
        }
    }

    @POST
    @Consumes({"application/json"})
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/taxas")
    public Response postJson(Taxa taxa) {
        try {

            filialDAO.salvarTaxa(taxa);

            return Response.ok().build();

        } catch (IllegalArgumentException ex) {
            logger.error("Illegal Body argument: {}", ex);
            throw new IllegalBodyArgumentException(ex.getMessage());
        } catch (WebApplicationException ex) {
            throw ex;
        } catch (Exception ex) {
            //TODO: Mensagem genérica - distutir com o Marketing.
            logger.error("Ops! no donuts for you");
            throw new InternalServerErrorException("Ops! no donuts for you");
        }
    }

    @Override
    public List<? extends BaseDestinationDAO> getDAOS() {
        return Arrays.asList(this.filialDAO);
    }
}
