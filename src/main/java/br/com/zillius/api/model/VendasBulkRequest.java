/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Daniel
 */
public class VendasBulkRequest implements Serializable{

    public VendasBulkRequest() {
    }

    private List<VendasRequest> vendasRequest;
    private String clientId;

    /**
     * @return the vendasRequest
     */
    public List<VendasRequest> getVendasRequest() {
        return vendasRequest;
    }

    /**
     * @param vendasRequest the vendasRequest to set
     */
    public void setVendasRequest(List<VendasRequest> vendasRequest) {
        this.vendasRequest = vendasRequest;
    }

    /**
     * @return the clientId
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * @param clientId the clientId to set
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
