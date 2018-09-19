/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.model;

import br.com.zillius.api.dao.ConciliacaoDAOImpl;
import br.com.zillius.api.dao.queryobject.ViewProperties;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Daniel
 */
public enum ResourceType {
    LANCAMENTOS(buildEntriesProperties()),
    CONCILIACOES(buildConciliationProperties());
       
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ConciliacaoDAOImpl.class.getName());
    
    private static Map<DrillDownType, ViewProperties[]> buildConciliationProperties() {
        final EnumMap<DrillDownType, ViewProperties[]> temp;
        
        temp = new EnumMap<>(DrillDownType.class);
        
        temp.put(DrillDownType.VENDAS, new ViewProperties[] { 
           new ViewProperties( "CFR_CC_VENDAS_EMP_V", "BANDEIRA, ADQUIRENTE ", ""),
           new ViewProperties( "CFR_CC_VENDAS_FILIAL_V", "FILIALID, NOMEFILIAL, BANDEIRA, ADQUIRENTE, PRODUTO ", ""),
           new ViewProperties( "CFR_CC_VENDAS_FILIAL_V", "FILIALID, NOMEFILIAL, BANDEIRA, ADQUIRENTE, PRODUTO ", "AND FILIALID = ?")
        } );

        temp.put(DrillDownType.RECEBIMENTOS, new ViewProperties[] { 
           new ViewProperties( "CFR_CC_RECTOS_EMP_V", "BANDEIRA, ADQUIRENTE ", ""),
           new ViewProperties( "CFR_CC_RECTOS_FILIAL_V", "PFJCODIGO, NOMEFILIAL, FILIALCODIGO, FILIALID, CNPJ, BANDEIRA, ADQUIRENTE ", ""),
           new ViewProperties( "CFR_CC_RECTOS_FILIAL_V", "PFJCODIGO, NOMEFILIAL, FILIALCODIGO, FILIALID, CNPJ, BANDEIRA, ADQUIRENTE ", "AND FILIALID = ?")
        } );
        
        return Collections.unmodifiableMap(temp);   
        
    }
    
    private static Map<DrillDownType, ViewProperties[]> buildEntriesProperties() {
        final EnumMap<DrillDownType, ViewProperties[]> temp;
        
        temp = new EnumMap<>(DrillDownType.class);
        
        temp.put(DrillDownType.PREVISOES, new ViewProperties[] { 
           new ViewProperties( "CFR_CC_LANC_PREV_EMP_V", "BANCO, AGENCIA, CONTA, BANDEIRA, ADQUIRENTE, PRODUTO ", ""),
           new ViewProperties( "CFR_CC_LANC_PREV_FILIAL_V", "BANCO, AGENCIA, CONTA, CLIENTECODIGO, NOMEFILIAL, FILIALCODIGO, FILIALID, CNPJ, BANDEIRA, ADQUIRENTE, PRODUTO ", ""),
           new ViewProperties( "CFR_CC_LANC_PREV_FILIAL_V", "BANCO, AGENCIA, CONTA, CLIENTECODIGO, NOMEFILIAL, FILIALCODIGO, FILIALID, CNPJ, BANDEIRA, ADQUIRENTE, PRODUTO ", "AND FILIALID = ?")
        } );      

        temp.put(DrillDownType.VENDAS, new ViewProperties[] { 
           new ViewProperties( "CFR_CC_LANC_VENDAS_EMP_V", "BANDEIRA, ADQUIRENTE, PRODUTO ", ""),
           new ViewProperties( "CFR_CC_LANC_VENDAS_FILIAL_V", "CLIENTECODIGO, NOMEFILIAL, FILIALCODIGO, FILIALID, CNPJ, BANDEIRA, ADQUIRENTE, PRODUTO ", ""),
           new ViewProperties( "CFR_CC_LANC_VENDAS_FILIAL_V", "CLIENTECODIGO, NOMEFILIAL, FILIALCODIGO, FILIALID, CNPJ, BANDEIRA, ADQUIRENTE, PRODUTO ", "AND FILIALID = ?")
        } );

        temp.put(DrillDownType.RECEBIMENTOS, new ViewProperties[] { 
           new ViewProperties( "CFR_CC_LANC_RECTOS_EMP_V", "BANCO, AGENCIA, CONTA, BANDEIRA, ADQUIRENTE, PRODUTO ", ""),
           new ViewProperties( "CFR_CC_LANC_RECTOS_FILIAL_V", "BANCO, AGENCIA, CONTA, PFJCODIGO, NOMEFILIAL, FILIALCODIGO, FILIALID, CNPJ, BANDEIRA, ADQUIRENTE, PRODUTO ", ""),
           new ViewProperties( "CFR_CC_LANC_RECTOS_FILIAL_V", "BANCO, AGENCIA, CONTA, PFJCODIGO, NOMEFILIAL, FILIALCODIGO, FILIALID, CNPJ, BANDEIRA, ADQUIRENTE, PRODUTO ", "AND FILIALID = ?")
        } );
        
        return Collections.unmodifiableMap(temp);   
        
    }    

    private final Map<DrillDownType, ViewProperties[]> views;

    private ResourceType(Map<DrillDownType, ViewProperties[]> views) {
        this.views = views;
    }        

    public ViewProperties getView(DrillDownType tipo, int nivel) {
        return views.get(tipo)[nivel];
    }

}

    
