/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.zillius.api.model.cliente;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Daniel POJO de terminal para transitar dados entre os recusos da API e as DAOs
 */
public class Terminal {
    
    private Integer id;
    private String estabelecimentoId;
    private String terminal;
    private String tipoTerminal;
    private String descricao;
    private BigDecimal valorContratado; 
    private Date dataInicioAtividade;
    private Date dataFimAtividade;
    
    /*
        Construtor
    */
    public Terminal(){
        
    }
    
    /**
     * Obter id
     * 
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Informar id
     * 
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

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
     * Obter terminal
     * 
     * @return the terminal
     */
    public String getTerminal() {
        return terminal;
    }

    /**
     * Informar terminal
     * 
     * @param terminal the terminal to set
     */
    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    /**
     * Obter tipo de terminal
     * 
     * @return the tipoTerminal
     */
    public String getTipoTerminal() {
        return tipoTerminal;
    }

    /**
     * informar tipo de terminal
     * 
     * @param tipoTerminal the tipoTerminal to set
     */
    public void setTipoTerminal(String tipoTerminal) {
        this.tipoTerminal = tipoTerminal;
    }

    /**
     * Obter descrição
     * 
     * @return the descricao
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Informar descrição
     * 
     * @param descricao the descricao to set
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Obter valor contratado
     * 
     * @return the valorContratado
     */
    public BigDecimal getValorContratado() {
        return valorContratado;
    }

    /**
     * Informar valor contratado
     * 
     * @param valorContratado the valorContratado to set
     */
    public void setValorContratado(BigDecimal valorContratado) {
        this.valorContratado = valorContratado;
    }

    /**
     * Obter data de início de atividade
     * 
     * @return the dataInicioAtividade
     */
    public Date getDataInicioAtividade() {
        return dataInicioAtividade;
    }

    /**
     * Informar data de início de atividade
     * 
     * @param dataInicioAtividade the dataInicioAtividade to set
     */
    public void setDataInicioAtividade(Date dataInicioAtividade) {
        this.dataInicioAtividade = dataInicioAtividade;
    }

    /**
     * Obter data fim de atividade
     * 
     * @return the dataFimAtividade
     */
    public Date getDataFimAtividade() {
        return dataFimAtividade;
    }

    /**
     * Informar data fim de atividade
     * 
     * @param dataFimAtividade the dataFimAtividade to set
     */
    public void setDataFimAtividade(Date dataFimAtividade) {
        this.dataFimAtividade = dataFimAtividade;
    }

}
