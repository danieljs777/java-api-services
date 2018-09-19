package br.com.zillius.api.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URI;
import java.util.LinkedHashMap;

/*
 POJO de conciliação para transitar entre recursos da API e DAOs
 */
public class Conciliacao implements Serializable {

    private Integer quantidadeLancamentosConta;
    private Integer quantidadeLancamentosContrapartida;
    private BigDecimal valorContrapartida;
    private BigDecimal valorConta;
    private BigDecimal diferenca;
    private String nomeFilial;
    private String filialCodigo;
    private Integer filialId;
    private String bandeira;
    private String adquirente;
    private LinkedHashMap<String, URI> detalhes;

    /*
     Obter Quantidade de lançamentos da conta
     */
    public Integer getQuantidadeLancamentosConta() {
        return quantidadeLancamentosConta;
    }

    /*
     Informar quantidade de lançamentos da conta
     */
    public void setQuantidadeLancamentosConta(Integer quantidadeLancamentosConta) {
        this.quantidadeLancamentosConta = quantidadeLancamentosConta;
    }

    /*
     Obter quantidade de lançamentos da contrapartida
     */
    public Integer getQuantidadeLancamentosContrapartida() {
        return quantidadeLancamentosContrapartida;
    }

    /*
     Informar quantidade de lançamentos da contrapartida
     */
    public void setQuantidadeLancamentosContrapartida(
            Integer quantidadeLancamentosContrapartida) {
        this.quantidadeLancamentosContrapartida
                = quantidadeLancamentosContrapartida;
    }

    /*
     Obter valor da conta
     */
    public BigDecimal getValorConta() {
        return valorConta;
    }

    /*
     Informar valor da conta
     */
    public void setValorConta(BigDecimal valorConta) {
        this.valorConta = valorConta;
    }

    public BigDecimal getValorContrapartida() {
        return valorContrapartida;
    }

    public void setValorContrapartida(BigDecimal valorContrapartida) {
        this.valorContrapartida = valorContrapartida;
    }

    /*
     Obter Diferença
     */
    public BigDecimal getDiferenca() {
        return diferenca;
    }

    /*
     Obter url analítica
     */
    public LinkedHashMap<String, URI> getDetalhes() {
        return detalhes;
    }

    /*
     Informar Url analítica
     */
    public void setDetalhes(LinkedHashMap<String, URI> urlAnalitica) {
        this.detalhes = urlAnalitica;
    }

    /*
     transformar em String
     */
    @Override
    public String toString() {
        return "Consolidado{" + "quantidadeLancamentosConta=" + quantidadeLancamentosConta + ", quantidadeLancamentosContrapartida=" + quantidadeLancamentosContrapartida + ", valorConta=" + valorConta + ", valorContrapartida=" + valorContrapartida + ", urlAnalitica=" + detalhes + '}';
    }

    /**
     * Informar diferença
     *
     * @param diferenca diferença de valor entre conta e contrapartida
     */
    public void setDiferenca(BigDecimal diferenca) {
        this.diferenca = diferenca;
    }

    /**
     * Obter bandeira
     *
     * @return bandeira
     */
    public String getBandeira() {
        return bandeira;
    }

    /**
     * Informar bandeira
     *
     * @param bandeira bandeira
     */
    public void setBandeira(String bandeira) {
        this.bandeira = bandeira;
    }

    /**
     * Obter Adquirente
     *
     * @return the adquirente
     */
    public String getAdquirente() {
        return adquirente;
    }

    /**
     * Informar adquirente
     *
     * @param adquirente the adquirente to set
     */
    public void setAdquirente(String adquirente) {
        this.adquirente = adquirente;
    }

    /**
     * Obter Nome da filial
     *
     * @return the nomeFilial
     */
    public String getNomeFilial() {
        return nomeFilial;
    }

    /**
     * Informar o nome da filial
     *
     * @param nomeFilial the nomeFilial to set
     */
    public void setNomeFilial(String nomeFilial) {
        this.nomeFilial = nomeFilial;
    }

    /**
     * Obter o id da filial
     *
     * @return the filialCodigo
     */
    public String getFilialCodigo() {
        return filialCodigo;
    }

    /**
     * Informar o id da filial
     *
     * @param filialCodigo the filialCodigo to set
     */
    public void setFilialCodigo(String filialCodigo) {
        this.filialCodigo = filialCodigo;
    }

    public void addDetalhe(String key, URI uri) {
        if (detalhes == null) {
            detalhes = new LinkedHashMap<>();
        }

        detalhes.put(key, uri);
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
}
