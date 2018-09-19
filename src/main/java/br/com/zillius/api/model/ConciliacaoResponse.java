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
public class ConciliacaoResponse implements Serializable {

    private List<Conciliacao> conciliacoes;

    public ConciliacaoResponse() {
    }

    /**
     * @return the conciliacoes
     */
    public List<Conciliacao> getConciliacoes() {
        return conciliacoes;
    }

    /**
     * @param conciliacoes the conciliacoes to set
     */
    public void setConciliacoes(List<Conciliacao> conciliacoes) {
        this.conciliacoes = conciliacoes;
    }

}
