/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.dao;

import br.com.zillius.api.exceptions.InternalServerErrorException;
import br.com.zillius.api.model.APIConstants;
import br.com.zillius.api.model.VendasRequest;
import br.com.zillius.api.model.UDTVendasResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import oracle.jdbc.OracleConnection;

/**
 *
 * @author Daniel
 */
@RequestScoped
public class EntradasDAOImpl extends BaseDestinationDAOImpl implements EntradasDAO {

    
    @Override
    public long iniciarConciliacao(String pfjCodigo, Date dataInicial, Date dataFinal) {
        final String statement = "{call " + getSchema() + ".CFR_API_TRANSACTION_PKG.REQ_CONC_VENDA(?,?,?,?)}";
        try (
                Connection cnn = getConnection();
                CallableStatement callableSt = cnn.prepareCall(statement)) {
            
            callableSt.setString(1, pfjCodigo);
            callableSt.setDate(2, wrapDate(dataInicial));
            callableSt.setDate(3, wrapDate(dataFinal));
            callableSt.registerOutParameter(4, Types.NUMERIC);

            callableSt.executeUpdate();

            final long processId = (Integer) callableSt.getInt(4);

            return processId;

        } catch (Exception ex) {
            Logger.getLogger(ConciliacaoDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new InternalServerErrorException("Unable to start conciliation: " + ex.getMessage(), ex);
        }
        
    }

    
    @Override
    public void salvarEntradasDeVendas(VendasRequest vendasRequest) throws Exception {

        //, ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?
        final String statement = "{call " + getSchema() + " .CFR_API_TRANSACTION_PKG.INCLUIR_VENDAS(?, ?, ?, ?, ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? )}";

        try (Connection cnn = getConnection();
                CallableStatement callableSt = getCallableStatement(cnn, statement)) {
            callableSt.setString("P_PFJ_CODIGO", vendasRequest.getPfjCode());
            callableSt.setString("P_FILIAL_CODIGO", vendasRequest.getFilialCodigo());
            callableSt.setInt("P_FILIAL_ID", vendasRequest.getFilialId());
            callableSt.setString("P_ADQUIRENTE", vendasRequest.getAdquirente());
            callableSt.setString("P_CNPJ_FILIAL", vendasRequest.getCNPJ());
            callableSt.setString("P_BANDEIRA", vendasRequest.getBandeira());
            callableSt.setString("P_PRODUTO", vendasRequest.getProduto());
            callableSt.setInt("P_NR_PARCELA", vendasRequest.getNumeroDaParcela());
            callableSt.setInt("P_QTD_PARCELAS", vendasRequest.getQuantidadeDeParcelas());
            callableSt.setString("P_NSU", vendasRequest.getNSU());
            callableSt.setString("P_AUTORIZACAO", vendasRequest.getAutorizacao());
            callableSt.setString("P_TID", vendasRequest.getTID());
            callableSt.setBigDecimal("P_VLR_BRUTO", vendasRequest.getValorBruto());
            callableSt.setBigDecimal("P_VLR_LIQUIDO", vendasRequest.getValorLiquido());
            callableSt.setString("P_NM_CLIENTE", vendasRequest.getCliente());
            callableSt.setDate("P_DATA_VENDA", wrapDate(vendasRequest.getDataVenda()));
            callableSt.setString("P_ORIGEM_VENDA", vendasRequest.getOrigem());
            callableSt.setString("P_KEY", vendasRequest.getKey());
            callableSt.setDate("P_DATA_PREVISAO", wrapDate(vendasRequest.getDataPrevisaoVencimento()));
            callableSt.setString("P_NOTA_FISCAL", vendasRequest.getNotaFiscal());

            callableSt.executeUpdate();

        } catch (Exception ex) {
            //Logger.getLogger(EntradasDAOImpl.class.getName()).log(Level.SEVERE, ex);
            throw new InternalServerErrorException("Unable to save entries", ex);
        }
    }

    @Override
    public List<UDTVendasResponse> salvarLoteDeVendas(List<VendasRequest> listVendasRequest) {
        final String statement = "{call " + getSchema() + ".CFR_API_TRANSACTION_PKG.INCLUIR_VENDAS_LOTE(?,?)}";
        final String responseCustomType = getSchema() + ".VENDA_RESPONSE_TABELA";

        //Logger.getLogger(EntradasDAOImpl.class.getName()).log(Level.INFO, ""  + getSchema());
        try (
                Connection cnn = getConnection();
                CallableStatement callableSt = cnn.prepareCall(statement)) {

            final Array TabelaListVendasRequest = toArray(listVendasRequest, "VENDA_REQUEST_TABELA", cnn);
            callableSt.setArray(1, TabelaListVendasRequest);
            callableSt.registerOutParameter(2, Types.ARRAY, responseCustomType);

            callableSt.executeUpdate();

            final Array responseArray = (Array) callableSt.getObject(2);

            List<UDTVendasResponse> listVendasResponse = fromOracleArray(responseArray);

            return listVendasResponse;

        } catch (Exception ex) {
            Logger.getLogger(EntradasDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new InternalServerErrorException("Unable to save entries: " + ex.getMessage(), ex);
        }

    }
    
    public static Date dateFromString(String sDate) throws ParseException
    {
        DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Date date = formatter.parse(sDate);
        
        return date;
    }    
    
    public static String today(String format)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String date = sdf.format(new Date()); 
	return date; 
    }    
    
    @Override
    public long excluirLoteDeVendas(List<VendasRequest> listVendasRequest, String pfjCode) {
        final String statement = "{call " + getSchema() + ".CFR_API_TRANSACTION_PKG.EXCLUI_LOTE_VENDA(?,?,?,?,?,?)}";

        try (
                Connection cnn = getConnection();
                CallableStatement callableSt = cnn.prepareCall(statement)) {

            final Array TabelaListVendasRequest = toArrayNumber(listVendasRequest, "LISTA_ID_VENDA", cnn);
            callableSt.setString(1, "L");
            callableSt.setArray(2, TabelaListVendasRequest);
            callableSt.setDate(3, new java.sql.Date(new Date().getTime()));
            callableSt.setDate(4, new java.sql.Date(new Date().getTime()));
            callableSt.setString(5, pfjCode);
            //callableSt.setArray(5, TabelaListVendasRequest);
            callableSt.registerOutParameter(6, Types.NUMERIC);

            callableSt.executeUpdate();

            final long processId = (Integer) callableSt.getInt(6);

            return processId;

        } catch (Exception ex) {
            Logger.getLogger(EntradasDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new InternalServerErrorException("Unable to save entries: " + ex.getMessage(), ex);
        }

    }    
    
    public long excluirPeriodoDeVendas(Date dataInicial, Date dataFinal, String pfjCode)
    {
        final String statement = "{call " + getSchema() + ".CFR_API_TRANSACTION_PKG.EXCLUI_LOTE_VENDA(?,?,?,?,?,?)}";
        try (
                Connection cnn = getConnection();
                CallableStatement callableSt = cnn.prepareCall(statement)) {

            final Array TabelaListVendasRequest = toArray(new ArrayList<VendasRequest>(), "LISTA_ID_VENDA", cnn);
            
            callableSt.setString(1, "P");
            callableSt.setArray(2, TabelaListVendasRequest);
            callableSt.setDate(3, wrapDate(dataInicial));
            callableSt.setDate(4, wrapDate(dataFinal));
            callableSt.setString(5, pfjCode);
            //callableSt.setArray(5, TabelaListVendasRequest);
            callableSt.registerOutParameter(6, Types.NUMERIC);

            callableSt.executeUpdate();

            final long processId = (Integer) callableSt.getInt(6);

            return processId;

        } catch (Exception ex) {
            Logger.getLogger(EntradasDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new InternalServerErrorException("Unable to save entries: " + ex.getMessage(), ex);
        }
    }

    private List<UDTVendasResponse> fromOracleArray(Array array) throws SQLException {
        final Object[] data = (Object[]) array.getArray();
        List<UDTVendasResponse> listVendasResponse = new ArrayList<>();

        for (Object tmp : data) {
            Struct row = (Struct) tmp;

            UDTVendasResponse venda = fromStruct(row);
            listVendasResponse.add(venda);

        }

        return listVendasResponse;
    }

    private UDTVendasResponse fromStruct(Struct structVendaResponse) throws SQLException {

        final UDTVendasResponse vendaResponse = new UDTVendasResponse();

        Object[] attributes = structVendaResponse.getAttributes();

        Logger.getLogger(EntradasDAOImpl.class.getName()).log(Level.INFO, "" + ((BigDecimal) attributes[APIConstants.UDT_VENDA_RESPONSE_IDX_HTTP_COD]).intValue());

        final int codigoHttp = ((BigDecimal) attributes[APIConstants.UDT_VENDA_RESPONSE_IDX_HTTP_COD]).intValue();

        vendaResponse.setCodigoHttp(codigoHttp);

        if (attributes[APIConstants.UDT_VENDA_RESPONSE_IDX_COD_RETORNO] != null) {
            vendaResponse.setCodigoResultado(((BigDecimal) attributes[APIConstants.UDT_VENDA_RESPONSE_IDX_COD_RETORNO]).intValue());
        }

        if (attributes[APIConstants.UDT_VENDA_RESPONSE_IDX_RETORNO] != null) {
            vendaResponse.setDescricao(attributes[APIConstants.UDT_VENDA_RESPONSE_IDX_RETORNO].toString());
        }

        if (codigoHttp == 201) {
            vendaResponse.setLancamentoId(((BigDecimal) attributes[APIConstants.UDT_VENDA_RESPONSE_IDX_ID_LANCTO]).intValue());
        }

        return vendaResponse;

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

    /**
     * Cria um array do tipo STRUCT para passagem de parÃ¢metro Ã  procedure
     *
     * @param List<VendasRequest> listVendasRequest Vendas
     * @param Connection connection ConexÃ£o
     */
    private Array toArray(List<VendasRequest> listVendasRequest, String customType, Connection connection) throws SQLException {

        final Struct[] structArray = new Struct[listVendasRequest.size()];
        final ListIterator<VendasRequest> iterator = listVendasRequest.listIterator();

        while (iterator.hasNext()) {
            structArray[iterator.nextIndex()] = toStruct(iterator.next(), connection);
        }

        OracleConnection oconn = (OracleConnection) connection.unwrap(oracle.jdbc.OracleConnection.class);
        //Logger.getLogger(EntradasDAOImpl.class.getName()).log(Level.INFO, ""  + oconn);

        return oconn.createOracleArray(getSchema() + "." + customType, structArray);
    }
    
    private Array toArrayNumber(List<VendasRequest> listVendasRequest, String customType, Connection connection) throws SQLException {

        final Number[] structArray = new Number[listVendasRequest.size()];
        final ListIterator<VendasRequest> iterator = listVendasRequest.listIterator();

        while (iterator.hasNext()) {
            if(customType.equals("LISTA_ID_VENDA"))
                structArray[iterator.nextIndex()] = iterator.next().getLancamentoId();

        }

        OracleConnection oconn = (OracleConnection) connection.unwrap(oracle.jdbc.OracleConnection.class);
        //Logger.getLogger(EntradasDAOImpl.class.getName()).log(Level.INFO, ""  + oconn);

        return oconn.createOracleArray(getSchema() + "." + customType, structArray);
    }
    

    private Struct toStruct(VendasRequest vendaRequest, Connection connection) throws SQLException {
        final Object[] attributes = new Object[]{
            vendaRequest.getPfjCode(),
            vendaRequest.getFilialCodigo(),
            vendaRequest.getFilialId(),
            vendaRequest.getAdquirente(),
            vendaRequest.getCNPJ(),
            vendaRequest.getBandeira(),
            vendaRequest.getProduto(),
            wrapDate(vendaRequest.getDataVenda()),
            wrapDate(vendaRequest.getDataPrevisaoVencimento()),
            vendaRequest.getNumeroDaParcela(),
            vendaRequest.getQuantidadeDeParcelas(),
            vendaRequest.getNSU(),
            vendaRequest.getAutorizacao(),
            vendaRequest.getTID(),
            vendaRequest.getValorBruto(),
            vendaRequest.getValorLiquido(),
            vendaRequest.getCliente(),
            vendaRequest.getOrigem(),
            vendaRequest.getKey(),
            vendaRequest.getNotaFiscal()
        };

        return connection.createStruct(getSchema() + ".VENDA_REQUEST", attributes);
    }    
    
    private Struct toStruct_Number(VendasRequest vendaRequest, Connection connection) throws SQLException {
        final Object[] attributes = new Object[]{
            vendaRequest.getLancamentoId()
        };

        return connection.createStruct("NUMBER", attributes);
    }

    /*
     private STRUCT toStruct_v6(VendasRequest vendaRequest, StructDescriptor structDescriptor, Connection connection) throws SQLException {

     final Object[] attributes = new Object[]{
     vendaRequest.getPfjCode(),
     vendaRequest.getFilialId(),
     vendaRequest.getAdquirente(),
     vendaRequest.getCnpjLojaFilial(),
     vendaRequest.getBandeira(),
     vendaRequest.getProduto(),
     wrapDate(vendaRequest.getDataVenda()),
     wrapDate(vendaRequest.getDataPrevisaoVencimento()),
     vendaRequest.getNumeroDaParcela(),
     vendaRequest.getQuantidadeDeParcelas(),
     vendaRequest.getNSU(),
     vendaRequest.getAutorizacao(),
     vendaRequest.getTID(),
     vendaRequest.getValorBruto(),
     vendaRequest.getValorLiquido(),
     vendaRequest.getCliente(),
     vendaRequest.getOrigem(),
     vendaRequest.getKey(),
     vendaRequest.getNotaFiscal()
     };
                
     return new STRUCT(structDescriptor, connection, attributes);
     }
 
    
     private ARRAY toArray_v6(List<VendasRequest> minhaLista, Connection connection) throws SQLException {
     final STRUCT[] structArray = new STRUCT[minhaLista.size()];
     final ListIterator<VendasRequest> iterator = minhaLista.listIterator();
     final StructDescriptor structDescriptor = StructDescriptor.createDescriptor("CFR291.VENDA_REQUEST", connection);
     while (iterator.hasNext()) {
     structArray[iterator.nextIndex()] = toStruct_v6(iterator.next(), structDescriptor, connection);
     }
     final ArrayDescriptor arrayDescriptor = ArrayDescriptor.createDescriptor("VENDA_REQUEST_TABELA", connection);

     return new ARRAY(arrayDescriptor, connection, structArray);
     }    
     */



}
