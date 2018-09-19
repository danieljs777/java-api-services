/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.resources.entradas;

import br.com.zillius.api.dao.BaseDestinationDAO;
import br.com.zillius.api.dao.EntradasDAO;
import br.com.zillius.api.exceptions.BusinessException;
import br.com.zillius.api.exceptions.IllegalBodyArgumentException;
import br.com.zillius.api.exceptions.InternalServerErrorException;
import br.com.zillius.api.model.APIConstants;
import br.com.zillius.api.model.ProcessoResponse;
import br.com.zillius.api.model.VendasRequest;
import br.com.zillius.api.model.UDTVendasResponse;
import br.com.zillius.api.model.VendasResponse;
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
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Daniel
 */
@Path("/entradas")
@RequestScoped
public class EntradasResource extends RoutingResource {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(EntradasResource.class.getName());

    @Inject
    private EntradasDAO entradasDAO;

    /**
     * Creates a new instance of VendasResource
     */
    public EntradasResource() {
    }
    
    @GET
    @Path("conciliar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response conciliar() {

        String pfjCode = getPfjCode().toString();
        
        long jobId = entradasDAO.iniciarConciliacao(pfjCode, getDataInicial(), getDataFinal());
        
        ProcessoResponse exclusaoResponse = new ProcessoResponse();
        exclusaoResponse.setProcessId(jobId);

        GenericEntity retornos = new GenericEntity<ProcessoResponse>(exclusaoResponse) {};

        return Response.ok(retornos).build();               
    }
        
    @POST
    @Consumes({"application/json"})
    @Path("unico")
    @Produces(MediaType.APPLICATION_JSON)
    public Response postJson(VendasRequest vendas) throws Exception {
        try {

            final String pfjCode = getPfjCode(vendas.getClienteId().toString());

            vendas.setPfjCode(pfjCode);

            entradasDAO.salvarEntradasDeVendas(vendas);

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
    @Path("vendas")
    public Response postBatch(List<VendasRequest> vendas) throws Exception {

        if(vendas.size() > APIConstants.MAX_TOTAL_SALES_IN_BATCH) {
            throw new BusinessException("Total sales in batch must not be greater than " + APIConstants.MAX_TOTAL_SALES_IN_BATCH + " entries");
        }
        
        if(vendas.size() < 1) { 
            throw new BusinessException("Total sales in batch must be greater than 0 entries");
        }
    
        try {
            
            for (VendasRequest venda : vendas) {
                final String pfjCode = getPfjCode(venda.getClienteId());
                venda.setPfjCode(pfjCode);
            }

            List<UDTVendasResponse> listVendasResponse = entradasDAO.salvarLoteDeVendas(vendas);

            VendasResponse vendaResponse = new VendasResponse();

            listVendasResponse = buildSalesReturnLink(listVendasResponse, "getRetornosDeVendas");

            vendaResponse.setRetornos(listVendasResponse);

            GenericEntity retornos = new GenericEntity<VendasResponse>(vendaResponse) {};

            return Response.ok(retornos).build();
        } catch (IllegalArgumentException ex) {
            logger.error("Illegal Body argument: {}", ex);
            throw new IllegalBodyArgumentException(ex.getMessage());
        } catch (WebApplicationException ex) {
            throw ex;
        } catch (Exception ex) {
            //TODO: Mensagem genérica - discutir com o Marketing.
            logger.error("Ops! no donuts for you");
            throw new InternalServerErrorException("Ops! no donuts for you");
        }
    }
    
    @POST
    @Consumes({"application/json"})
    @Path("excluir/lote")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBatch(List<VendasRequest> vendas) throws Exception {
        try {
            
            String pfjCode = getPfjCode().toString();

            if(vendas.size() < 1) { 
                throw new BusinessException("Total sales in batch must be greater than 0 entries");
            }
            
            for (VendasRequest venda : vendas) {
                venda.setPfjCode(pfjCode);
            }            

            long processId = entradasDAO.excluirLoteDeVendas(vendas, pfjCode);
            ProcessoResponse exclusaoResponse = new ProcessoResponse();
            exclusaoResponse.setProcessId(processId);
            
            GenericEntity retornos = new GenericEntity<ProcessoResponse>(exclusaoResponse) {};
            
            return Response.ok(retornos).build();            
            
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
    @Path("excluir/periodo")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteRange() throws Exception {
        try {

            String pfjCode = getPfjCode().toString();
            
            long processId = entradasDAO.excluirPeriodoDeVendas(getDataInicial(), getDataFinal(), pfjCode);
            ProcessoResponse exclusaoResponse = new ProcessoResponse();
            exclusaoResponse.setProcessId(processId);
            
            GenericEntity retornos = new GenericEntity<ProcessoResponse>(exclusaoResponse) {};
            
            return Response.ok(retornos).build();            

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
        return Arrays.asList(this.entradasDAO);
    }
}
