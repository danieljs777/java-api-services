/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.dao;

import br.com.zillius.api.dao.queryobject.ViewProperties;
import br.com.zillius.api.exceptions.InternalServerErrorException;
import br.com.zillius.api.model.Conciliacao;
import br.com.zillius.api.model.DrillDownType;
import br.com.zillius.api.model.QueryCustomFilterVO;
import br.com.zillius.api.model.QueryFilterVO;
import br.com.zillius.api.model.ResourceType;
import br.com.zillius.api.model.VendasRequest;
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Daniel Classe de acesso a dados de conciliação
 */
@RequestScoped
public class ConciliacaoDAOImpl extends BaseDestinationDAOImpl implements ConciliacaoDAO {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ConciliacaoDAOImpl.class.getName());

    private DrillDownType tipoDrillDown;
    private ResourceType tipoResource;
    private int filialId, limit, offset;
            
    /*
     Obtem conciliações
     */
    private List<Conciliacao> obterConciliacoesDeVendas(String pfjCodigo, Date dataInicial, Date dataFinal, int nivel) {

        List<Conciliacao> conciliacoes = new ArrayList<>();
        setSchema(getSchema());

        String view, group_by, where;

        ViewProperties viewProperties = tipoResource.getView(tipoDrillDown, nivel);
        
        view = viewProperties.getViewName();
        group_by = viewProperties.getGroupBy();
        where = viewProperties.getWhereClause();

        StringBuilder query = new StringBuilder();
        
//        if (nivel > 0) {
            query.append("SELECT * FROM (SELECT rownum rn, t.*  FROM  (");
//        }        
        
        query.append("SELECT ").append(group_by)
                .append(", SUM(QTDLANCTOSCONTA) TOTAL_LANCTOS, SUM(QTDLANCTOSCONTRA) TOTAL_LANCTOS_CONTRA,")
                .append(" SUM(VALORCONTA) TOTAL_VALOR_CONTA, SUM(VALORCONTRAPARTIDA) TOTAL_VALOR_CONTRA, SUM(DIFERENCA) TOTAL_DIFERENCA")
                .append(" FROM ")
                .append(getSchema())
                .append(".")
                .append(view)
                .append(" WHERE CLIENTECODIGO = ?")
                .append(" AND DATAVENDA BETWEEN ? AND ? ")
                .append(where)
                .append(" GROUP BY ")
                .append(group_by);
//        query.append(" ORDER BY DATAVENDA DESC, BANDEIRA, ADQUIRENTE");

//        if (nivel > 0) {
            query.append("  ) t ) WHERE rn BETWEEN ? AND ? ");
//        }
        
        try (Connection cnn = getConnection();
                PreparedStatement ps = getPreparedStatement(cnn, query.toString())) {

            if (pfjCodigo == null) {
                throw new IllegalArgumentException("Not enough arguments!");
            }

            ps.setString(1, pfjCodigo);
            ps.setDate(2, wrapDate(dataInicial));
            ps.setDate(3, wrapDate(dataFinal));

            int index = 4;

            if (nivel == 2) {
                ps.setInt(index, filialId);
                index++;
            }

            int offset2 = (offset + limit) - 1;

//            if (nivel > 0) {
                ps.setInt(index, offset);
                index++;
                ps.setInt(index, offset2);
//            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    conciliacoes.add(popularConciliacoesDeVendas(rs));
                }
            };

            return conciliacoes;
        } catch (Exception ex) {
            logger.error("DAOException ", ex);

            throw new InternalServerErrorException("Unable to query reconciliations", ex);
        }

    }

    @Override
    public List<Conciliacao> obterConciliacoesDeVendasGeral(QueryFilterVO filters) {
        this.tipoDrillDown = filters.tipoDrillDown;
        this.tipoResource = filters.tipoResource;
        this.offset = filters.offset;
        this.limit = filters.limit;        
        
        return obterConciliacoesDeVendas(filters.pfjCodigo, filters.dataInicial, filters.dataFinal, 0);
    }

    @Override
    public List<Conciliacao> obterConciliacoesDeVendasFiliais(QueryFilterVO filters) {
        this.tipoDrillDown = filters.tipoDrillDown;
        this.tipoResource = filters.tipoResource;
        this.offset = filters.offset;
        this.limit = filters.limit;
        
        return obterConciliacoesDeVendas(filters.pfjCodigo, filters.dataInicial, filters.dataFinal, 1);
    }

    @Override
    public List<Conciliacao> obterConciliacoesDeVendasPorFilial(QueryCustomFilterVO filters) {
        this.tipoDrillDown = filters.tipoDrillDown;
        this.filialId = filters.filialId;
        this.tipoResource = filters.tipoResource;
        this.offset = filters.offset;
        this.limit = filters.limit;        
        
        return obterConciliacoesDeVendas(filters.pfjCodigo, filters.dataInicial, filters.dataFinal, 2);
    }

    private Conciliacao popularConciliacoesDeVendas(ResultSet rs) throws SQLException {
        Conciliacao conciliacao = new Conciliacao();

        conciliacao.setQuantidadeLancamentosConta(rs.getInt("TOTAL_LANCTOS"));
        conciliacao.setQuantidadeLancamentosContrapartida(rs.getInt("TOTAL_LANCTOS_CONTRA"));
        conciliacao.setBandeira(rs.getString("BANDEIRA"));
        conciliacao.setAdquirente(rs.getString("ADQUIRENTE"));

        conciliacao.setValorConta(rs.getBigDecimal("TOTAL_VALOR_CONTA"));
        if (rs.wasNull()) {
            conciliacao.setValorConta(null);
        }

        conciliacao.setValorContrapartida(rs.getBigDecimal("TOTAL_VALOR_CONTRA"));
        if (rs.wasNull()) {
            conciliacao.setValorContrapartida(null);
        }

        conciliacao.setDiferenca(rs.getBigDecimal("TOTAL_DIFERENCA"));
        if (rs.wasNull()) {
            conciliacao.setDiferenca(null);
        }

        try {
            conciliacao.setFilialId(rs.getInt("FILIALID"));
            if (rs.wasNull()) {
                conciliacao.setFilialId(null);
            }

            conciliacao.setNomeFilial(rs.getString("NOMEFILIAL"));
            if (rs.wasNull()) {
                conciliacao.setNomeFilial(null);
            }

        } catch (Exception e) {
            //Não precisa tratar caso os campos não sejam retornados na view
        }

        return conciliacao;
    }

    @Override
    public List<Conciliacao> obterConciliacoesDeRecebimentosGeral(QueryFilterVO filters) {
        this.tipoDrillDown = filters.tipoDrillDown;
        this.tipoResource = filters.tipoResource;
        this.offset = filters.offset;
        this.limit = filters.limit;                
        
        return obterConciliacoesDeRecebimentos(filters.pfjCodigo, filters.dataInicial, filters.dataFinal, 0);
    }

    @Override
    public List<Conciliacao> obterConciliacoesDeRecebimentosFiliais(QueryFilterVO filters) {
        this.tipoDrillDown = filters.tipoDrillDown;
        this.tipoResource = filters.tipoResource;
        this.offset = filters.offset;
        this.limit = filters.limit;
        
        return obterConciliacoesDeRecebimentos(filters.pfjCodigo, filters.dataInicial, filters.dataFinal, 1);
    }

    @Override
    public List<Conciliacao> obterConciliacoesDeRecebimentosPorFilial(QueryCustomFilterVO filters) {
        this.tipoDrillDown = filters.tipoDrillDown;
        this.tipoResource = filters.tipoResource;
        this.offset = filters.offset;
        this.limit = filters.limit;
        this.filialId = filters.filialId;
        
        return obterConciliacoesDeRecebimentos(filters.pfjCodigo, filters.dataInicial, filters.dataFinal, 2);
    }

    private List<Conciliacao> obterConciliacoesDeRecebimentos(String pfjCodigo, Date dataInicial, Date dataFinal, int nivel) {

        List<Conciliacao> conciliacoes = new ArrayList<>();
        setSchema(getSchema());

        String view, group_by, where;

        ViewProperties viewProperties = tipoResource.getView(tipoDrillDown, nivel);
        
        view = viewProperties.getViewName();
        group_by = viewProperties.getGroupBy();
        where = viewProperties.getWhereClause();

        StringBuilder query = new StringBuilder();

//        if (nivel > 0) {
            query.append("SELECT * FROM (SELECT rownum rn, t.*  FROM  (");
//        }

        query.append("SELECT ")
                .append(group_by)
                .append(", SUM(QTDLANCTOSCONTA) AS QTDLANCTOSCONTA , SUM(QTDLANCTOSCONTRA) AS QTDLANCTOSCONTRA , ")
                .append(" SUM(VALORCONTA) AS VALORCONTA ,SUM(VALORCONTRAPARTIDA) AS VALORCONTRAPARTIDA,SUM(VALORCONTA) - SUM(VALORCONTRAPARTIDA) AS DIFERENCA ")
                .append(" FROM ")
                .append(getSchema())
                .append(".")
                .append(view)
                .append(" WHERE PFJCODIGO = ?")
                .append(" AND DATARECEBIMENTO BETWEEN ? AND ? ")
                .append(where)
                .append(" GROUP BY ")
                .append(group_by);

//        if (nivel > 0) {
            query.append("  ) t ) WHERE rn BETWEEN ? AND ? ");
//        }

        try (Connection cnn = getConnection();
                PreparedStatement ps = getPreparedStatement(cnn, query.toString())) {

            if (pfjCodigo == null) {
                throw new IllegalArgumentException("Not enough arguments!");
            }

            ps.setString(1, pfjCodigo);
            ps.setDate(2, wrapDate(dataInicial));
            ps.setDate(3, wrapDate(dataFinal));

            int index = 4;

            if (nivel == 2) {
                ps.setInt(index, filialId);
                index++;
            }

            int offset2 = (offset + limit) - 1;

//            if (nivel > 0) {
                ps.setInt(index, offset);
                index++;
                ps.setInt(index, offset2);
//            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    conciliacoes.add(popularConciliacoesDeRecebimentos(rs, nivel));
                }
            }

            return conciliacoes;
        } catch (Exception ex) {
            logger.error("DAOException ", ex);

            throw new InternalServerErrorException("Unable to query reconciliations", ex);
        }
    }

    private Conciliacao popularConciliacoesDeRecebimentos(ResultSet rs, int nivel) throws SQLException {

        Conciliacao conciliacao = new Conciliacao();

        conciliacao.setQuantidadeLancamentosConta(rs.getInt("QTDLANCTOSCONTA"));
        conciliacao.setQuantidadeLancamentosContrapartida(rs.getInt("QTDLANCTOSCONTRA"));
        conciliacao.setValorConta(rs.getBigDecimal("VALORCONTA"));
        conciliacao.setValorContrapartida(rs.getBigDecimal("VALORCONTRAPARTIDA"));
        conciliacao.setDiferenca(rs.getBigDecimal("DIFERENCA"));
        conciliacao.setBandeira(rs.getString("BANDEIRA"));
        conciliacao.setAdquirente(rs.getString("ADQUIRENTE"));

        if (nivel > 0) {
            conciliacao.setFilialCodigo(rs.getString("FILIALCODIGO"));
            conciliacao.setFilialId(rs.getInt("FILIALID"));
            conciliacao.setNomeFilial(rs.getString("NOMEFILIAL"));
        }
        return conciliacao;
    }

    private java.sql.Date wrapDate(Date date) {
        return date != null ? new java.sql.Date(date.getTime()) : null;
    }


}
