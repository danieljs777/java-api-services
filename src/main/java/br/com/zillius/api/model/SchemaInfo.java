/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.model;

/**
 *
 * @author Daniel
 */
public class SchemaInfo {
    
    private Integer id;
    private String connectionPool;
    private String jdbcUrl;
    private String domainName;
    private String password;
    private String accessUrl;
    private String userName;
    private Integer domainCode;
    private Integer clienteId;
    private Integer serverId;
    private String tenantId;
    
    public Integer getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the connectionPool
     */
    public String getConnectionPool() {
        return connectionPool;
    }

    /**
     * @param connectionPool the connectionPool to set
     */
    public void setConnectionPool(String connectionPool) {
        this.connectionPool = connectionPool;
    }

    /**
     * @return the jdbcUrl
     */
    public String getJdbcUrl() {
        return jdbcUrl;
    }

    /**
     * @param jdbcUrl the jdbcUrl to set
     */
    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    /**
     * @return the domainName
     */
    public String getDomainName() {
        return domainName;
    }

    /**
     * @param domainName the domainName to set
     */
    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the accessUrl
     */
    public String getAccessUrl() {
        return accessUrl;
    }

    /**
     * @param accessUrl the accessUrl to set
     */
    public void setAccessUrl(String accessUrl) {
        this.accessUrl = accessUrl;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the domainCode
     */
    public Integer getDomainCode() {
        return domainCode;
    }

    /**
     * @param domainCode the domainCode to set
     */
    public void setDomainCode(Integer domainCode) {
        this.domainCode = domainCode;
    }

    /**
     * @return the clienteId
     */
    public Integer getClienteId() {
        return clienteId;
    }

    /**
     * @param clienteId the clienteId to set
     */
    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    /**
     * @return the serverId
     */
    public Integer getServerId() {
        return serverId;
    }

    /**
     * @param serverId the serverId to set
     */
    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    /**
     * @return the tenantId
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * @param tenantId the tenantId to set
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

  
}
