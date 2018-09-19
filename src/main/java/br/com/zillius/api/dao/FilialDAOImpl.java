/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.dao;

import br.com.zillius.api.exceptions.InternalServerErrorException;
import br.com.zillius.api.model.cliente.Estabelecimento;
import br.com.zillius.api.model.cliente.Filial;
import br.com.zillius.api.model.cliente.Taxa;
import br.com.zillius.api.model.cliente.Terminal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Daniel Classe de acesso a dados de filial
 */
@RequestScoped
public class FilialDAOImpl extends BaseDestinationDAOImpl implements FilialDAO {

    /*
     Salva um estabelecimento
     */
    @Override
    public void salvarEstabelecimento(Estabelecimento estabelecimento) {

        try (Connection cnn = getConnection();
                PreparedStatement ps = getPreparedStatement(cnn, "begin  " + getSchema() + ".cfr_api_transaction_pkg.incluir_estabelecimento(?,?,?,?,?); end;")) {

            ps.setString(1, estabelecimento.getClienteId());
            ps.setString(2, estabelecimento.getFilialId());
            ps.setInt(3, estabelecimento.getOperadoraId());
            ps.setString(4, estabelecimento.getNumero());
            ps.setString(5, estabelecimento.getReferEcommerce());

            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(FilialDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            throw new InternalServerErrorException("Unable to perform configuration", ex);
        }
    }

    /*
     Salva um terminal
     */
    @Override
    public void salvarTerminal(Terminal terminal) {

        try (Connection cnn = getConnection();
                PreparedStatement ps = getPreparedStatement(cnn, "begin  " + getSchema() + ".cfr_api_transaction_pkg.incluir_terminal(?,?,?,?,?,?,?); end;")) {

            ps.setString(1, terminal.getEstabelecimentoId());
            ps.setString(2, terminal.getTerminal());
            ps.setString(3, terminal.getTipoTerminal());
            ps.setString(4, terminal.getDescricao());
            ps.setBigDecimal(5, terminal.getValorContratado());
            ps.setDate(6, (Date) terminal.getDataInicioAtividade());
            ps.setDate(7, (Date) terminal.getDataFimAtividade());

            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(FilialDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            throw new InternalServerErrorException("Unable to perform configuration", ex);
        }
    }

    /*
     Salva uma taxa acordada com uma adquirente
     */
    @Override
    public void salvarTaxa(Taxa taxa) {
        try (Connection cnn = getConnection();
                PreparedStatement ps = getPreparedStatement(cnn, "begin  " + getSchema() + ".cfr_api_transaction_pkg.incluir_taxa(?,?,?,?,?,?,?,?,?,?); end;")) {

            ps.setString(1, taxa.getEstabelecimentoId());
            ps.setString(2, taxa.getBandeira());
            ps.setString(3, taxa.getProduto());
            ps.setDate(4, (Date) taxa.getDataInicial());
            ps.setDate(5, (Date) taxa.getDataFinal());
            ps.setBigDecimal(6, taxa.getValorInicial());
            ps.setBigDecimal(7, taxa.getValorFinal());
            ps.setInt(8, taxa.getQuantidadeMinimaDeParcelas());
            ps.setInt(9, taxa.getQuantidadeMaximaDeParcelas());
            ps.setBigDecimal(10, taxa.getTaxa());

            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(FilialDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            throw new InternalServerErrorException("Unable to perform configuration", ex);
        }
    }

    /*
     Obtem uma filial pelo seu id
     */
    @Override
    public Filial obterFilialPorId(String id) {

        Filial filial = new Filial();

        StringBuilder query = new StringBuilder();

        query.append("select * from  " + getSchema() + ".caifr_cc_filial f where f.Filial_Codigo =?");

        try (Connection cnn = getConnection();
                PreparedStatement ps = getPreparedStatement(cnn, query.toString())) {
            ps.setString(1, id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                filial.setFilialId(rs.getString("FILIAL_CODIGO"));
                filial.setClienteId(rs.getString("PFJ_CODIGO"));
                filial.setNome(rs.getString("FILIAL_NOME"));
                filial.setCnpj(rs.getString("CNPJ"));
            }
            return filial;
        } catch (SQLException e) {
            throw new InternalServerErrorException("Unable to perform configuration", e);
        }
    }

    /*
     Salva uma filial
     */
    @Override
    public void salvarFilial(Filial filial) {
       
            StringBuilder statement = new StringBuilder();

            statement.append("begin  " + getSchema() + ".cfr_api_transaction_pkg.incluir_filial(?,?,?,?); end;");

            try (Connection cnn = getConnection(); 
                    PreparedStatement ps = getPreparedStatement(cnn, statement.toString())) {
                ps.setString(1, filial.getClienteId());
                ps.setString(2, filial.getFilialId());
                ps.setString(3, filial.getNome());
                ps.setString(4, filial.getCnpj());

                ps.execute();

        } catch (SQLException ex) {
            Logger.getLogger(FilialDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            
            throw new InternalServerErrorException("Unable to perform configuration", ex);
        }
    }

}
