/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.dao;

import br.com.zillius.api.model.SchemaInfo;

/**
 *
 * @author Daniel
 */
public interface SchemaInfoDAO extends BaseOriginDAO{

    SchemaInfo obterSchemaInfoPor(String appToken);
    
}
