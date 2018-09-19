/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.dao;

import br.com.zillius.api.model.Organizacao;

/**
 *
 * @author Daniel
 */
public interface OrganizacaoDAO extends BaseOriginDAO{
    
    Organizacao obterOrganizacaoPor(String appToken)throws Exception;
}
