/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.dao;

import br.com.zillius.api.model.VendasRequest;
import br.com.zillius.api.model.UDTVendasResponse;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Daniel
 */
public interface EntradasDAO extends BaseDestinationDAO{
    
    void salvarEntradasDeVendas(VendasRequest vendasRequest) throws Exception;
    List<UDTVendasResponse> salvarLoteDeVendas(List<VendasRequest> listVendasRequest);
    long excluirLoteDeVendas(List<VendasRequest> listVendasRequest, String pfjCode);
    long excluirPeriodoDeVendas(Date dataInicial, Date dataFinal, String pfjCode);
    long iniciarConciliacao(String pfjCodigo, Date dataInicial, Date dataFinal);
    
}
