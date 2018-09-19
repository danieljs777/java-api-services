/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.dao;

import br.com.zillius.api.exceptions.InternalServerErrorException;
import br.com.zillius.api.model.SchemaInfo;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Daniel
 */
@RequestScoped
public class SchemaInfoDAOImpl extends BaseOriginDAOImpl implements SchemaInfoDAO {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SchemaInfoDAOImpl.class);

    @Override
    public SchemaInfo obterSchemaInfoPor(String appToken) {

        SchemaInfo schemaInfo = new SchemaInfo();

        final String query = "SELECT org.tenant_id , "
                + "  org.descricao , "
                + "  sci.cd_dominio , "
                + "  sci.nm_dominio , "
                + "  sci.conection_pool , "
                + "  sci.username "
                + "FROM cfr_organizacao org "
                + "JOIN cfr_produto_cliente sci "
                + "ON org.cd_dominio = sci.cd_dominio "
                + "WHERE org.app_key = ?";

        try (Connection cnn = getConnection();
                PreparedStatement ps = getPreparedStatement(cnn, query)) {
            
            ps.setString(1, appToken);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                schemaInfo.setConnectionPool(rs.getString("CONECTION_POOL"));
                schemaInfo.setUserName(rs.getString("USERNAME"));
                schemaInfo.setTenantId(rs.getString("TENANT_ID"));
                schemaInfo.setDomainCode(rs.getInt("CD_DOMINIO"));
                schemaInfo.setDomainName(rs.getString("NM_DOMINIO"));
            }
            
            if(schemaInfo.getConnectionPool() == null)
            {
                logger.error("Invalid Token", "Invalid Token: " + appToken);

                throw new InternalServerErrorException("Invalid Token", null);                
                
            }

        } catch (SQLException ex) {
            logger.error("Error executing query", ex);
            
            throw new InternalServerErrorException("Invalid Schema", ex);

        }
        return schemaInfo;
    }
}
