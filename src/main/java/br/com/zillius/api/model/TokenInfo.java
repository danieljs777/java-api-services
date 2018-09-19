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
public class TokenInfo {
    
    private String schema;
    private String dataSource;
    private String appToken;

    /**
     * @return the schema
     */
    public String getSchema() {
        return schema;
    }

    /**
     * @param schema the schema to set
     */
    public void setSchema(String schema) {
        this.schema = schema;
    }

    /**
     * @return the dataSource
     */
    public String getDataSource() {
        return dataSource;
    }

    /**
     * @param dataSource the dataSource to set
     */
    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * @return the appToken
     */
    public String getAppToken() {
        return appToken;
    }

    /**
     * @param appToken the appToken to set
     */
    public void setAppToken(String appToken) {
        this.appToken = appToken;
    }
}
