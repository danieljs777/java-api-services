/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.xml.bind.annotation.XmlTransient;


/**
 *
 * @author Daniel POJO de request de vendas para transitar dados entre os recursos da API e as DAOs
 */
public class VendasRequest implements Serializable{

    private String pfjCode;
    private String clienteId;
    private Integer filialId;
    private String filialCodigo;
    private String adquirente;
    private String cnpj;
    private String bandeira;
    private String produto;
    private Integer numeroDaParcela;
    private Integer quantidadeDeParcelas;
    private String NSU;
    private String autorizacao;
    private String TID;
    private BigDecimal valorBruto;
    private BigDecimal valorLiquido;
    private String cliente;
    private Date dataVenda;
    private String origem;
    private String key;
    private Date dataPrevisaoVencimento;
    private String notaFiscal;
    private Integer lancamentoId;

    
    public String getFilialCodigo() {
        return filialCodigo;
    }

    public void setFilialCodigo(String filialCodigo) {
        this.filialCodigo = filialCodigo;
    }

    
    /**
     * @return the clienteId
     */
    public String getClienteId() {
        return clienteId;
    }

    /**
     * @param clienteId the clienteId to set
     */
    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    /**
     * @return the filialCodigo
     */
    public Integer getFilialId() {
        return filialId;
    }

    /**
     * @param filialCodigo the filialCodigo to set
     */
    public void setFilialId(Integer filialId) {
        this.filialId = filialId;
    }

    /**
     * @return the adquirente
     */
    public String getAdquirente() {
        return adquirente;
    }

    /**
     * @param adquirente the adquirente to set
     */
    public void setAdquirente(String adquirente) {
        this.adquirente = adquirente;
    }

    /**
     * @return the cnpj
     */
    public String getCNPJ() {
        return cnpj;
    }

    /**
     * @param cnpj the cnpj to set
     */
    public void setCNPJ(String cnpj) {
        this.cnpj = cnpj;
    }

    /**
     * @return the bandeira
     */
    public String getBandeira() {
        return bandeira;
    }

    /**
     * @param bandeira the bandeira to set
     */
    public void setBandeira(String bandeira) {
        this.bandeira = bandeira;
    }

    /**
     * @return the produto
     */
    public String getProduto() {
        return produto;
    }

    /**
     * @param produto the produto to set
     */
    public void setProduto(String produto) {
        this.produto = produto;
    }

    /**
     * @return the numeroDaParcela
     */
    public Integer getNumeroDaParcela() {
        return numeroDaParcela;
    }

    /**
     * @param numeroDaParcela the numeroDaParcela to set
     */
    public void setNumeroDaParcela(Integer numeroDaParcela) {
        this.numeroDaParcela = numeroDaParcela;
    }

    /**
     * @return the quantidadeDeParcelas
     */
    public Integer getQuantidadeDeParcelas() {
        return quantidadeDeParcelas;
    }

    /**
     * @param quantidadeDeParcelas the quantidadeDeParcelas to set
     */
    public void setQuantidadeDeParcelas(Integer quantidadeDeParcelas) {
        this.quantidadeDeParcelas = quantidadeDeParcelas;
    }

    /**
     * @return the NSU
     */
    public String getNSU() {
        return NSU;
    }

    /**
     * @param NSU the NSU to set
     */
    public void setNSU(String NSU) {
        this.NSU = NSU;
    }

    /**
     * @return the autorizacao
     */
    public String getAutorizacao() {
        return autorizacao;
    }

    /**
     * @param autorizacao the autorizacao to set
     */
    public void setAutorizacao(String autorizacao) {
        this.autorizacao = autorizacao;
    }

    /**
     * @return the TID
     */
    public String getTID() {
        return TID;
    }

    /**
     * @param TID the TID to set
     */
    public void setTID(String TID) {
        this.TID = TID;
    }

    /**
     * @return the valorBruto
     */
    public BigDecimal getValorBruto() {
        return valorBruto;
    }

    /**
     * @param valorBruto the valorBruto to set
     */
    public void setValorBruto(BigDecimal valorBruto) {
        this.valorBruto = valorBruto;
    }

    /**
     * @return the valorLiquido
     */
    public BigDecimal getValorLiquido() {
        return valorLiquido;
    }

    /**
     * @param valorLiquido the valorLiquido to set
     */
    public void setValorLiquido(BigDecimal valorLiquido) {
        this.valorLiquido = valorLiquido;
    }

    /**
     * @return the cliente
     */
    public String getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    /**
     * @return the dataVenda
     */
    public Date getDataVenda() {
        return dataVenda;
    }

    /**
     * @param dataVenda the dataVenda to set
     */
    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }

    /**
     * @return the origem
     */
    public String getOrigem() {
        return origem;
    }

    /**
     * @param origem the origem to set
     */
    public void setOrigem(String origem) {
        this.origem = origem;
    }

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return the dataPrevisaoVencimento
     */
    public Date getDataPrevisaoVencimento() {
        return dataPrevisaoVencimento;
    }

    /**
     * @param dataPrevisaoVencimento the dataPrevisaoVencimento to set
     */
    public void setDataPrevisaoVencimento(Date dataPrevisaoVencimento) {
        this.dataPrevisaoVencimento = dataPrevisaoVencimento;
    }

    /**
     * @return the notaFiscal
     */
    public String getNotaFiscal() {
        return notaFiscal;
    }

    /**
     * @param notaFiscal the notaFiscal to set
     */
    public void setNotaFiscal(String notaFiscal) {
        this.notaFiscal = notaFiscal;
    }

    /**
     * @return the pfjCode
     */
    @XmlTransient
    public String getPfjCode() {
        return pfjCode;
    }

    /**
     * @param pfjCode the pfjCode to set
     */
    public void setPfjCode(String pfjCode) {
        this.pfjCode = pfjCode;
    }

    public Integer getLancamentoId() {
        return lancamentoId;
    }

    public void setLancamentoId(Integer lancamentoId) {
        this.lancamentoId = lancamentoId;
    }
    
    

}
