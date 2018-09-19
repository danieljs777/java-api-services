/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.zillius.api.model.cliente;

/**
 *
 * @author Daniel POJO filial para transitar dados entre os recusos da API e as DAOs
 */
public class Filial extends Cliente{
    
    private String filialId;
    
    /*
        Construtor
    */
    public Filial(){
        
    }
    
    /**
     * Obter id da filial
     * 
     * @return the id
     */
    public String getFilialId() {
        return filialId;
    }

    /**
     * Informar id da filial
     * 
     * @param id the id to set
     */
    public void setFilialId(String id) {
        this.filialId = id;
    }
}
