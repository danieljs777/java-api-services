/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.zillius.api.model;

import java.io.Serializable;
import java.net.URI;

/**
 *
 * @author Daniel Jordão
 */
public class UDTVendasResponse implements Serializable {

    private Integer codigoHttp; // -> p_http_codigo NUMBER
    private Integer codigoResultado; // -> p_codigo_retorno
    private String descricao; // -> p_retorno
    private Integer lancamentoId;  
    private URI location; //-> http://localhost:8080/api-mock/api/retornos/vendas?lancamentoId = lancamentoId

    public Integer getCodigoHttp() {
        return codigoHttp;
    }

    public void setCodigoHttp(Integer codigoHttp) {
        this.codigoHttp = codigoHttp;
    }

    public Integer getCodigoResultado() {
        return codigoResultado;
    }

    public void setCodigoResultado(Integer codigoResultado) {
        this.codigoResultado = codigoResultado;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getLancamentoId() {
        return lancamentoId;
    }

    public void setLancamentoId(Integer lancamentoId) {
        this.lancamentoId = lancamentoId;
    }

    public URI getLocation() {
        return location;
    }

    public void setLocation(URI location) {
        this.location = location;
    }


}
