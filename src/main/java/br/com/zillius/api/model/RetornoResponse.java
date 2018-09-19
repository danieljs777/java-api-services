/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.model;

import java.io.Serializable;
import java.net.URI;
import java.util.List;

/**
 *
 * @author Daniel
 */
public class RetornoResponse implements Serializable{

    private List<Retorno> retornos;
    private URI proximo;
    private URI anterior;
    
    public RetornoResponse() {
    }

    /**
     * @return the retornos
     */
    public List<Retorno> getRetornos() {
        return retornos;
    }

    /**
     * @param retornos the retornos to set
     */
    public void setRetornos(List<Retorno> retornos) {
        this.retornos = retornos;
    }

    /**
     * @return the proximo
     */
    public URI getProximo() {
        return proximo;
    }

    /**
     * @param proximo the proximo to set
     */
    public void setProximo(URI proximo) {
        this.proximo = proximo;
    }

    /**
     * @return the anterior
     */
    public URI getAnterior() {
        return anterior;
    }

    /**
     * @param anterior the anterior to set
     */
    public void setAnterior(URI anterior) {
        this.anterior = anterior;
    }

}

