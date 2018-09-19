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
public class QueryCustomFilterVO  extends QueryFilterVO {

    public int filialId;

    public QueryCustomFilterVO(String pfjCodigo, Date dataInicial, Date dataFinal, DrillDownType tipoDrillDown, ResourceType tipoResource, int limit, int offset, int filialId) {
        super(pfjCodigo, dataInicial, dataFinal, tipoDrillDown, tipoResource, limit, offset);
        this.filialId = filialId;
    }
    
    public QueryCustomFilterVO(DrillDownType tipoDrillDown, ResourceType tipoResource, int limit, int offset, int filialId) {
        super(tipoDrillDown, tipoResource, limit, offset);
        this.filialId = filialId;
    }
    
}
