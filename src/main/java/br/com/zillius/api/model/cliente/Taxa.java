/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.zillius.api.model.cliente;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author Daniel POJO Taxa para transitar dados entre os recursos da API e as DAOs
 */
public class Taxa {
    
    private String estabelecimentoId;
    private String bandeira;
    private String produto;
    private Date dataInicial;
    private Date dataFinal ;
    private BigDecimal valorInicial;
    private BigDecimal valorFinal;
    private Integer quantidadeMinimaDeParcelas ;
    private Integer quantidadeMaximaDeParcelas;
    private BigDecimal taxa ;
     private Map<String, String> testes;

    /**
     * Obter id do estabelecimento
     * 
     * @return the estabelecimentoId
     */
    public String getEstabelecimentoId() {
        return estabelecimentoId;
    }

    /**
     * Informar id do estabelecimento
     * 
     * @param estabelecimentoId the estabelecimentoId to set
     */
    public void setEstabelecimentoId(String estabelecimentoId) {
        this.estabelecimentoId = estabelecimentoId;
    }

    /**
     * Obter bandeira
     * 
     * @return the bandeira
     */
    public String getBandeira() {
        return bandeira;
    }

    /**
     * Informar bandeira
     * 
     * @param bandeira the bandeira to set
     */
    public void setBandeira(String bandeira) {
        this.bandeira = bandeira;
    }

    /**
     * Obter produto
     * 
     * @return the produto
     */
    public String getProduto() {
        return produto;
    }

    /**
     * Informar produto
     * 
     * @param produto the produto to set
     */
    public void setProduto(String produto) {
        this.produto = produto;
    }

    /**
     * obter data inicial
     * 
     * @return the dataInicial
     */
    public Date getDataInicial() {
        return dataInicial;
    }

    /**
     * Informar data inicial
     * 
     * @param dataInicial the dataInicial to set
     */
    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    /**
     * Obter data final
     * 
     * @return the dataFinal
     */
    public Date getDataFinal() {
        return dataFinal;
    }

    /**
     * Informar data final
     * 
     * @param dataFinal the dataFinal to set
     */
    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    /**
     * Obter valor inicial
     * 
     * @return the valorInicial
     */
    public BigDecimal getValorInicial() {
        return valorInicial;
    }

    /**
     * Informar valor inicial
     * 
     * @param valorInicial the valorInicial to set
     */
    public void setValorInicial(BigDecimal valorInicial) {
        this.valorInicial = valorInicial;
    }

    /**
     * Obter valor final
     * 
     * @return the valorFinal
     */
    public BigDecimal getValorFinal() {
        return valorFinal;
    }

    /**
     * Informar valor final
     * 
     * @param valorFinal the valorFinal to set
     */
    public void setValorFinal(BigDecimal valorFinal) {
        this.valorFinal = valorFinal;
    }

    /**
     * Obter quantidade mínima de parcelas
     * 
     * @return the quantidadeMinimaDeParcelas
     */
    public Integer getQuantidadeMinimaDeParcelas() {
        return quantidadeMinimaDeParcelas;
    }

    /**
     * Informar quantidade mínima de parcelas
     * 
     * @param quantidadeMinimaDeParcelas the quantidadeMinimaDeParcelas to set
     */
    public void setQuantidadeMinimaDeParcelas(Integer quantidadeMinimaDeParcelas) {
        this.quantidadeMinimaDeParcelas = quantidadeMinimaDeParcelas;
    }

    /**
     * Obter quantidade máxima de parcelas
     * 
     * @return the quantidadeMaximaDeParcelas
     */
    public Integer getQuantidadeMaximaDeParcelas() {
        return quantidadeMaximaDeParcelas;
    }

    /**
     * Informar quantidade máxima de parcelas
     * 
     * @param quantidadeMaximaDeParcelas the quantidadeMaximaDeParcelas to set
     */
    public void setQuantidadeMaximaDeParcelas(Integer quantidadeMaximaDeParcelas) {
        this.quantidadeMaximaDeParcelas = quantidadeMaximaDeParcelas;
    }

    /**
     * Obter taxa
     * 
     * @return the taxa
     */
    public BigDecimal getTaxa() {
        return taxa;
    }

    /**
     * Informar taxa
     * 
     * @param taxa the taxa to set
     */
    public void setTaxa(BigDecimal taxa) {
        this.taxa = taxa;
    }

    /**
     * @return the testes
     */
    public Map<String, String> getTestes() {
        return testes;
    }

    /**
     * @param testes the testes to set
     */
    public void setTestes(Map<String, String> testes) {
        this.testes = testes;
    }
}
