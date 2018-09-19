/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 *
 * @author Daniel
 */
public class BaseOriginDAOImpl implements BaseOriginDAO {

    @Resource(mappedName = "jdbc/cfr_main")
    private DataSource dataSource;

    @Override
    public Connection getConnection() throws SQLException {
        return this.dataSource.getConnection();
    }

    @Override
    public PreparedStatement getPreparedStatement(Connection connection, String statement) throws SQLException {

        return connection.prepareStatement(statement);
    }
}