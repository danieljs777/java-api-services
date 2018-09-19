/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.model.cliente;

/**
 *
 * @author Daniel POJO de cliente para transitar dados entre os recursos da API e as DAOs
 */
public class Cliente {
 
    private String dominio;
    private String nome;
    private String clienteId;
    private String responsavel;
    private String cargoDoResponsavel;
    private String mnemonico;
    private String tipo;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String pais;
    private String cep;
    private String cnpj;
    private String inscricaoEstadual;
    private String inscricaoMunicipal;
    private String telefone;
    private String fax;
    private String site;
    private String email;

    /*
        Construtor
    */
    public Cliente(){
        
    }

    /**
     * Obter domínio
     * 
     * @return the dominio
     */
    public String getDominio() {
        return dominio;
    }

    /**
     * Informar domínio
     * 
     * @param dominio the dominio to set
     */
    public void setDominio(String dominio) {
        this.dominio = dominio;
    }

    /**
     * Obter Nome
     * 
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Informar nome
     * 
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
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
     * Obter Responsável
     * 
     * @return the responsavel
     */
    public String getResponsavel() {
        return responsavel;
    }

    /**
     * Informar responsável
     * 
     * @param responsavel the responsavel to set
     */
    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    /**
     * Obter cargo do responsável
     * 
     * @return the cargoDoResponsavel
     */
    public String getCargoDoResponsavel() {
        return cargoDoResponsavel;
    }

    /**
     * Informar o cargo do responsável
     * 
     * @param cargoDoResponsavel the cargoDoResponsavel to set
     */
    public void setCargoDoResponsavel(String cargoDoResponsavel) {
        this.cargoDoResponsavel = cargoDoResponsavel;
    }

    /**
     * Obter mnemonico
     * 
     * @return the mnemonico
     */
    public String getMnemonico() {
        return mnemonico;
    }

    /**
     * Informar mnemonico
     * 
     * @param mnemonico the mnemonico to set
     */
    public void setMnemonico(String mnemonico) {
        this.mnemonico = mnemonico;
    }

    /**
     * Obter tipo
     * 
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Informar tipo
     * 
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Obter logradouro
     * 
     * @return the logradouro
     */
    public String getLogradouro() {
        return logradouro;
    }

    /**
     * Informar Logradouro
     * 
     * @param logradouro the logradouro to set
     */
    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
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
     * Obter complemento
     * 
     * @return the complemento
     */
    public String getComplemento() {
        return complemento;
    }

    /**
     * Informar complemento
     * 
     * @param complemento the complemento to set
     */
    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    /**
     * Obter bairro
     * 
     * @return the bairro
     */
    public String getBairro() {
        return bairro;
    }

    /**
     * Informar bairro
     * 
     * @param bairro the bairro to set
     */
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    /**
     * Obter cidade
     * 
     * @return the cidade
     */
    public String getCidade() {
        return cidade;
    }

    /**
     * Informar cidade
     * 
     * @param cidade the cidade to set
     */
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    /**
     * Obter Uf
     * 
     * @return the uf
     */
    public String getUf() {
        return uf;
    }

    /**
     * Informar uf
     * 
     * @param uf the uf to set
     */
    public void setUf(String uf) {
        this.uf = uf;
    }

    /**
     * Obter país
     * 
     * @return the pais
     */
    public String getPais() {
        return pais;
    }

    /**
     * Informar país
     * 
     * @param pais the pais to set
     */
    public void setPais(String pais) {
        this.pais = pais;
    }

    /**
     * Obter cep
     * 
     * @return the cep
     */
    public String getCep() {
        return cep;
    }

    /**
     * Informar cep
     * 
     * @param cep the cep to set
     */
    public void setCep(String cep) {
        this.cep = cep;
    }

    /**
     * Obter CNPJ
     * 
     * @return the cnpj
     */
    public String getCnpj() {
        return cnpj;
    }

    /**
     * Informar CNPJ
     * 
     * @param cnpj the cnpj to set
     */
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    /**
     * Obter inscrição estadual
     * 
     * @return the inscricaoEstadual
     */
    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    /**
     * Informar inscrição estadual
     * 
     * @param inscricaoEstadual the inscricaoEstadual to set
     */
    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    /**
     * Obter inscrição municipal
     * 
     * @return the inscricaoMunicipal
     */
    public String getInscricaoMunicipal() {
        return inscricaoMunicipal;
    }

    /**
     * Informar inscrição municipal
     * 
     * @param inscricaoMunicipal the inscricaoMunicipal to set
     */
    public void setInscricaoMunicipal(String inscricaoMunicipal) {
        this.inscricaoMunicipal = inscricaoMunicipal;
    }

    /**
     * Obter telefone
     * 
     * @return the telefone
     */
    public String getTelefone() {
        return telefone;
    }

    /**
     * Informar telefone
     * 
     * @param telefone the telefone to set
     */
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    /**
     * Obter fax
     * 
     * @return the fax
     */
    public String getFax() {
        return fax;
    }

    /**
     * Informar fax
     * 
     * @param fax the fax to set
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * Obter site
     * 
     * @return the site
     */
    public String getSite() {
        return site;
    }

    /**
     * Informar site
     * 
     * @param site the site to set
     */
    public void setSite(String site) {
        this.site = site;
    }

    /**
     * Obter email
     * 
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Informar email
     * 
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
