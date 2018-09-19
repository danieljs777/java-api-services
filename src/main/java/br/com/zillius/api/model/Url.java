/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.zillius.api.model;

/**
 *
 * @author Daniel POJO de Url para transitar dados entre os recursos da API e as DAOs
 */
public class Url {
    private String rel;
    private String href;
    private String verbo;

    /**
     * obter nome do método
     * 
     * @return the rel
     */
    public String getRel() {
        return rel;
    }

    /**
     * Informar nome do método
     * 
     * @param rel the rel to set
     */
    public void setRel(String rel) {
        this.rel = rel;
    }

    /**
     * Obter endereço
     * 
     * @return the href
     */
    public String getHref() {
        return href;
    }

    /**
     * Informar endereço
     * 
     * @param href the href to set
     */
    public void setHref(String href) {
        this.href = href;
    }

    /**
     * Obter Verbo - GET, POST, PUT, DELETE
     * 
     * @return the verbo
     */
    public String getVerbo() {
        return verbo;
    }

    /**
     * Informar verbo - GET, POST, PUT, DELETE
     * 
     * @param verbo the verbo to set
     */
    public void setVerbo(String verbo) {
        this.verbo = verbo;
    }
}
