/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.resources;

import br.com.zillius.api.dao.BaseDestinationDAO;
import br.com.zillius.api.dao.ConciliacaoDAO;
import br.com.zillius.api.dao.LancamentoDAO;
import br.com.zillius.api.exceptions.BusinessException;
import br.com.zillius.api.exceptions.EntityNotFoundException;
import br.com.zillius.api.exceptions.IllegalPathArgumentException;
import br.com.zillius.api.exceptions.IllegalQueryArgumentException;
import br.com.zillius.api.exceptions.InternalServerErrorException;
import br.com.zillius.api.exceptions.NotImplementedException;
import br.com.zillius.api.model.APIConstants;
import br.com.zillius.api.model.Conciliacao;
import br.com.zillius.api.model.ConciliacaoResponse;
import br.com.zillius.api.model.DrillDownType;
import br.com.zillius.api.model.Lancamento;
import br.com.zillius.api.model.LancamentoResponse;
import br.com.zillius.api.model.QueryCustomFilterVO;
import br.com.zillius.api.model.QueryFilterVO;
import br.com.zillius.api.model.ResourceType;
import java.util.Arrays;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Daniel
 */
@RequestScoped
public class FiliaisResource extends RoutingResource {

    private static final Logger logger = LoggerFactory.getLogger(FiliaisResource.class.getName());

    private String id;

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

            validatePages(limit, offset);
            
            if (getResourceType() == ResourceType.CONCILIACOES) {
                return Response.ok(obterListaGenericaDeConciliacoesDeFiliais(
                        new QueryFilterVO(getPfjCode(), getDataInicial(), getDataFinal(), getTipoDrillDown(), getResourceType(), limit, offset))
                ).build();
            } else if (getResourceType() == ResourceType.LANCAMENTOS) {
                return Response.ok(obterListaGenericaDeLancamentosDeFiliais(
                        new QueryFilterVO(getPfjCode(), getDataInicial(), getDataFinal(), getTipoDrillDown(), getResourceType(), limit, offset)
                )).build();
            } else {
                throw new NotImplementedException("Method not implemented");
            }
        } catch (EntityNotFoundException ex) {
            throw ex;
        } catch (IllegalArgumentException ex) {
            logger.error("Illegal path argument clienteId: {}", ex);
            throw new IllegalPathArgumentException(ex.getMessage());
        } catch (WebApplicationException ex) {
            throw ex;
        } catch (Exception ex) {
            //TODO: Mensagem genérica - distutir com o Marketing.
            logger.error("Ops! no donuts for you {}", ex);
            throw new InternalServerErrorException("Ops! no donuts for you");
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFilial(@DefaultValue("20") @QueryParam("limit") int limit,
            @DefaultValue("1") @QueryParam("offset") int offset,
            @NotNull @PathParam("id") Integer id) throws Exception {

        this.id = id.toString();

        validatePages(limit, offset);
        
        try {
            if (getResourceType() == ResourceType.CONCILIACOES) {
                return Response.ok(obterListaGenericaDeConciliacoesDeFilial(
                        new QueryCustomFilterVO(getPfjCode(), getDataInicial(), getDataFinal(), getTipoDrillDown(), getResourceType(), limit, offset, id))
                ).build();
            } else if (getResourceType() == ResourceType.LANCAMENTOS) {
                return Response.ok(obterListaGenericaDeLancamentosDeFilial(
                    new QueryCustomFilterVO(getPfjCode(), getDataInicial(), getDataFinal(), getTipoDrillDown(), getResourceType(), limit, offset, id))
                ).build();
            } else {
                throw new NotImplementedException("Method not implemented");
            }
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

    
    // <editor-fold defaultstate="collapsed" desc="Conciliacoes">

    private ConciliacaoResponse obterListaGenericaDeConciliacoesDeFiliais(QueryFilterVO queryFilters) {

        ConciliacaoResponse conciliacaoResponse = new ConciliacaoResponse();

        if (getTipoDrillDown() == DrillDownType.RECEBIMENTOS) {

            conciliacaoResponse = getReconciliationResponseWithLinks(obterConciliacoesDeRecebimentosDeFiliais(queryFilters), "getRetornosDeRecebimentos");

        } else if (getTipoDrillDown() == DrillDownType.VENDAS) {

            conciliacaoResponse = getReconciliationResponseWithLinks(obterConciliacoesDeVendasDeFiliais(queryFilters), "getRetornosDeVendas");

        }

        return conciliacaoResponse;
    }
    
    private ConciliacaoResponse obterListaGenericaDeConciliacoesDeFilial(QueryCustomFilterVO queryFilters) {

        ConciliacaoResponse conciliacaoResponse = new ConciliacaoResponse();

        if (getTipoDrillDown() == DrillDownType.RECEBIMENTOS) {

            conciliacaoResponse = getReconciliationResponseWithLinks(obterConciliacoesDeRecebimentosDeFilial(queryFilters), "getRetornosDeRecebimentos");

        } else if (getTipoDrillDown() == DrillDownType.VENDAS) {

            conciliacaoResponse = getReconciliationResponseWithLinks(obterConciliacoesDeVendasDeFilial(queryFilters), "getRetornosDeVendas");

        }

        return conciliacaoResponse;
    }        

    private List<Conciliacao> obterConciliacoesDeRecebimentosDeFilial(QueryCustomFilterVO queryFilters) {

        List<Conciliacao> conciliacoes = conciliacoesDAO.obterConciliacoesDeRecebimentosPorFilial(queryFilters);

        if (conciliacoes.isEmpty()) {
            throw new EntityNotFoundException("No data has been found");
        }

        return conciliacoes;
    }

    private List<Conciliacao> obterConciliacoesDeVendasDeFilial(QueryCustomFilterVO queryFilters) {

        List<Conciliacao> conciliacoes = conciliacoesDAO.obterConciliacoesDeVendasPorFilial(queryFilters);
        
        if (conciliacoes.isEmpty()) {
            throw new EntityNotFoundException("No data has been found");
        }

        return conciliacoes;        
    }
    
    private List<Conciliacao> obterConciliacoesDeRecebimentosDeFiliais(QueryFilterVO queryFilters) {

        List<Conciliacao> conciliacoes = conciliacoesDAO.obterConciliacoesDeRecebimentosFiliais(queryFilters);

        if (conciliacoes.isEmpty()) {
            throw new EntityNotFoundException("No data has been found");
        }

        return conciliacoes;
    }

    private List<Conciliacao> obterConciliacoesDeVendasDeFiliais(QueryFilterVO queryFilters) {
        List<Conciliacao> conciliacoes =  conciliacoesDAO.obterConciliacoesDeVendasFiliais(queryFilters);
        
        if (conciliacoes.isEmpty()) {
            throw new EntityNotFoundException("No data has been found");
        }

        return conciliacoes;        
    }
    
    // </editor-fold>    
    
    // <editor-fold defaultstate="collapsed" desc="Lancamentos">
    
    private LancamentoResponse obterListaGenericaDeLancamentosDeFiliais(QueryFilterVO filters) {

        LancamentoResponse lancamentoResponse = new LancamentoResponse();

        if (getTipoDrillDown() == DrillDownType.RECEBIMENTOS) {
            lancamentoResponse = getEntriesResponseWithLinks(obterLancamentosDeRecebimentosDeFiliais(filters), "getRetornosDeRecebimentos");
        } else if (getTipoDrillDown() == DrillDownType.VENDAS) {
            lancamentoResponse = getEntriesResponseWithLinks(obterLancamentosDeVendasDeFiliais(filters), "getRetornosDeVendas");
        } else if (getTipoDrillDown() == DrillDownType.PREVISOES) {
            lancamentoResponse.setLancamentos(obterLancamentosDePrevisoesDeFiliais(filters));
        }

        return lancamentoResponse;
    }    

    private LancamentoResponse obterListaGenericaDeLancamentosDeFilial(QueryCustomFilterVO filters) {

        LancamentoResponse lancamentoResponse = new LancamentoResponse();

        if (getTipoDrillDown() == DrillDownType.RECEBIMENTOS) {
            lancamentoResponse = getEntriesResponseWithLinks(obterLancamentosDeRecebimentosDeFilial(filters), "getRetornosDeRecebimentos");

        } else if (getTipoDrillDown() == DrillDownType.VENDAS) {
            lancamentoResponse = getEntriesResponseWithLinks(obterLancamentosDeVendasDeFilial(filters), "getRetornosDeVendas");

        } else if (getTipoDrillDown() == DrillDownType.PREVISOES) {
            lancamentoResponse = getEntriesResponseWithLinks(obterLancamentosDePrevisoesDeFilial(filters), "getRetornosDeVendas");

        }        

        return lancamentoResponse;
    }            
    
    private List<Lancamento> obterLancamentosDeRecebimentosDeFiliais(QueryFilterVO filters) {

        List<Lancamento> lancamentos = lancamentosDAO.obterLancamentosDeRecebimentosFiliais(filters);

        if (lancamentos.isEmpty()) {
            throw new EntityNotFoundException("No data has been found");
        }

        return lancamentos;        
    }           

    private List<Lancamento> obterLancamentosDeVendasDeFiliais(QueryFilterVO filters) {

        return lancamentosDAO.obterLancamentosDeVendasFiliais(filters);
    }

    private List<Lancamento> obterLancamentosDePrevisoesDeFiliais(QueryFilterVO filters)  {

        return lancamentosDAO.obterLancamentosDePrevisoesFiliais(filters);
    }        

    private List<Lancamento> obterLancamentosDeVendasDeFilial(QueryCustomFilterVO filters)  {

        List<Lancamento> lancamentos = lancamentosDAO.obterLancamentosDeVendasPorFilial(filters);

        if (lancamentos.isEmpty()) {
            throw new EntityNotFoundException("No data has been found");
        }

        return lancamentos;        
    } 
    
    private List<Lancamento> obterLancamentosDePrevisoesDeFilial(QueryCustomFilterVO filters) {

        return lancamentosDAO.obterLancamentosDePrevisoesPorFilial(filters);
    }
    
    private List<Lancamento> obterLancamentosDeRecebimentosDeFilial(QueryCustomFilterVO filters) {

        List<Lancamento> lancamentos = lancamentosDAO.obterLancamentosDeRecebimentosPorFilial(filters);

        if (lancamentos.isEmpty()) {
            throw new EntityNotFoundException("No data has been found");
        }

        return lancamentos;
    }    

    // </editor-fold>    

    @Override
    public List<? extends BaseDestinationDAO> getDAOS() {
        return Arrays.asList(this.conciliacoesDAO, this.lancamentosDAO);
    }
}
