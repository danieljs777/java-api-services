/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.zillius.api.model.cliente;

/**
 *
 * @author Daniel Pojo de estabelecimento para transitar dados entre os recursos das APIs e as DAOs
 */
public class Estabelecimento {
    
    private int id;
    private String clienteId;
    private String filialId;
    private int operadoraId;
    private String numero;
    private String referEcommerce;

    /*
        Construtor
    */
    public Estabelecimento(){
        
    }
    
    /**
     * Obter id
     * 
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Informar Id
     * 
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obter id do cliente
     * 
     * @return the clienteId
     */
    public String getClienteId() {
        return clienteId;
    }

    /**
     * Informar id do cliente
     * 
     * @param clienteId the clienteId to set
     */
    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    /**
     * Obter id da filial
     * 
     * @return the filialId
     */
    public String getFilialId() {
        return filialId;
    }

    /**
     * Informar id da filial
     * 
     * @param filialId the filialId to set
     */
    public void setFilialId(String filialId) {
        this.filialId = filialId;
    }

    /**
     * Obter id da operadora
     * 
     * @return the operadoraId
     */
    public int getOperadoraId() {
        return operadoraId;
    }

    /**
     * Informar id da operadora
     * 
     * @param operadoraId the operadoraId to set
     */
    public void setOperadoraId(int operadoraId) {
        this.operadoraId = operadoraId;
    }

    /**
     * Obter número
     * 
     * @return the numero
     */
    public String getNumero() {
        return numero;
    }

    /**
     * Informar número
     * 
     * @param numero the numero to set
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     * Obter refer ecommerce
     * 
     * @return the referEcommerce
     */
    public String getReferEcommerce() {
        return referEcommerce;
    }

    /**
     * Informar refer ecommerce
     * 
     * @param referEcommerce the referEcommerce to set
     */
    public void setReferEcommerce(String referEcommerce) {
        this.referEcommerce = referEcommerce;
    }
}
