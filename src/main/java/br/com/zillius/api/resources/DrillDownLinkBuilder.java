/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.resources;

import br.com.zillius.api.exceptions.InternalServerErrorException;
import br.com.zillius.api.model.Conciliacao;
import br.com.zillius.api.model.DrillDownType;
import br.com.zillius.api.model.Lancamento;
import java.net.URI;
import java.util.List;

/**
 *
 * @author Daniel
 */
public class DrillDownLinkBuilder {

    private DrillDownType drillDownType;

    private final String SALES_URL = "getRetornosDeVendas";
    private final String ENTRIES_URL = "getRetornosDeRecebimentos";

    public DrillDownLinkBuilder(DrillDownType drillDownType) {
        this.drillDownType = drillDownType;
    }

    // método que percorre o response e adiciona links
   
}
