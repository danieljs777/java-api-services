/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.dao;

import br.com.zillius.api.exceptions.InternalServerErrorException;
import br.com.zillius.api.model.Processo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Daniel
 */
@RequestScoped
public class ProcessoDAOImpl extends BaseDestinationDAOImpl implements ProcessoDAO {

    @Override
    public Processo getProcessStatus(long processId) {
        
        StringBuilder query = new StringBuilder();
        Processo processo = new Processo();

        query.append("SELECT * ");
        query.append(" FROM ");
        query.append(getSchema());
        query.append(".CFR_CC_REQUEST_V "
                + "     WHERE REQUEST_ID = ? ");
        
        try (Connection cnn = getConnection();
        PreparedStatement ps = getPreparedStatement(cnn, query.toString())) {

            ps.setLong(1, processId);
            
            try (ResultSet rs = ps.executeQuery()) {
                
                while (rs.next()) {
                    processo.setRequestId(rs.getInt("REQUEST_ID"));
                    processo.setDescricao(rs.getString("DESCRICAO"));
                    processo.setFaseCodigo(rs.getString("FASE_CODIGO"));
                    processo.setStatusCodigo(rs.getString("STATUS_CODIGO"));
                    processo.setDataInicio(rs.getDate("DATA_INICIO"));
                    processo.setDataFim(rs.getDate("DATA_FIM"));
                    processo.setRequestFases(rs.getString("REQUEST_FASES"));
                    processo.setRequestStatus(rs.getString("REQUEST_STATUS"));
                    
                }
            }
        } catch (Exception ex) {

            throw new InternalServerErrorException("Unable to query requests" + ex.getMessage(), ex);
        }
        
        return processo;
    }
    
    
    
}
