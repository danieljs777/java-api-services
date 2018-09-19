/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.resources;

import br.com.zillius.api.dao.BaseDestinationDAO;
import br.com.zillius.api.dao.ConciliacaoDAO;
import br.com.zillius.api.dao.LancamentoDAO;
import br.com.zillius.api.exceptions.EntityNotFoundException;
import br.com.zillius.api.exceptions.IllegalPathArgumentException;
import br.com.zillius.api.exceptions.IllegalQueryArgumentException;
import br.com.zillius.api.exceptions.InternalServerErrorException;
import br.com.zillius.api.exceptions.NotImplementedException;
import br.com.zillius.api.model.Conciliacao;
import br.com.zillius.api.model.ConciliacaoResponse;
import br.com.zillius.api.model.DrillDownType;
import br.com.zillius.api.model.Lancamento;
import br.com.zillius.api.model.LancamentoResponse;
import br.com.zillius.api.model.QueryFilterVO;
import br.com.zillius.api.model.ResourceType;
import br.com.zillius.api.resources.retornos.RetornosResource;
import java.util.Arrays;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Daniel
 */
@RequestScoped
public class DrillDownResource extends RoutingResource {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(DrillDownResource.class.getName());

    @Context
    private UriInfo uriInfo;

    /**
     * Contexto do recurso
     */
    @Context
    private ResourceContext resourceContext;

    @Inject
    private ConciliacaoDAO conciliacoesDAO;

    @Inject
    private LancamentoDAO lancamentosDAO;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJson(@DefaultValue("20") @QueryParam("limit") int limit,
            @DefaultValue("1") @QueryParam("offset") int offset) throws Exception {

        try {

            validate(limit, offset);
            
            if (getResourceType() == ResourceType.CONCILIACOES) {                
                return Response.ok(obterListaGenericaDeConciliacoes(limit, offset)).build();
            } else if (getResourceType() == ResourceType.LANCAMENTOS) {
                return Response.ok(obterListaGenericaDeLancamentos(limit, offset)).build();
            } else {
                logger.error("NotImplementedException :");
                throw new NotImplementedException("Method not implemented");
            }
        } catch (EntityNotFoundException ex) {
            logger.error("EntityNotFoundException :", ex);
            throw ex;
        } catch (IllegalArgumentException ex) {
            logger.error("IllegalArgumentException :", ex);
            throw new IllegalPathArgumentException(ex.getMessage());
        } catch (WebApplicationException ex) {
            logger.error("WebApplicationException :", ex);
            throw ex;
        } catch (Exception ex) {
            logger.error("Exception :", ex);
            //TODO: Mensagem genérica - distutir com o Marketing.
            throw new InternalServerErrorException("Ops! no donuts for you");
        }
    }

    @Path("filiais")
    public FiliaisResource getFiliais() {

        FiliaisResource filiaisResource = resourceContext.getResource(FiliaisResource.class);

        filiaisResource.setResourceType(getResourceType());
        filiaisResource.setDrillDownType(getTipoDrillDown());

        return filiaisResource;

    }

    private List<Conciliacao> obterConciliacoesDeRecebimentos(int limit, int offset) {
        
        
        List<Conciliacao> conciliacoes = conciliacoesDAO
                .obterConciliacoesDeRecebimentosGeral(new QueryFilterVO(getPfjCode(), getDataInicial(), getDataFinal(), getTipoDrillDown(), getResourceType(), limit, offset));

        if (conciliacoes.isEmpty()) {
            logger.error("No data has been found");
            throw new EntityNotFoundException("No data has been found");
        }
        return conciliacoes;
    }

    public List<Conciliacao> obterConciliacoesDeVendas(int limit, int offset) {
        List<Conciliacao> conciliacoes = conciliacoesDAO
                .obterConciliacoesDeVendasGeral(new QueryFilterVO(getPfjCode(), getDataInicial(), getDataFinal(), getTipoDrillDown(), getResourceType(), limit, offset));
        
        if (conciliacoes.isEmpty()) {
            logger.error("No data has been found");
            throw new EntityNotFoundException("No data has been found");
        }
        return conciliacoes;        
    }

    private ConciliacaoResponse obterListaGenericaDeConciliacoes(int limit, int offset) {

        ConciliacaoResponse conciliacaoResponse = new ConciliacaoResponse();

        if (getTipoDrillDown() == DrillDownType.RECEBIMENTOS) {

            conciliacaoResponse = getReconciliationResponseWithLinks(obterConciliacoesDeRecebimentos(limit, offset), "getRetornosDeRecebimentos");
            
        } else if (getTipoDrillDown() == DrillDownType.VENDAS) {

            conciliacaoResponse = getReconciliationResponseWithLinks(obterConciliacoesDeVendas(limit, offset), "getRetornosDeVendas");

        } else {
            logger.error("DrillDownType not implemented");
            throw new NotImplementedException("DrillDownType não implementado");
        }
        
        return conciliacaoResponse;
    }

    private LancamentoResponse obterListaGenericaDeLancamentos(int limit, int offset) {

        LancamentoResponse lancamentoResponse = new LancamentoResponse();

        if (getTipoDrillDown() == DrillDownType.RECEBIMENTOS) {

            lancamentoResponse = getEntriesResponseWithLinks(obterLancamentosDeRecebimentos(limit, offset), "getRetornosDeRecebimentos");

        } else if (getTipoDrillDown() == DrillDownType.VENDAS) {

            lancamentoResponse = getEntriesResponseWithLinks(obterLancamentosDeVendas(limit, offset), "getRetornosDeVendas");

        } else if (getTipoDrillDown() == DrillDownType.PREVISOES) {
            lancamentoResponse.setLancamentos(obterLancamentosDePrevisoes(limit, offset));
        }

        return lancamentoResponse;
    }

    private List<Lancamento> obterLancamentosDeRecebimentos(int limit, int offset) {
        
        List<Lancamento> lancamentos = lancamentosDAO
                .obterLancamentosDeRecebimentosGeral(new QueryFilterVO(getPfjCode(), getDataInicial(), getDataFinal(), getTipoDrillDown(), getResourceType(), limit, offset));

        if (lancamentos.isEmpty()) {
            logger.error("No data has been found");
            throw new EntityNotFoundException("No data has been found");
        }
        return lancamentos;
        
    }

    private List<Lancamento> obterLancamentosDeVendas(int limit, int offset) {
        
        List<Lancamento> lancamentos = lancamentosDAO
                .obterLancamentosDeVendasGeral(new QueryFilterVO(getPfjCode(), getDataInicial(), getDataFinal(), getTipoDrillDown(), getResourceType(), limit, offset));

        if (lancamentos.isEmpty()) {
            logger.error("No data has been found");
            throw new EntityNotFoundException("No data has been found");
        }
        return lancamentos;
    }

    private List<Lancamento> obterLancamentosDePrevisoes(int limit, int offset) {
        
        List<Lancamento> lancamentos = lancamentosDAO
                .obterLancamentosDePrevisoesGeral(new QueryFilterVO(getPfjCode(), getDataInicial(), getDataFinal(), getTipoDrillDown(), getResourceType(), limit, offset));

        if (lancamentos.isEmpty()) {
            logger.error("No data has been found");
            throw new EntityNotFoundException("No data has been found");
        }
        return lancamentos;
    }

    @Override
    public List<? extends BaseDestinationDAO> getDAOS() {
        return Arrays.asList(this.conciliacoesDAO, this.lancamentosDAO);
    }
}
