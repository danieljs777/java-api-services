/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.dao;

import br.com.zillius.api.model.Retorno;
import br.com.zillius.api.model.StatusConciliacao;
import br.com.zillius.api.model.TipoFiltroDeRetorno;
import br.com.zillius.api.model.TipoRetorno;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Daniel Interface de acesso a dados de retorno de vendas e recebimentos
 */
public interface RetornoDAO extends BaseDestinationDAO {

    /*
     Retorna uma lista de retorno de vendas
     */
    List<Retorno> obterRetornosDeVendas(String pfjCode, String clienteId, Date dataInicio, Date dataFinal, Integer filialId, StatusConciliacao statusConciliacao, String adquirente, String bandeira, String produto, String key, Integer lancamentoId, TipoFiltroDeRetorno tipoRetorno, int limit, int offset);

    /*
     Retorna uma lista de retornos de recebimentos
     */
    List<Retorno> obterRetornosDeRecebimentos(String pfjCode, String clienteId, Date dataInicio, Date dataFinal, Integer filialId, StatusConciliacao statusConciliacao, String adquirente, String bandeira, String produto, String key, Integer lancamentoId, TipoFiltroDeRetorno tipoRetorno, int limit, int offset);

    long obterCountRetornos(TipoRetorno tipoRetorno, TipoFiltroDeRetorno tipoFiltroRetorno, String pfjCode, String clienteId, Date dataInicio, Date dataFinal, Integer filialId, StatusConciliacao statusConciliacao, String adquirente, String bandeira, String produto, String key, Integer lancamentoId);
}
