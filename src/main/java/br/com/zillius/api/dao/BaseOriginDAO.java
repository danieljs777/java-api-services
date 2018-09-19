/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Daniel
 */
public interface BaseOriginDAO extends BaseDAO{

    Connection getConnection() throws SQLException;

    PreparedStatement getPreparedStatement(Connection connection, String statement) throws SQLException;
    
}
