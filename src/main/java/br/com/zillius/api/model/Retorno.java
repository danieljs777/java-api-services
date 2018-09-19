/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Daniel POJO de retorno para transitar dados entre os recursos da API e DAOs
 */
public class Retorno implements Serializable{

    private String clienteId;
    private String filialCodigo;
    private Integer filialId;
    private String key;
    private StatusConciliacao statusConciliacao;
    private String adquirente;
    private String cnpj;
    private String bandeira;
    private String produto;
    private Integer numeroDaParcela;
    private Integer quantidadeDeParcelas;
    private String nsu;
    private String autorizacao;
    private String tid;
    private BigDecimal valorBrutoConta;
    private BigDecimal valorBrutoContraPartida;
    private BigDecimal valorLiquidoConta;
    private BigDecimal valorLiquidoContraPartida;
    private BigDecimal taxaConta;
    private BigDecimal taxaContraPartida;
    private String banco;
    private String agencia;
    private String conta;  
    private String terminal;
    private String estabelecimento;
    private Date dataVenda;
    private Date dataRecebimento;
    private String sistemaOrigem;
    private Date dataDePrevisaoDoVencimento;
    private String notaFiscal;
    private Integer lancamentoId;

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
    public String getFilialCodigo() {
        return filialCodigo;
    }

    /**
     * @param filialCodigo the filialCodigo to set
     */
    public void setFilialCodigo(String filialCodigo) {
        this.filialCodigo = filialCodigo;
    }

    /**
     * @return the filialId
     */
    public Integer getFilialId() {
        return filialId;
    }

    /**
     * @param filialId the filialId to set
     */
    public void setFilialId(Integer filialId) {
        this.filialId = filialId;
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
     * @return the statusConciliacao
     */
    public StatusConciliacao getStatusConciliacao() {
        return statusConciliacao;
    }

    /**
     * @param statusConciliacao the statusConciliacao to set
     */
    public void setStatusConciliacao(StatusConciliacao statusConciliacao) {
        this.statusConciliacao = statusConciliacao;
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
    public String getCnpj() {
        return cnpj;
    }

    /**
     * @param cnpj the cnpj to set
     */
    public void setCnpj(String cnpj) {
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
     * @return the nsu
     */
    public String getNsu() {
        return nsu;
    }

    /**
     * @param nsu the nsu to set
     */
    public void setNsu(String nsu) {
        this.nsu = nsu;
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
     * @return the tid
     */
    public String getTid() {
        return tid;
    }

    /**
     * @param tid the tid to set
     */
    public void setTid(String tid) {
        this.tid = tid;
    }

    /**
     * @return the valorBrutoConta
     */
    public BigDecimal getValorBrutoConta() {
        return valorBrutoConta;
    }

    /**
     * @param valorBrutoConta the valorBrutoConta to set
     */
    public void setValorBrutoConta(BigDecimal valorBrutoConta) {
        this.valorBrutoConta = valorBrutoConta;
    }

    /**
     * @return the valorBrutoContraPartida
     */
    public BigDecimal getValorBrutoContraPartida() {
        return valorBrutoContraPartida;
    }

    /**
     * @param valorBrutoContraPartida the valorBrutoContraPartida to set
     */
    public void setValorBrutoContraPartida(BigDecimal valorBrutoContraPartida) {
        this.valorBrutoContraPartida = valorBrutoContraPartida;
    }

    /**
     * @return the valorLiquidoConta
     */
    public BigDecimal getValorLiquidoConta() {
        return valorLiquidoConta;
    }

    /**
     * @param valorLiquidoConta the valorLiquidoConta to set
     */
    public void setValorLiquidoConta(BigDecimal valorLiquidoConta) {
        this.valorLiquidoConta = valorLiquidoConta;
    }

    /**
     * @return the valorLiquidoContraPartida
     */
    public BigDecimal getValorLiquidoContraPartida() {
        return valorLiquidoContraPartida;
    }

    /**
     * @param valorLiquidoContraPartida the valorLiquidoContraPartida to set
     */
    public void setValorLiquidoContraPartida(BigDecimal valorLiquidoContraPartida) {
        this.valorLiquidoContraPartida = valorLiquidoContraPartida;
    }

    /**
     * @return the taxaConta
     */
    public BigDecimal getTaxaConta() {
        return taxaConta;
    }

    /**
     * @param taxaConta the taxaConta to set
     */
    public void setTaxaConta(BigDecimal taxaConta) {
        this.taxaConta = taxaConta;
    }

    /**
     * @return the taxaContraPartida
     */
    public BigDecimal getTaxaContraPartida() {
        return taxaContraPartida;
    }

    /**
     * @param taxaContraPartida the taxaContraPartida to set
     */
    public void setTaxaContraPartida(BigDecimal taxaContraPartida) {
        this.taxaContraPartida = taxaContraPartida;
    }

    /**
     * @return the banco
     */
    public String getBanco() {
        return banco;
    }

    /**
     * @param banco the banco to set
     */
    public void setBanco(String banco) {
        this.banco = banco;
    }

    /**
     * @return the agencia
     */
    public String getAgencia() {
        return agencia;
    }

    /**
     * @param agencia the agencia to set
     */
    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    /**
     * @return the conta
     */
    public String getConta() {
        return conta;
    }

    /**
     * @param conta the conta to set
     */
    public void setConta(String conta) {
        this.conta = conta;
    }

    /**
     * @return the terminal
     */
    public String getTerminal() {
        return terminal;
    }

    /**
     * @param terminal the terminal to set
     */
    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    /**
     * @return the estabelecimento
     */
    public String getEstabelecimento() {
        return estabelecimento;
    }

    /**
     * @param estabelecimento the estabelecimento to set
     */
    public void setEstabelecimento(String estabelecimento) {
        this.estabelecimento = estabelecimento;
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
     * @return the dataRecebimento
     */
    public Date getDataRecebimento() {
        return dataRecebimento;
    }

    /**
     * @param dataRecebimento the dataRecebimento to set
     */
    public void setDataRecebimento(Date dataRecebimento) {
        this.dataRecebimento = dataRecebimento;
    }

    /**
     * @return the sistemaOrigem
     */
    public String getSistemaOrigem() {
        return sistemaOrigem;
    }

    /**
     * @param sistemaOrigem the sistemaOrigem to set
     */
    public void setSistemaOrigem(String sistemaOrigem) {
        this.sistemaOrigem = sistemaOrigem;
    }

    /**
     * @return the dataDePrevisaoDoVencimento
     */
    public Date getDataDePrevisaoDoVencimento() {
        return dataDePrevisaoDoVencimento;
    }

    /**
     * @param dataDePrevisaoDoVencimento the dataDePrevisaoDoVencimento to set
     */
    public void setDataDePrevisaoDoVencimento(Date dataDePrevisaoDoVencimento) {
        this.dataDePrevisaoDoVencimento = dataDePrevisaoDoVencimento;
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
     * @return the lancamentoId
     */
    public Integer getLancamentoId() {
        return lancamentoId;
    }

    /**
     * @param lancamentoId the lancamentoId to set
     */
    public void setLancamentoId(Integer lancamentoId) {
        this.lancamentoId = lancamentoId;
    }

}
