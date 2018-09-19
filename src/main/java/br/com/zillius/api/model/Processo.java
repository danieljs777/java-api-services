/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Daniel
 */
public class Processo implements Serializable{
    Integer requestId;
    String descricao;
    String faseCodigo;
    String statusCodigo;
    Date dataInicio;
    Date dataFim;
    String requestFases;
    String requestStatus;

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getFaseCodigo() {
        return faseCodigo;
    }

    public void setFaseCodigo(String faseCodigo) {
        this.faseCodigo = faseCodigo;
    }

    public String getStatusCodigo() {
        return statusCodigo;
    }

    public void setStatusCodigo(String statusCodigo) {
        this.statusCodigo = statusCodigo;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public String getRequestFases() {
        return requestFases;
    }

    public void setRequestFases(String requestFases) {
        this.requestFases = requestFases;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }
        
    
}
