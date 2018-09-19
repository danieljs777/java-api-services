/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.dao;

import br.com.zillius.api.dao.queryobject.ViewProperties;
import br.com.zillius.api.exceptions.InternalServerErrorException;
import br.com.zillius.api.model.Lancamento;
import br.com.zillius.api.model.DrillDownType;
import br.com.zillius.api.model.QueryCustomFilterVO;
import br.com.zillius.api.model.QueryFilterVO;
import br.com.zillius.api.model.ResourceType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Daniel Classe de acesso a dados de conciliação
 */
@RequestScoped
public class LancamentoDAOImpl extends BaseDestinationDAOImpl implements LancamentoDAO {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(LancamentoDAOImpl.class.getName());

    private DrillDownType tipoDrillDown;
    private ResourceType tipoResource;
    private int filialId, limit, offset;
    

    // <editor-fold defaultstate="collapsed" desc="Lançamentos de Vendas">
    private List<Lancamento> obterLancamentosDeVendas(String pfjCodigo, Date dataInicial, Date dataFinal, int nivel) {

        List<Lancamento> lancamentos = new ArrayList<>();
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
                .append(", SUM(QTDLANCTOSCONTRA) TOTAL_LANCTOS_CONTRA, SUM(VALORCONTRAPARTIDA) TOTAL_VALOR_CONTRA")
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
                    lancamentos.add(popularLancamentosDeVendas(rs, nivel));
                }
            };

            return lancamentos;
        } catch (Exception ex) {
            logger.error("DAOException ", ex);

            throw new InternalServerErrorException("Unable to query entries", ex);
        }

    }

    @Override
    public List<Lancamento> obterLancamentosDeVendasGeral(QueryFilterVO filters) {
        this.tipoDrillDown = filters.tipoDrillDown;
        this.tipoResource = filters.tipoResource;
        this.offset = filters.offset;
        this.limit = filters.limit;
        
        return obterLancamentosDeVendas(filters.pfjCodigo, filters.dataInicial, filters.dataFinal, 0);
    }

    @Override
    public List<Lancamento> obterLancamentosDeVendasFiliais(QueryFilterVO filters) {
        this.tipoDrillDown = filters.tipoDrillDown;
        this.tipoResource = filters.tipoResource;
        this.offset = filters.offset;
        this.limit = filters.limit;
        
        return obterLancamentosDeVendas(filters.pfjCodigo, filters.dataInicial, filters.dataFinal, 1);
    }

    @Override
    public List<Lancamento> obterLancamentosDeVendasPorFilial(QueryCustomFilterVO filters) {
        this.tipoDrillDown = filters.tipoDrillDown;
        this.tipoResource = filters.tipoResource;
        this.offset = filters.offset;
        this.limit = filters.limit;
        this.filialId = filters.filialId;
        
        return obterLancamentosDeVendas(filters.pfjCodigo, filters.dataInicial, filters.dataFinal, 2);
    }

    private Lancamento popularLancamentosDeVendas(ResultSet rs, int nivel) throws SQLException {
        Lancamento lancamento = new Lancamento();

        lancamento.setBandeira(rs.getString("BANDEIRA"));
        lancamento.setAdquirente(rs.getString("ADQUIRENTE"));
        lancamento.setProduto(rs.getString("PRODUTO"));

        lancamento.setQuantidadeLanctosContra(rs.getInt("TOTAL_LANCTOS_CONTRA"));;        
        lancamento.setValorBruto(rs.getBigDecimal("TOTAL_VALOR_CONTRA"));
        
        if (rs.wasNull()) {
            lancamento.setValorBruto(null);
        }

        if(nivel > 0) {
            lancamento.setFilialId(rs.getInt("FILIALID"));
            if (rs.wasNull()) {
                lancamento.setFilialId(null);
            }

            lancamento.setFilialCodigo(rs.getString("FILIALCODIGO"));
            if (rs.wasNull()) {
                lancamento.setFilialCodigo(null);
            }            
        }
        
        return lancamento;
    }

    // </editor-fold>    
    // <editor-fold defaultstate="collapsed" desc="Lançamentos de Recebimentos">
    @Override
    public List<Lancamento> obterLancamentosDeRecebimentosGeral(QueryFilterVO filters) {
        this.tipoDrillDown = filters.tipoDrillDown;
        this.tipoResource = filters.tipoResource;
        this.offset = filters.offset;
        this.limit = filters.limit;                
        
        return obterLancamentosDeRecebimentos(filters.pfjCodigo, filters.dataInicial, filters.dataFinal, 0);
    }

    @Override
    public List<Lancamento> obterLancamentosDeRecebimentosFiliais(QueryFilterVO filters) {
        this.tipoDrillDown = filters.tipoDrillDown;
        this.tipoResource = filters.tipoResource;
        this.offset = filters.offset;
        this.limit = filters.limit;
        
        return obterLancamentosDeRecebimentos(filters.pfjCodigo, filters.dataInicial, filters.dataFinal, 1);
    }

    @Override
    public List<Lancamento> obterLancamentosDeRecebimentosPorFilial(QueryCustomFilterVO filters) {
        this.tipoDrillDown = filters.tipoDrillDown;
        this.tipoResource = filters.tipoResource;
        this.offset = filters.offset;
        this.limit = filters.limit;
        this.filialId = filters.filialId;
        
        return obterLancamentosDeRecebimentos(filters.pfjCodigo, filters.dataInicial, filters.dataFinal, 2);
    }

    private List<Lancamento> obterLancamentosDeRecebimentos(String pfjCodigo, Date dataInicial, Date dataFinal, int nivel) {

        List<Lancamento> lancamentos = new ArrayList<>();
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
                .append(", SUM(QTDLANCTOSCONTRA) TOTAL_LANCTOS_CONTRA, SUM(VALORCONTRAPARTIDA) TOTAL_VALOR_CONTRA ")
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
                    lancamentos.add(popularLancamentosDeRecebimentos(rs, nivel));
                }
            }

            return lancamentos;
        } catch (Exception ex) {
            logger.error("DAOException ", ex);

            throw new InternalServerErrorException("Unable to query entries", ex);
        }
    }

    private Lancamento popularLancamentosDeRecebimentos(ResultSet rs, int nivel) throws SQLException {

        Lancamento lancamento = new Lancamento();

        lancamento.setBandeira(rs.getString("BANDEIRA"));
        lancamento.setAdquirente(rs.getString("ADQUIRENTE"));
        
        lancamento.setBanco(rs.getString("BANCO"));
        if (rs.wasNull()) {
            lancamento.setBanco(null);
        }

        lancamento.setAgencia(rs.getString("AGENCIA"));
        if (rs.wasNull()) {
            lancamento.setAgencia(null);
        }       
        
        lancamento.setContaBancaria(rs.getString("CONTA"));
        if (rs.wasNull()) {
            lancamento.setContaBancaria(null);
        }         
        
        lancamento.setQuantidadeLanctosContra(rs.getInt("TOTAL_LANCTOS_CONTRA"));
        lancamento.setValorLiquido(rs.getBigDecimal("TOTAL_VALOR_CONTRA"));

        if (nivel > 0) {
            lancamento.setFilialCodigo(rs.getString("FILIALCODIGO"));
            lancamento.setFilialId(rs.getInt("FILIALID"));
            ///lancamento.setNomeFilial(rs.getString("NOMEFILIAL"));
        }
        return lancamento;
    }

    // </editor-fold>    
    // <editor-fold defaultstate="collapsed" desc="Lançamentos de Previsões">
    
    @Override
    public List<Lancamento> obterLancamentosDePrevisoesGeral(QueryFilterVO filters) {
        this.tipoDrillDown = filters.tipoDrillDown;
        this.tipoResource = filters.tipoResource;
        this.offset = filters.offset;
        this.limit = filters.limit;        
        
        return obterLancamentosDePrevisoes(filters.pfjCodigo, filters.dataInicial, filters.dataFinal, 0);
    }

    @Override
    public List<Lancamento> obterLancamentosDePrevisoesFiliais(QueryFilterVO filters) {
        this.tipoDrillDown = filters.tipoDrillDown;
        this.tipoResource = filters.tipoResource;
        this.offset = filters.offset;
        this.limit = filters.limit;
        
        return obterLancamentosDePrevisoes(filters.pfjCodigo, filters.dataInicial, filters.dataFinal, 1);
    }

    @Override
    public List<Lancamento> obterLancamentosDePrevisoesPorFilial(QueryCustomFilterVO filters) {
        this.tipoDrillDown = filters.tipoDrillDown;
        this.tipoResource = filters.tipoResource;
        this.offset = filters.offset;
        this.limit = filters.limit;
        this.filialId = filters.filialId;
        
        return obterLancamentosDePrevisoes(filters.pfjCodigo, filters.dataInicial, filters.dataFinal, 2);
    }

    private List<Lancamento> obterLancamentosDePrevisoes(String pfjCodigo, Date dataInicial, Date dataFinal, int nivel) {

        List<Lancamento> lancamentos = new ArrayList<>();
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
                .append(", SUM(QTDLANCTOSCONTA) AS TOTAL_LANCTOS_CONTA, SUM(VALORLIQUIDOCONTA) AS TOTAL_VALOR_CONTA")
                .append(" FROM ")
                .append(getSchema())
                .append(".")
                .append(view)
                .append(" WHERE CLIENTECODIGO = ?")
                .append(" AND DATADEPREVISAODOVENCIMENTO BETWEEN ? AND ? ")
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
                    lancamentos.add(popularLancamentosDePrevisões(rs, nivel));
                }
            }

            return lancamentos;
        } catch (Exception ex) {
            logger.error("DAOException ", ex);

            throw new InternalServerErrorException("Unable to query entries", ex);
        }
    }

    private Lancamento popularLancamentosDePrevisões(ResultSet rs, int nivel) throws SQLException {

        Lancamento lancamento = new Lancamento();

        lancamento.setBandeira(rs.getString("BANDEIRA"));
        lancamento.setAdquirente(rs.getString("ADQUIRENTE"));
        
        lancamento.setBanco(rs.getString("BANCO"));
        if (rs.wasNull()) {
            lancamento.setBanco(null);
        }

        lancamento.setAgencia(rs.getString("AGENCIA"));
        if (rs.wasNull()) {
            lancamento.setAgencia(null);
        }       
        
        lancamento.setContaBancaria(rs.getString("CONTA"));
        if (rs.wasNull()) {
            lancamento.setContaBancaria(null);
        }        
        
        lancamento.setQuantidadeDeLancamentos(rs.getInt("TOTAL_LANCTOS_CONTA"));
        lancamento.setValorLiquido(rs.getBigDecimal("TOTAL_VALOR_CONTA"));

        if (nivel > 0) {
            lancamento.setFilialCodigo(rs.getString("FILIALCODIGO"));
            lancamento.setFilialId(rs.getInt("FILIALID"));
            ///lancamento.setNomeFilial(rs.getString("NOMEFILIAL"));
        }
        
        return lancamento;
    }
    
    // </editor-fold>    
    
    private java.sql.Date wrapDate(Date date) {
        return date != null ? new java.sql.Date(date.getTime()) : null;
    }

}
