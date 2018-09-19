/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.dao;

import br.com.zillius.api.exceptions.InternalServerErrorException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author Daniel Classe base de BaseDestinationDAO com métodos de configuração
 */
public class BaseDestinationDAOImpl implements BaseDestinationDAO {

    private String schema;
    private String connectionPool;

    /*
     Data Source
     */
    private DataSource dataSource;

    protected Connection getConnection() throws SQLException {
        
        if(this.dataSource == null)
            throw new InternalServerErrorException("The database connection failed");
        
        return this.dataSource.getConnection();
    }

    protected PreparedStatement getPreparedStatement(Connection connection, String statement) throws SQLException {

        return connection.prepareStatement(statement);
    }

    protected CallableStatement getCallableStatement(Connection connection, String procedureCall) throws SQLException {
        return connection.prepareCall(procedureCall);
    }

    @Override
    public String getSchema() {
        return schema;
    }

    @Override
    public void setSchema(String schema) {
        this.schema = schema;
    }

    @Override
    public String getConnectionPool() {
        return connectionPool;
    }

    @Override
    public void setConnectionPool(String connectionPool) throws NamingException {
        this.connectionPool = connectionPool;
        final Context context = new InitialContext();
        dataSource = (DataSource) context.lookup("jdbc/" + connectionPool.toLowerCase());
    }
}
