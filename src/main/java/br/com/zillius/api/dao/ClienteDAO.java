/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.dao;

import br.com.zillius.api.model.cliente.Cliente;

/**
 *
 * @author Daniel Interface de acesso a dados de cliente
 */
public interface ClienteDAO extends BaseDestinationDAO{
    
    /*
        Salvar um cliente
    */
    void salvarCliente(Cliente cliente);
    
    /*
        Obtem um cliente pelo id
    */
    Cliente obterClientePorId(String id);
}
