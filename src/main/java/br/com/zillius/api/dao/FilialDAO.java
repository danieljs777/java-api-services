/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.dao;

import br.com.zillius.api.model.cliente.Estabelecimento;
import br.com.zillius.api.model.cliente.Filial;
import br.com.zillius.api.model.cliente.Taxa;
import br.com.zillius.api.model.cliente.Terminal;

/**
 *
 * @author Daniel Interface de acesso a dados de filial
 */
public interface FilialDAO extends BaseDestinationDAO{
    
    /*
        Salva um estabelecimento
    */
    void salvarEstabelecimento(Estabelecimento estabelecimento);
    
    /*
        Salva um terminal
    */
    void salvarTerminal(Terminal terminal);
    
    /*
        Salva uma taxa
    */
    void salvarTaxa(Taxa taxa);
    
    /*
        Salva uma filial
    */
    void salvarFilial(Filial filial);
    
    /*
        Obtem uma filial pelo seu id.
    */
    Filial obterFilialPorId(String id);
}
