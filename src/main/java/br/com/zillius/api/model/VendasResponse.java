package br.com.zillius.api.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Daniel Jordão
 */
public class VendasResponse implements Serializable{

    private List<UDTVendasResponse> retornos;
    
    public VendasResponse() {
    }

    /**
     * @return the retornos
     */
    public List<UDTVendasResponse> getRetornos() {
        return retornos;
    }

    /**
     * @param retornos the retornos to set
     */
    public void setRetornos(List<UDTVendasResponse> retornos) {
        this.retornos = retornos;
    }

}
