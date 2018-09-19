/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Daniel
 */
public class LancamentoResponse implements Serializable{

    private List<Lancamento> lancamentos;
    
    public LancamentoResponse() {
    }

    /**
     * @return the lancamentos
     */
    public List<Lancamento> getLancamentos() {
        return lancamentos;
    }

    /**
     * @param lancamentos the lancamentos to set
     */
    public void setLancamentos(List<Lancamento> lancamentos) {
        this.lancamentos = lancamentos;
    }


}
