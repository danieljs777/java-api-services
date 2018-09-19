/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.resources.clientes;

import br.com.zillius.api.dao.BaseDestinationDAO;
import br.com.zillius.api.dao.ClienteDAO;
import br.com.zillius.api.exceptions.EntityNotFoundException;
import br.com.zillius.api.exceptions.IllegalBodyArgumentException;
import br.com.zillius.api.exceptions.IllegalPathArgumentException;
import br.com.zillius.api.exceptions.InternalServerErrorException;
import br.com.zillius.api.model.APIFault;
import br.com.zillius.api.model.cliente.Cliente;
import br.com.zillius.api.resources.RoutingResource;
import java.util.Arrays;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
@Path("/clientes")
@RequestScoped
public class ClientesResource extends RoutingResource {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ClientesResource.class.getName());

    @Context
    private UriInfo context;

    @Inject
    private ClienteDAO clienteDAO;

    /**
     * Creates a new instance of VendasResource
     */
    public ClientesResource() {
    }

    @Path("/{id}")
    @GET()
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJson() throws Exception {

        try {

            Cliente cliente = clienteDAO.obterClientePorId(getPfjCode());

            if (cliente == null) {
                logger.error("Cliente not found");
                throw new EntityNotFoundException("Cliente not found");
            }

            return Response.ok(cliente).build();

        } catch (EntityNotFoundException ex) {
            throw ex;
        } catch (IllegalArgumentException ex) {
            logger.error("Illegal path argument clienteId: {}", ex);
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
    public Response salvarCliente(Cliente cliente) {
        try {

            clienteDAO.salvarCliente(cliente);

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
        return Arrays.asList(this.clienteDAO);
    }
}
