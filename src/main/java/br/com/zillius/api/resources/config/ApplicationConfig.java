package br.com.zillius.api.resources.config;

import br.com.zillius.api.resources.providers.DateParamProvider;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import java.util.Set;
import javax.ws.rs.ext.ParamConverterProvider;

/**
 *
 * @author a.accioly Configuração do Jersey
 */
@javax.ws.rs.ApplicationPath("api")
public class ApplicationConfig extends ResourceConfig {

    public ApplicationConfig() {
        final Set<Class<?>> resources = new java.util.LinkedHashSet<>();
        addRestResourceClasses(resources);
        
        resources.remove(br.com.zillius.api.resources.resolvers.MOXyJsonContextResolver.class);
        resources.remove(br.com.zillius.api.resources.providers.DateParamProvider.class);
        
        // Registrando filtros
        register(br.com.zillius.api.resources.filters.ApiFilter.class);
        register(br.com.zillius.api.resources.DrillDownResource.class);
        register(br.com.zillius.api.resources.FiliaisResource.class);
        
        //Registrando parser JSON
        register(br.com.zillius.api.resources.resolvers.MOXyJsonContextResolver.class);
        // Workaround para registar o parametro de data com maior prioridade do que o AggregatedProvider
        // Ver https://java.net/jira/browse/JERSEY-2572
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(DateParamProvider.class).to(ParamConverterProvider.class).ranked(10);
            }
        });
        
        registerClasses(resources);

    }

    /**
     * Do not modify addRestResourceClasses() method. It is automatically
     * populated with all resources defined in the project. If required, comment
     * out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(br.com.zillius.api.resources.ConciliacoesResource.class);
        resources.add(br.com.zillius.api.resources.DrillDownResource.class);
        resources.add(br.com.zillius.api.resources.FiliaisResource.class);
        resources.add(br.com.zillius.api.resources.LancamentosResource.class);
        resources.add(br.com.zillius.api.resources.ProcessoResource.class);
        resources.add(br.com.zillius.api.resources.clientes.ClientesResource.class);
        resources.add(br.com.zillius.api.resources.clientes.FiliaisResource.class);
        resources.add(br.com.zillius.api.resources.entradas.EntradasResource.class);
        resources.add(br.com.zillius.api.resources.providers.DateParamProvider.class);
        resources.add(br.com.zillius.api.resources.providers.EntityNotFoundMapper.class);
        resources.add(br.com.zillius.api.resources.resolvers.MOXyJsonContextResolver.class);
        resources.add(br.com.zillius.api.resources.retornos.RetornosResource.class);
    }

}
