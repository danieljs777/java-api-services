/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.dao;

import br.com.zillius.api.exceptions.InternalServerErrorException;
import br.com.zillius.api.model.Retorno;
import br.com.zillius.api.model.StatusConciliacao;
import br.com.zillius.api.model.TipoFiltroDeRetorno;
import br.com.zillius.api.model.TipoRetorno;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Daniel Classe de acesso a dados de retorno de vendas e recebimentos
 */
@RequestScoped
public class RetornoDAOImpl extends BaseDestinationDAOImpl implements RetornoDAO {

    private static final Logger logger = LoggerFactory.getLogger(RetornoDAOImpl.class);

    /*
     retorna uma lista de retornos de vendas
     */
    @Override
    public long obterCountRetornos(TipoRetorno tipoRetorno, TipoFiltroDeRetorno tipoFiltroRetorno, String pfjCode, String clienteId, Date dataInicio, Date dataFinal, Integer filialId, StatusConciliacao statusConciliacao, String adquirente, String bandeira, String produto, String key, Integer lancamentoId) {

        StringBuilder query = new StringBuilder();

        if (tipoRetorno == TipoRetorno.VENDAS) {
            query.append("SELECT COUNT(CLIENTECODIGO) as total");
        } else {
            query.append("SELECT COUNT(PFJCODIGO) as total");
        }

        query.append(" FROM ");
        query.append(getSchema());

        if (tipoRetorno == TipoRetorno.VENDAS) {
            query.append(".CFR_CC_VENDAS_DET_V ");
            query.append("     WHERE CLIENTECODIGO = ? ");
            if (dataFinal != null && dataInicio != null)            
                query.append("       AND DATAVENDA BETWEEN ? AND ? ");
        } else {
            query.append(".CFR_CC_RECTOS_DET_V");
            query.append("     WHERE PFJCODIGO = ? ");

            if (dataFinal != null && dataInicio != null)
                query.append("  AND DATARECEBIMENTO BETWEEN ? AND ? ");        
            
        }
        

        if (produto != null) {
            query.append("  AND PRODUTO    = ? ");
        }

        if (bandeira != null) {
            query.append("  AND BANDEIRA   = ? ");
        }

        if (adquirente != null) {
            query.append("  AND ADQUIRENTE = ? ");
        }

        if (filialId != null) {
            query.append("  AND FILIALID   = ? ");
        }

        if (lancamentoId != null) {
            query.append("  AND LANCAMENTOID   = ? ");
        }

        if (key != null) {
            query.append("  AND key   = ? ");
        }

        if (statusConciliacao != null && statusConciliacao != StatusConciliacao.TODOS) {
            query.append(" AND STATUSCONCILIACAO = ? ");
        }

        if (tipoFiltroRetorno != null) {
            query.append(" And tipo_retorno = ? ");
        }

        logger.info(query.toString());

        try (Connection cnn = getConnection();
                PreparedStatement ps = getPreparedStatement(cnn, query.toString())) {

            ps.setString(1, pfjCode);

            Integer index = 2;

            if (dataFinal != null && dataInicio != null)
            {
                logger.info(wrapDate(dataInicio).toString());
                ps.setDate(2, wrapDate(dataInicio));
                ps.setDate(3, wrapDate(dataFinal));
                index = 4;
            }

            if (produto != null) {
                ps.setString(index, produto);
                index++;
            }

            if (bandeira != null) {
                ps.setString(index, bandeira);
                index++;
            }

            if (adquirente != null) {
                ps.setString(index, adquirente);
                index++;
            }

            if (filialId != null) {
                ps.setInt(index, filialId);
                index++;
            }

            if (key != null) {
                ps.setString(index, key);
                index++;
            }

            if (lancamentoId != null) {
                ps.setInt(index, lancamentoId);
                index++;
            }

            if (statusConciliacao != null && statusConciliacao != StatusConciliacao.TODOS) {
                ps.setString(index, statusConciliacao.toString());
                index++;
            }

            if (tipoFiltroRetorno != null) {
                ps.setString(index, tipoFiltroRetorno.toString());
                index++;
            }

            ResultSet rs = ps.executeQuery();

            long totalDeRegistros = 0;

            while (rs.next()) {
                totalDeRegistros = rs.getLong("total");
            }

            return totalDeRegistros;
        } catch (Exception e) {
            logger.error("Error executing query", e);
            throw new InternalServerErrorException("Unable to query entries", e);
        }
    }

    @Override
    public List<Retorno> obterRetornosDeVendas(String pfjCode, String clienteId, Date dataInicio, Date dataFinal, Integer filialId, StatusConciliacao statusConciliacao, String adquirente, String bandeira, String produto, String key, Integer lancamentoId, TipoFiltroDeRetorno tipoRetorno, int limit, int offset) {
        try {

            StringBuilder query = new StringBuilder();

            query.append("SELECT * FROM ( "
                    + "  SELECT rownum rn, t.* FROM ( "
                    + "    SELECT * ");
            query.append(" FROM ");
            query.append(getSchema());
            query.append(".CFR_CC_VENDAS_DET_V "
                    + "     WHERE CLIENTECODIGO = ? ");
            
            if (dataFinal != null && dataInicio != null)
                query.append("  AND DATAVENDA BETWEEN ? AND ? ");

            if (produto != null) {
                query.append("  AND PRODUTO    = ? ");
            }

            if (bandeira != null) {
                query.append("  AND BANDEIRA   = ? ");
            }

            if (adquirente != null) {
                query.append("  AND ADQUIRENTE = ? ");
            }

            if (filialId != null) {
                query.append("  AND FILIALID   = ? ");
            }

            if (lancamentoId != null) {
                query.append("  AND LANCAMENTOID   = ? ");
            }

            if (key != null) {
                query.append("  AND key   = ? ");
            }

            if (statusConciliacao != null && statusConciliacao != StatusConciliacao.TODOS) {
                query.append(" AND STATUSCONCILIACAO = ?");
            }

            if (tipoRetorno != null) {
                query.append(" AND tipo_retorno = ?");
            }

            query.append("  ) t ");

            query.append(" ) WHERE rn BETWEEN ? AND ? ");

            logger.info(query.toString());

            int offset2 = (offset + limit) - 1;

            return executarQuery(query.toString(), TipoRetorno.VENDAS, pfjCode, clienteId, dataInicio, dataFinal, filialId, statusConciliacao, adquirente, bandeira, produto, key, lancamentoId, tipoRetorno, offset, offset2);

        } catch (Exception e) {
            throw new InternalServerErrorException("Unable to query entries", e);
        }
    }

    /*
     Retorna uma lista de retornos de recebimentos
     */
    @Override
    public List<Retorno> obterRetornosDeRecebimentos(String pfjCode, String clienteId, Date dataInicio, Date dataFinal, Integer filialId, StatusConciliacao statusConciliacao, String adquirente, String bandeira, String produto, String key, Integer lancamentoId, TipoFiltroDeRetorno tipoRetorno, int limit, int offset) {

        StringBuilder query = new StringBuilder();

        query.append("SELECT * FROM ( "
                + "  SELECT rownum rn, t.* FROM ( "
                + "    SELECT * ");
        query.append(" FROM ");
        query.append(getSchema());
        query.append(".CFR_CC_RECTOS_DET_V "
                + "     WHERE PFJCODIGO = ? ");
                
        if (dataFinal != null && dataInicio != null)
            query.append("       AND DATARECEBIMENTO BETWEEN ? AND ? ");

        if (produto != null) {
            query.append("  AND PRODUTO    = ? ");
        }

        if (bandeira != null) {
            query.append("  AND BANDEIRA   = ? ");
        }

        if (adquirente != null) {
            query.append("  AND ADQUIRENTE = ? ");
        }

        if (filialId != null) {
            query.append("  AND FILIALID   = ? ");
        }

        if (lancamentoId != null) {
            query.append("  AND LANCAMENTOID   = ? ");
        }

        if (key != null) {
            query.append("  AND key   = ? ");
        }

        if (statusConciliacao != null && statusConciliacao != StatusConciliacao.TODOS) {
            query.append(" AND STATUSCONCILIACAO = ?");
        }

        if (tipoRetorno != null) {
            query.append(" AND tipo_retorno = ?");
        }

        query.append("  ) t ");

        query.append(" ) WHERE rn BETWEEN ? AND ? ");

        logger.info(query.toString());

        int offset2 = (offset + limit);

        return executarQuery(query.toString(), TipoRetorno.RECEBIMENTOS, pfjCode, clienteId, dataInicio, dataFinal, filialId, statusConciliacao, adquirente, bandeira, produto, key, lancamentoId, tipoRetorno, offset, offset2);
    }

    /*
     Executa a query
     */
    private List<Retorno> executarQuery(String query, TipoRetorno tipoRetorno, String pfjCodigo, String clienteId, Date dataInicial, Date dataFinal,
            Integer filialId, StatusConciliacao statusConciliacao, String adquirente, String bandeira, String produto,
            String key, Integer lancamentoId, TipoFiltroDeRetorno tipoFiltroDeRetorno, int offset, int offset2) {

        List<Retorno> retornos = new ArrayList<>();

        logger.info("PFJCodigo:" + pfjCodigo);
        logger.info("DataInicial: " + dataInicial);
        logger.info("DataFinal: " + dataFinal);

        try (Connection cnn = getConnection();
                PreparedStatement ps = getPreparedStatement(cnn, query)) {

            ps.setString(1, pfjCodigo);

            Integer index = 2;

            if (dataFinal != null && dataInicial != null)
            {
                logger.info(wrapDate(dataInicial).toString());
                ps.setDate(2, wrapDate(dataInicial));
                ps.setDate(3, wrapDate(dataFinal));
                index = 4;
            }

            if (produto != null) {
                ps.setString(index, produto);
                index++;
            }

            if (bandeira != null) {
                ps.setString(index, bandeira);
                index++;
            }

            if (adquirente != null) {
                ps.setString(index, adquirente);
                index++;
            }

            if (filialId != null) {
                ps.setInt(index, filialId);
                index++;
            }

            if (statusConciliacao != null && statusConciliacao != StatusConciliacao.TODOS) {
                ps.setString(index, statusConciliacao.toString());
                index++;
            }

            if (key != null) {
                ps.setString(index, key);
                index++;
            }

            if (lancamentoId != null) {
                ps.setInt(index, lancamentoId);
                index++;
            }

            if (tipoFiltroDeRetorno != null) {
                ps.setString(index, tipoFiltroDeRetorno.toString());
                index++;
            }

            ps.setInt(index, offset);

            index++;

            ps.setInt(index, offset2);

            logger.info("Rodar a query ");
            ResultSet rs = ps.executeQuery();

            logger.info("Rodei ");
            while (rs.next()) {
                Retorno retorno = new Retorno();

                retorno.setClienteId(clienteId);

                retorno.setFilialId(rs.getInt("filialId"));
                if (rs.wasNull()) {
                    //throw new SQLException("filialId has not returned");
                    continue;
                }

                retorno.setLancamentoId(rs.getInt("lancamentoId"));
                if (rs.wasNull()) {
                    //throw new SQLException("lancamentoId has not returned");
                    continue;
                }

                retorno.setFilialCodigo(rs.getString("filialCodigo"));
                retorno.setKey(rs.getString("key"));

                if (rs.getString("statusConciliacao") == null) {
                    //throw new SQLException("StatusConciliacao has not returned");
                } else {
                    retorno.setStatusConciliacao(StatusConciliacao.valueOf(rs.getString("statusConciliacao")));
                }

                if (rs.getString("Adquirente") == null) {
                    continue;
                    //throw new SQLException("Adquirente has not returned");
                } else {
                    retorno.setAdquirente(rs.getString("Adquirente"));
                }

                retorno.setCnpj(rs.getString("CNPJ"));
                retorno.setBandeira(rs.getString("Bandeira"));
                retorno.setProduto(rs.getString("produto"));
                retorno.setNumeroDaParcela(rs.getInt("numeroDaParcela"));
                if (rs.wasNull()) {
                    retorno.setNumeroDaParcela(null);
                }
                retorno.setQuantidadeDeParcelas(rs.getInt("quantidadeDeParcelas"));
                if (rs.wasNull()) {
                    retorno.setQuantidadeDeParcelas(null);
                }
                retorno.setNsu(rs.getString("nsu"));

                if (rs.wasNull()) {
                    retorno.setNsu(null);
                }
                retorno.setAutorizacao(rs.getString("autorizacao"));
                retorno.setTid(rs.getString("tid"));
                retorno.setValorBrutoConta(rs.getBigDecimal("valorBrutoConta"));
                retorno.setValorBrutoContraPartida(rs.getBigDecimal("valorBrutoContraPartida"));
                retorno.setValorLiquidoConta(rs.getBigDecimal("valorLiquidoConta"));
                retorno.setValorLiquidoContraPartida(rs.getBigDecimal("valorLiquidoContraPartida"));
                retorno.setTaxaConta(rs.getBigDecimal("taxaConta"));
                retorno.setTaxaContraPartida(rs.getBigDecimal("taxaContraPartida"));
                retorno.setBanco(rs.getString("banco"));
                retorno.setAgencia(rs.getString("agencia"));
                retorno.setConta(rs.getString("conta"));
                retorno.setTerminal(rs.getString("terminal"));
                retorno.setEstabelecimento(rs.getString("estabelecimento"));

                retorno.setDataDePrevisaoDoVencimento(rs.getDate("dataDePrevisaoDoVencimento"));

                switch (tipoRetorno) {
                    case RECEBIMENTOS:
                        if (rs.getDate("DATARECEBIMENTO") == null) {
                            //throw new SQLException("dataRecebimento has not returned");
                        } else {
                            retorno.setDataRecebimento(rs.getDate("DATARECEBIMENTO"));
                        }
                        retorno.setDataVenda(rs.getDate("dataVenda"));
                        break;
                    case VENDAS:
                        if (rs.getDate("dataVenda") == null) {
                            //throw new SQLException("dataVenda has not returned");
                        } else {
                            retorno.setDataVenda(rs.getDate("dataVenda"));
                        }
                        retorno.setDataRecebimento(rs.getDate("DATARECEBIMENTO"));
                        break;
                }

                retorno.setNotaFiscal(rs.getString("notaFiscal"));
                retorno.setSistemaOrigem(rs.getString("SISTEMAORIGEM"));

                retornos.add(retorno);

            }
            return retornos;
        } catch (Exception e) {
            logger.error("Error executing query", e);
            throw new InternalServerErrorException("Unable to query entries", e);
        }
    }

    /**
     * Retorna uma data do banco sql a partir de uma data do java.
     *
     * @param date data original
     * @return data para jdbc
     */
    private java.sql.Date wrapDate(Date date) {
        return date != null ? new java.sql.Date(date.getTime()) : null;
    }
}
