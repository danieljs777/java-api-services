/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.model;

import java.io.Serializable;

/**
 *
 * @author Daniel POJO de Referência para transitar informações entre os recursos da API e as DAOs
 */
public class Referencia implements Serializable{

    private String coluna;
    private String valor;

    public Referencia() {
    }
    
    /*
        Construtor básico
    */
    public Referencia(String coluna, String valor) {
        this.coluna = coluna;
        this.valor = valor;
    }

    /**
     * Obter Coluna
     * 
     * @return the coluna
     */
    public String getColuna() {
        return coluna;
    }

    /**
     * Obter valor
     * 
     * @return the valor
     */
    public String getValor() {
        return valor;
    }

}
