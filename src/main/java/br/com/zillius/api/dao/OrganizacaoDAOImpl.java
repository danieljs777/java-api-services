/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.dao;

import br.com.zillius.api.exceptions.InternalServerErrorException;
import br.com.zillius.api.model.Organizacao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Daniel
 */
@RequestScoped
public class OrganizacaoDAOImpl extends BaseOriginDAOImpl implements OrganizacaoDAO {

    @Override
    public Organizacao obterOrganizacaoPor(String appToken) throws Exception {
        Organizacao organizacao = new Organizacao();

        try (Connection cnn = getConnection(); PreparedStatement ps = getPreparedStatement(cnn, "select * from CFR_ORGANIZACAO where APP_KEY=?")) {

            ps.setString(1, appToken);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                organizacao.setTenantId(rs.getString("TENANT_ID"));
                organizacao.setDominio(rs.getInt("CD_DOMINIO"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAOImpl.class.getName()).log(Level.SEVERE, null, ex);

            throw new InternalServerErrorException("Invalid TenantId", ex);
        }

        return organizacao;
    }

}
