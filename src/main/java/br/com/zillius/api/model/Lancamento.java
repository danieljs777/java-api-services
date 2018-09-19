/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;
import javax.ws.rs.core.Link;

/**
 *
 * @author Daniel POJO de Lançamento para transitar entre recursos da API e DAOs
 */

public class Lancamento implements Serializable {

    private String adquirente;
    private String bandeira;
    private String produto;
    private BigDecimal valorBruto;
    private BigDecimal valorLiquido;
    private Integer quantidadeLanctosContra;
    private Integer quantidadeDeLancamentos;
    private String banco;
    private String agencia;
    private String contaBancaria;
    private String formaDePagamento;
    private Integer filialId;
    private String filialCodigo;

    private LinkedHashMap<String, URI> detalhes;

    public String getAdquirente() {
        return adquirente;
    }

    public void setAdquirente(String adquirente) {
        this.adquirente = adquirente;
    }

    public String getBandeira() {
        return bandeira;
    }

    public void setBandeira(String bandeira) {
        this.bandeira = bandeira;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public BigDecimal getValorBruto() {
        return valorBruto;
    }

    public void setValorBruto(BigDecimal valorBruto) {
        this.valorBruto = valorBruto;
    }

    public BigDecimal getValorLiquido() {
        return valorLiquido;
    }

    public void setValorLiquido(BigDecimal valorLiquido) {
        this.valorLiquido = valorLiquido;
    }

    public Integer getQuantidadeLanctosContra() {
        return quantidadeLanctosContra;
    }

    public void setQuantidadeLanctosContra(Integer quantidadeLanctosContra) {
        this.quantidadeLanctosContra = quantidadeLanctosContra;
    }

    public Integer getQuantidadeDeLancamentos() {
        return quantidadeDeLancamentos;
    }

    public void setQuantidadeDeLancamentos(Integer quantidadeDeLancamentos) {
        this.quantidadeDeLancamentos = quantidadeDeLancamentos;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getContaBancaria() {
        return contaBancaria;
    }

    public void setContaBancaria(String contaBancaria) {
        this.contaBancaria = contaBancaria;
    }

    public String getFormaDePagamento() {
        return formaDePagamento;
    }

    public void setFormaDePagamento(String formaDePagamento) {
        this.formaDePagamento = formaDePagamento;
    }

    public Integer getFilialId() {
        return filialId;
    }

    public void setFilialId(Integer filialId) {
        this.filialId = filialId;
    }

    public String getFilialCodigo() {
        return filialCodigo;
    }

    public void setFilialCodigo(String filialCodigo) {
        this.filialCodigo = filialCodigo;
    }

    public LinkedHashMap<String, URI> getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(LinkedHashMap<String, URI> detalhes) {
        this.detalhes = detalhes;
    }
    
    public void addDetalhe(String key, URI uri) {
        if (detalhes == null) {
            detalhes = new LinkedHashMap<>();
        }

        detalhes.put(key, uri);
    }    
}
