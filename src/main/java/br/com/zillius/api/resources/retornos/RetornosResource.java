/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.resources.retornos;

import br.com.zillius.api.dao.BaseDestinationDAO;
import br.com.zillius.api.dao.RetornoDAO;
import br.com.zillius.api.exceptions.EntityNotFoundException;
import br.com.zillius.api.exceptions.IllegalQueryArgumentException;
import br.com.zillius.api.exceptions.InternalServerErrorException;
import br.com.zillius.api.model.Retorno;
import br.com.zillius.api.model.RetornoResponse;
import br.com.zillius.api.model.StatusConciliacao;
import br.com.zillius.api.model.TipoFiltroDeRetorno;
import br.com.zillius.api.model.TipoRetorno;
import br.com.zillius.api.resources.RoutingResource;
import java.util.Arrays;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Daniel
 */
@Path("/retornos")
@RequestScoped
public class RetornosResource extends RoutingResource {

    private static final Logger logger = LoggerFactory.getLogger(RetornosResource.class);

    @Inject
    private RetornoDAO retornoDAO;

    public RetornosResource() {

    }

    @Path("/recebimentos")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRetornosDeRecebimentos(
            @QueryParam("filialId") Integer filialId,
            @DefaultValue("TODOS") @QueryParam("statusConciliacao") StatusConciliacao statusConciliacao,
            @QueryParam("adquirente") String adquirente,
            @QueryParam("bandeira") String bandeira,
            @QueryParam("produto") String produto,
            @QueryParam("key") String key,
            @QueryParam("lancamentoId") Integer lancamentoId,
            @QueryParam("tipoRetorno") TipoFiltroDeRetorno tipoRetorno,
            @DefaultValue("20") @QueryParam("limit") int limit,
            @DefaultValue("1") @QueryParam("offset") int offset) throws Exception {

        try {

            validate(limit, offset);

            List<Retorno> listRetornos = retornoDAO.obterRetornosDeRecebimentos(getPfjCode(), getClienteId(), getDataInicial(),
                    getDataFinal(), filialId, statusConciliacao, adquirente, bandeira, produto, key, lancamentoId, tipoRetorno, limit, offset);

            if (listRetornos.isEmpty()) {
                logger.error("No data has been found");
                throw new EntityNotFoundException("No data has been found");
            }

            RetornoResponse retornoResponse = new RetornoResponse();
            retornoResponse.setRetornos(listRetornos);

            long quantidadeDeRegistros = retornoDAO.obterCountRetornos(TipoRetorno.RECEBIMENTOS, tipoRetorno, getPfjCode(), getClienteId(), getDataInicial(), getDataFinal(), filialId, statusConciliacao, adquirente, bandeira, produto, key, lancamentoId);

            setPages(quantidadeDeRegistros, offset, limit, "getRetornosDeRecebimentos", TipoRetorno.RECEBIMENTOS, retornoResponse);

            return Response.ok(retornoResponse).build();
        } catch (IllegalArgumentException ex) {
            logger.error("Illegal Query argument: {}", ex);
            throw new IllegalQueryArgumentException(ex.getMessage());
        } catch (WebApplicationException ex) {
            throw ex;
        } catch (Exception ex) {
            //TODO: Mensagem genérica - distutir com o Marketing.
            throw new InternalServerErrorException("Ops! no donuts for you");
        }
    }

    @Path("/vendas")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getRetornosDeVendas(
            @QueryParam("filialId") Integer filialId,
            @DefaultValue("TODOS") @QueryParam("statusConciliacao") StatusConciliacao statusConciliacao,
            @QueryParam("adquirente") String adquirente,
            @QueryParam("bandeira") String bandeira,
            @QueryParam("produto") String produto,
            @QueryParam("key") String key,
            @QueryParam("lancamentoId") Integer lancamentoId,
            @QueryParam("tipoRetorno") TipoFiltroDeRetorno tipoRetorno,
            @DefaultValue("20") @QueryParam("limit") int limit,
            @DefaultValue("1") @QueryParam("offset") int offset) throws Exception {

        try {
            validate(limit, offset);

            List<Retorno> listRetornos = retornoDAO.obterRetornosDeVendas(getPfjCode(), getClienteId(), getDataInicial(),
                    getDataFinal(), filialId, statusConciliacao, adquirente, bandeira, produto, key, lancamentoId, tipoRetorno, limit, offset);

            if (listRetornos.isEmpty()) {
                logger.error("No data has been found");
                throw new EntityNotFoundException("No data has been found");
            }

            RetornoResponse retornoResponse = new RetornoResponse();
            retornoResponse.setRetornos(listRetornos);

            long quantidadeDeRegistros = retornoDAO.obterCountRetornos(TipoRetorno.VENDAS, tipoRetorno, getPfjCode(), getClienteId(), getDataInicial(), getDataFinal(), filialId, statusConciliacao, adquirente, bandeira, produto, key, lancamentoId);

            setPages(quantidadeDeRegistros, offset, limit, "getRetornosDeVendas", TipoRetorno.VENDAS, retornoResponse);

            return Response.ok(retornoResponse).build();

        } catch (EntityNotFoundException ex) {
            throw ex;
        } catch (IllegalArgumentException ex) {
            logger.error("Illegal Query argument: {}", ex);
            throw new IllegalQueryArgumentException(ex.getMessage());
        } catch (WebApplicationException ex) {
            throw ex;
        } catch (Exception ex) {
            //TODO: Mensagem genérica - distutir com o Marketing.
            logger.error("Ops! no donuts for you");
            throw new InternalServerErrorException("Ops! no donuts for you");
        }
    }

    private void setPages(long quantidadeDeRegistros, int offset, int limit, String method, TipoRetorno tipoRetorno, RetornoResponse retornoResponse) {

        //Possui próxima página
        if (offset + limit <= quantidadeDeRegistros) {

            UriBuilder uriBuilder = getUriBuilder(RetornosResource.class, method);
            uriBuilder = setQueryParamsForLinking(uriBuilder);
            uriBuilder.queryParam("offset", Math.min(offset + limit, quantidadeDeRegistros));
            uriBuilder.queryParam("limit", limit);
            retornoResponse.setProximo(uriBuilder.build());
        }

        //Possui página anterior
        if (offset > 1) {
            UriBuilder uriBuilder = getUriBuilder(RetornosResource.class, method);
            uriBuilder = setQueryParamsForLinking(uriBuilder);
            uriBuilder.queryParam("offset", Math.max(offset - limit, 1));
            uriBuilder.queryParam("limit", limit);
            retornoResponse.setAnterior(uriBuilder.build());
        }
    }

    @Override
    public List<? extends BaseDestinationDAO> getDAOS() {
        return Arrays.asList(this.retornoDAO);
    }
}
