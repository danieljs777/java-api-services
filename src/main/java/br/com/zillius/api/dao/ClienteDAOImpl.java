/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.dao;

import br.com.zillius.api.exceptions.InternalServerErrorException;
import br.com.zillius.api.model.cliente.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Daniel Classe de acesso a dados de cliente
 */
@RequestScoped
public class ClienteDAOImpl extends BaseDestinationDAOImpl implements ClienteDAO {
    /*
     Salva um cliente na base
     */

    @Override
    public void salvarCliente(Cliente cliente) {

        final String statement = "begin  " + getSchema() + ".cfr_api_transaction_pkg.incluir_cliente(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;";

        try (Connection cnn = getConnection();
                PreparedStatement ps = getPreparedStatement(cnn, statement)) {
            ps.setString(1, cliente.getDominio());
            ps.setString(2, cliente.getClienteId());
            ps.setString(3, cliente.getNome());
            ps.setString(4, cliente.getResponsavel());
            ps.setString(5, cliente.getCargoDoResponsavel());
            ps.setString(6, cliente.getMnemonico());
            ps.setString(7, cliente.getTipo());
            ps.setString(8, cliente.getLogradouro());
            ps.setString(9, cliente.getNumero());
            ps.setString(10, cliente.getComplemento());
            ps.setString(11, cliente.getBairro());
            ps.setString(12, cliente.getCidade());
            ps.setString(13, cliente.getUf());
            ps.setString(14, cliente.getPais());
            ps.setString(15, cliente.getCep());
            ps.setString(16, cliente.getCnpj());
            ps.setString(17, cliente.getInscricaoEstadual());
            ps.setString(18, cliente.getInscricaoMunicipal());
            ps.setString(19, cliente.getTelefone());
            ps.setString(20, cliente.getFax());
            ps.setString(21, cliente.getSite());
            ps.setString(22, cliente.getEmail());

            ps.execute();

        } catch (SQLException ex) {
            Logger.getLogger(FilialDAOImpl.class.getName()).log(Level.SEVERE, null, ex);

            throw new InternalServerErrorException("Unable to query customer", ex);
        }

    }

    /*
     Obtem um cliente pelo id
     */
    @Override
    public Cliente obterClientePorId(String id) {

        Cliente cliente = new Cliente();

        try (Connection cnn = getConnection();
                PreparedStatement ps = getPreparedStatement(cnn, "select * from " + getSchema() + ".Caifr_empresa_uen where pjf_codigo=?")) {
            ps.setString(1, id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                cliente.setDominio(rs.getString(""));
                cliente.setClienteId(rs.getString(""));
                cliente.setNome(rs.getString(""));
                cliente.setResponsavel(rs.getString(""));
                cliente.setCargoDoResponsavel(rs.getString(""));
                cliente.setMnemonico(rs.getString(""));
                cliente.setTipo(rs.getString(""));
                cliente.setLogradouro(rs.getString(""));
                cliente.setNumero(rs.getString(""));
                cliente.setComplemento(rs.getString(""));
                cliente.setBairro(rs.getString(""));
                cliente.setCidade(rs.getString(""));
                cliente.setUf(rs.getString(""));
                cliente.setPais(rs.getString(""));
                cliente.setCep(rs.getString(""));
                cliente.setCnpj(rs.getString(""));
                cliente.setInscricaoEstadual(rs.getString(""));
                cliente.setInscricaoMunicipal(rs.getString(""));
                cliente.setTelefone(rs.getString(""));
                cliente.setFax(rs.getString(""));
                cliente.setSite(rs.getString(""));
                cliente.setEmail(rs.getString(""));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAOImpl.class.getName()).log(Level.SEVERE, null, ex);

            throw new InternalServerErrorException("Unable to query customer", ex);
        }
        return cliente;
    }

}
