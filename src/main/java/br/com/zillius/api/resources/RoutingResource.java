/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.resources;

import br.com.zillius.api.dao.BaseDestinationDAO;
import br.com.zillius.api.exceptions.BusinessException;
import br.com.zillius.api.exceptions.IllegalQueryArgumentException;
import br.com.zillius.api.model.APIConstants;
import br.com.zillius.api.model.Conciliacao;
import br.com.zillius.api.model.ConciliacaoResponse;
import br.com.zillius.api.model.DrillDownType;
import br.com.zillius.api.model.Lancamento;
import br.com.zillius.api.model.LancamentoResponse;
import br.com.zillius.api.model.ResourceType;
import br.com.zillius.api.model.StatusConciliacao;
import br.com.zillius.api.model.UDTVendasResponse;
import br.com.zillius.api.resources.retornos.RetornosResource;
import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.naming.NamingException;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * @author Daniel
 */
@RequestScoped
public abstract class RoutingResource {

    private String schema;
    private String connectionPool;
    private DrillDownType tipoDrillDown;
    private String tenantId;
    private ResourceType resourceType;

    @Context
    protected UriInfo uriInfo;

    @HeaderParam("App-Token")
    private String appToken;

    @HeaderParam("Masking-Host")
    private String host;
    
    @HeaderParam("Masking-ContextPath")
    private String contextPath;

    @HeaderParam("Masking-Port")
    private Integer port;

    @HeaderParam("Masking-Scheme")
    private String scheme;

    @QueryParam("clienteId")
    private String clienteId;

    @QueryParam("dataInicial")
    private Date dataInicial;

    @QueryParam("dataFinal")
    private Date dataFinal;

    public String getSchema() {
        return schema;
    }

    public String getConnectionPool() {
        return connectionPool;
    }

    public String getPfjCode() {
        if(clienteId == null)
            throw new BusinessException("Query Parameter clienteId is mandatory!");
        
        return getPfjCode(clienteId);
    }

    public String getPfjCode(String clienteId) {
        return tenantId + "." + clienteId;
    }

    public String getBaseUrl() {
        return "http://" + getHost() + "/api-portal/api";
    }

    @SuppressWarnings("unchecked")
    public List<? extends BaseDestinationDAO> getDAOS() {
        //noinspection unchecked
        return Collections.EMPTY_LIST;
    }

    public UriBuilder getUriBuilder(Class<?> resource, String method) {

        // UriBuilder uriBuilder = UriBuilder.fromMethod(resource, method);
        UriBuilder baseUri = uriInfo.getBaseUriBuilder();

        if (host != null) {
            baseUri.host(host);
        }

        if(contextPath != null){
              baseUri.replacePath(contextPath);
        }
        
        baseUri.path(resource);
        baseUri.path(resource, method);

        if (getScheme() != null) {
            baseUri.scheme(getScheme());
        }

        if (port != null) {
            baseUri.port(port);
        }

        return baseUri;
    }

    public UriBuilder setQueryParam(UriBuilder uriBuilder, String paramName) {

        if (uriInfo.getQueryParameters().containsKey(paramName)) {
            uriBuilder = uriBuilder.queryParam(paramName, uriInfo.getQueryParameters().getFirst(paramName));
        }

        return uriBuilder;
    }

    protected UriBuilder setQueryParamsForLinking(UriBuilder uriBuilder) {

        uriBuilder = setQueryParam(uriBuilder, "dataInicial");
        uriBuilder = setQueryParam(uriBuilder, "dataFinal");
        uriBuilder = setQueryParam(uriBuilder, "clienteId");
        uriBuilder = setQueryParam(uriBuilder, "filialId");
        uriBuilder = setQueryParam(uriBuilder, "statusConciliacao");
        uriBuilder = setQueryParam(uriBuilder, "adquirente");
        uriBuilder = setQueryParam(uriBuilder, "bandeira");
        uriBuilder = setQueryParam(uriBuilder, "produto");
        uriBuilder = setQueryParam(uriBuilder, "key");
        uriBuilder = setQueryParam(uriBuilder, "lancamentoId");

        return uriBuilder;
    }

    protected ConciliacaoResponse getReconciliationResponseWithLinks(List<Conciliacao> origem, String method) {

        List<Conciliacao> destino = new ArrayList<>();

        for (Conciliacao conciliacao : origem) {
            conciliacao.addDetalhe("detalhes", getDrillDownURI(method, conciliacao.getFilialId(), conciliacao.getBandeira(), conciliacao.getAdquirente(), StatusConciliacao.TODOS));

            if (conciliacao.getDiferenca() != null && BigDecimal.ZERO.compareTo(conciliacao.getDiferenca()) != 0) {
                conciliacao.addDetalhe("pendentes", getDrillDownURI(method, conciliacao.getFilialId(), conciliacao.getBandeira(), conciliacao.getAdquirente(), StatusConciliacao.PENDENTE));
            }

            destino.add(conciliacao);
        }

        ConciliacaoResponse conciliacaoResponse = new ConciliacaoResponse();
        conciliacaoResponse.setConciliacoes(destino);

        return conciliacaoResponse;
    }

    protected List<UDTVendasResponse> buildSalesReturnLink(List<UDTVendasResponse> listVendaResponse, String method) {
        for (UDTVendasResponse venda : listVendaResponse) {
            if (venda.getCodigoHttp() == 201) {
                UriBuilder uri = getUriBuilder(RetornosResource.class, method).queryParam("lancamentoId", venda.getLancamentoId());
                venda.setLocation(uri.build());
            }
        }

        return listVendaResponse;
    }

    protected LancamentoResponse getEntriesResponseWithLinks(List<Lancamento> origem, String method) {
        
        List<Lancamento> destino = new ArrayList<>();

        for (Lancamento lancamento : origem) {
            lancamento.addDetalhe("detalhes", getDrillDownURI(method, lancamento.getFilialId(), lancamento.getBandeira(), lancamento.getAdquirente(), ResourceType.LANCAMENTOS));

            destino.add(lancamento);
        }

        LancamentoResponse lancamentoResponse = new LancamentoResponse();
        lancamentoResponse.setLancamentos(destino);

        return lancamentoResponse;
    }
    
    protected URI getDrillDownURI(String method, Integer filialId, String bandeira, String adquirente, ResourceType tipoServico) {

        UriBuilder uriBuilder = getUriBuilder(RetornosResource.class, method);

        uriBuilder = setQueryParam(uriBuilder, "dataInicial");
        uriBuilder = setQueryParam(uriBuilder, "dataFinal");
        uriBuilder = setQueryParam(uriBuilder, "clienteId");

        uriBuilder.queryParam("tipoRetorno", tipoServico);

        if (filialId != null) {
            uriBuilder.queryParam("filialId", filialId);
        }

        if (bandeira != null && !bandeira.isEmpty()) {
            uriBuilder.queryParam("bandeira", bandeira);
        }

        if (adquirente != null && !adquirente.isEmpty()) {
            uriBuilder.queryParam("adquirente", adquirente);
        }

        uriBuilder.queryParam("limit", 20);
        uriBuilder.queryParam("offset", 1);

        return uriBuilder.build();
    }    

    protected URI getDrillDownURI(String method, Integer filialId, String bandeira, String adquirente, StatusConciliacao statusConciliacao) {

        UriBuilder uriBuilder = getUriBuilder(RetornosResource.class, method);

        uriBuilder = setQueryParam(uriBuilder, "dataInicial");
        uriBuilder = setQueryParam(uriBuilder, "dataFinal");
        uriBuilder = setQueryParam(uriBuilder, "clienteId");

        uriBuilder.queryParam("statusConciliacao", statusConciliacao);

        if (filialId != null) {
            uriBuilder.queryParam("filialId", filialId);
        }

        if (bandeira != null && !bandeira.isEmpty()) {
            uriBuilder.queryParam("bandeira", bandeira);
        }

        if (adquirente != null && !adquirente.isEmpty()) {
            uriBuilder.queryParam("adquirente", adquirente);
        }

        uriBuilder.queryParam("limit", 20);
        uriBuilder.queryParam("offset", 1);

        return uriBuilder.build();
    }

    public void configureDAOS() {
        try {
            for (BaseDestinationDAO dao : getDAOS()) {

                dao.setConnectionPool(getConnectionPool());
                dao.setSchema(getSchema());
            }
        } catch (NamingException ex) {
            Logger.getLogger(RoutingResource.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalStateException("Can't configure DAOs", ex);
        }
    }

    public String getAppToken() {
        return appToken;
    }

    public String getClienteId() {
        return clienteId;
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setDrillDownType(DrillDownType tipoDrillDown) {
        this.tipoDrillDown = tipoDrillDown;
    }

    public DrillDownType getTipoDrillDown() {
        return tipoDrillDown;
    }

    public void setConnectionPool(String connectionPool) {
        this.connectionPool = connectionPool;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public void setAppToken(String appToken) {
        this.appToken = appToken;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @param host the host to set
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * @return the port
     */
    public Integer getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(Integer port) {
        this.port = port;
    }

    /**
     * @return the scheme
     */
    public String getScheme() {
        return scheme;
    }

    /**
     * @param scheme the scheme to set
     */
    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    /**
     * @return the contextPath
     */
    public String getContextPath() {
        return contextPath;
    }

    /**
     * @param contextPath the contextPath to set
     */
    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }
    
    protected void validatePages(int limit, int offset) {
        
        if (limit < 1 || limit > APIConstants.MAX_SIZE_OF_A_RESULTSET) {
            throw new BusinessException("limit must be an integer between 1 and " + APIConstants.MAX_SIZE_OF_A_RESULTSET);
        }

        if (offset < 1) {
            throw new IllegalQueryArgumentException("out of range, offset must be greater than or equal to 1");
        }
    }    
    
    protected void validate(int limit, int offset) {

        if (limit < 1 || limit > APIConstants.MAX_SIZE_OF_A_RESULTSET) {
            throw new BusinessException("limit must be an integer between 1 and " + APIConstants.MAX_SIZE_OF_A_RESULTSET);
        }

        if (offset < 1) {
            throw new IllegalQueryArgumentException("out of range, offset must be greater than or equal to 1");
        }

        Calendar today = Calendar.getInstance();
        Calendar inicio = Calendar.getInstance();
        Calendar fim = Calendar.getInstance();

        today.set(Calendar.HOUR_OF_DAY, 23);
        today.set(Calendar.MINUTE, 59);
        today.set(Calendar.SECOND, 59);
        today.set(Calendar.MILLISECOND, 999);
        
        if(dataFinal != null && dataInicial != null)
        {

            inicio.setTime(getDataInicial());
            fim.setTime(getDataFinal());

            if (inicio.after(fim)) {
                throw new BusinessException("Initial date is greater than final date");
            }

            if (inicio.after(today) || fim.after(today)) {
                throw new IllegalQueryArgumentException("Can't retrieve data from the future");
            }

            Calendar businessDateValidation = Calendar.getInstance();

            businessDateValidation.setTime(getDataInicial());

            businessDateValidation.add(Calendar.DAY_OF_MONTH, APIConstants.MAX_BUSINESS_DAYS_FOR_A_RESULTSET);

            long diffTime = getDataFinal().getTime() - getDataInicial().getTime();
            long diffDays = diffTime / (1000 * 60 * 60 * 24);

            if (diffDays > APIConstants.MAX_INTERVAL_OF_DAYS) {
                throw new BusinessException("Date range limited to " + APIConstants.MAX_INTERVAL_OF_DAYS + " days");
            }            
        }

    }
    
}
