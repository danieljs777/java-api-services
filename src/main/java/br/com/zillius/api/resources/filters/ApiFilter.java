/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.resources.filters;

import br.com.zillius.api.dao.SchemaInfoDAO;
import br.com.zillius.api.model.APIConstants;
import br.com.zillius.api.model.DrillDownType;
import br.com.zillius.api.model.ResourceType;
import br.com.zillius.api.model.SchemaInfo;
import br.com.zillius.api.resources.RoutingResource;
import br.com.zillius.api.resources.providers.DateParamProvider;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Daniel
 */
@RequestScoped
public class ApiFilter implements ContainerRequestFilter {

    @Inject
    private SchemaInfoDAO schemaInfoDAO;

    private static final Logger logger = Logger.getLogger(ApiFilter.class.getName());

    @Override
    public void filter(ContainerRequestContext requestContext)
            throws IOException {

        final UriInfo uriInfo = requestContext.getUriInfo();

        for (Object o : uriInfo.getMatchedResources()) {
            if (o instanceof RoutingResource) {

                final RoutingResource resource = (RoutingResource) o;

                final MultivaluedMap<String, String> queryParameters = uriInfo.getQueryParameters();

                final String appToken = requestContext.getHeaders().getFirst("App-Token");

                SchemaInfo schemaInfo = schemaInfoDAO.obterSchemaInfoPor(appToken);

                resource.setConnectionPool(schemaInfo.getConnectionPool());
                resource.setTenantId(schemaInfo.getTenantId());
                resource.setSchema(schemaInfo.getUserName());

                resource.setAppToken(appToken);

                //TODO: alimentar estes parametros via Gateway.
                resource.setHost("api.concildesenvolvedores.com.br");
                resource.setPort(80);
                resource.setScheme("http");
                resource.setContextPath("/concilcard/v1");

                if (requestContext.getMethod().equals("GET")) {
                    resource.setClienteId(queryParameters.getFirst("clienteId"));
                }

                Calendar initialDate = null;
                Calendar finalDate = null;

                final DateFormat dateFormat = DateParamProvider.FORMATTER.get();
                try {
                    if (queryParameters.containsKey("dataInicial")) {

                        Date dataInicial = dateFormat.parse(queryParameters.getFirst("dataInicial"));

                        resource.setDataInicial(dataInicial);

                        initialDate = Calendar.getInstance();
                        initialDate.setTime(dataInicial);
                    }

                    if (queryParameters.containsKey("dataFinal")) {

                        Date dataFinal = dateFormat.parse(queryParameters.getFirst("dataFinal"));
                        resource.setDataFinal(dataFinal);

                        finalDate = Calendar.getInstance();
                        finalDate.setTime(dataFinal);
                    }

                    if(!queryParameters.containsKey("lancamentoId")) {
                        if (initialDate == null) {

                            if (finalDate == null) {
                                finalDate = Calendar.getInstance();

                                initialDate = Calendar.getInstance();
                                initialDate.add(Calendar.DAY_OF_MONTH, -APIConstants.MAX_BUSINESS_DAYS_FOR_A_RESULTSET);
                            } else {
                                initialDate = Calendar.getInstance();

                                initialDate.setTime(finalDate.getTime());
                                initialDate.add(Calendar.DAY_OF_MONTH, -APIConstants.MAX_BUSINESS_DAYS_FOR_A_RESULTSET);
                            }
                        } else if (finalDate == null) {

                            finalDate = Calendar.getInstance();

                            finalDate.setTime(initialDate.getTime());
                            finalDate.add(Calendar.DAY_OF_MONTH, APIConstants.MAX_BUSINESS_DAYS_FOR_A_RESULTSET);

                            Calendar today = Calendar.getInstance();

                            if (finalDate.after(today)) {
                                finalDate.setTime(today.getTime());
                            }

                        }

                        initialDate.set(Calendar.HOUR_OF_DAY, 0);
                        initialDate.set(Calendar.MINUTE, 0);
                        initialDate.set(Calendar.SECOND, 0);
                        initialDate.set(Calendar.MILLISECOND, 0);

                        finalDate.set(Calendar.HOUR_OF_DAY, 23);
                        finalDate.set(Calendar.MINUTE, 59);
                        finalDate.set(Calendar.SECOND, 59);
                        finalDate.set(Calendar.MILLISECOND, 999);

                        logger.info(initialDate.getTime().toString());
                        logger.info(finalDate.getTime().toString());

                        resource.setDataInicial(initialDate.getTime());
                        resource.setDataFinal(finalDate.getTime());                        
                    }


                } catch (ParseException e) {
                    throw new IllegalArgumentException("Can't parse date", e);
                }

                /*
                 resource.setConnectionPool("cfr");
                 resource.setTenantId("25.1");
                 resource.setSchema("CFR_1000");
                 */

                resource.configureDAOS();
            }
        }
    }
}
