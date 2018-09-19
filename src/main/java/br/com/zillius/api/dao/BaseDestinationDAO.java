/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.dao;

import javax.naming.NamingException;

/**
 *
 * @author Daniel
 */
public interface BaseDestinationDAO extends BaseDAO{

    String getSchema();

    void setSchema(String schema);

    String getConnectionPool();

    void setConnectionPool(String connectionPool)throws NamingException;
}
