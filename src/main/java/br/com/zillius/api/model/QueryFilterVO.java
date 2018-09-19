/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.model;

import java.util.Date;

/**
 *
 * @author Daniel
 */
public class QueryFilterVO {
    
    public String pfjCodigo;
    public Date dataInicial;
    public Date dataFinal;

    public DrillDownType tipoDrillDown;
    public ResourceType tipoResource;
    
    public int limit;
    public int offset;    

    public QueryFilterVO(String pfjCodigo, Date dataInicial, Date dataFinal, DrillDownType tipoDrillDown, ResourceType tipoResource, int limit, int offset) {
        this.pfjCodigo = pfjCodigo;
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
        this.tipoDrillDown = tipoDrillDown;
        this.tipoResource = tipoResource;
        this.limit = limit;
        this.offset = offset;
    }

    public QueryFilterVO(DrillDownType tipoDrillDown, ResourceType tipoResource, int limit, int offset) {
        this.limit = limit;
        this.offset = offset;        
        this.tipoDrillDown = tipoDrillDown;
        this.tipoResource = tipoResource;        
    }
    
    
    
    
}
