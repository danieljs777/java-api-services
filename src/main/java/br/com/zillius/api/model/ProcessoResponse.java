/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.model;

import java.io.Serializable;

/**
 *
 * @author Daniel
 */
public class ProcessoResponse implements Serializable{
    
    private long processId;
    
    public ProcessoResponse() {
    }

    public long getProcessId() {
        return processId;
    }

    public void setProcessId(long processId) {
        this.processId = processId;
    }
    
    
    
}
