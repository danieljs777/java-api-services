/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.dao;

import br.com.zillius.api.model.Lancamento;
import br.com.zillius.api.model.QueryCustomFilterVO;
import br.com.zillius.api.model.QueryFilterVO;
import java.util.List;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Daniel Interface de acesso a dados de lançamentos
 */
@RequestScoped
public interface LancamentoDAO extends BaseDestinationDAO {

    /*
      Obtem uma lista de lançamentos de vendas agrupadas por Adquirente e bandeira de acordo
      com o pfjCodigo e range de data de venda.
    */    
    List<Lancamento> obterLancamentosDeVendasGeral(QueryFilterVO filters);
    
    /*
      Obtem uma lista de lançamentos de vendas agrupadas por Adquirente, bandeira e filiais de acordo
      com o pfjCodigo e range de data de venda.
    */    
    List<Lancamento> obterLancamentosDeVendasFiliais(QueryFilterVO filters);
    
    /*
      Obtem uma lista de lançamentos de vendas de uma filial específica agrupadas por Adquirente, bandeira de acordo
      com o pfjCodigo e range de data de venda.
    */    
    List<Lancamento> obterLancamentosDeVendasPorFilial(QueryCustomFilterVO filters);
    
    /*
      Obtem uma lista de lançamentos de recebimentos agrupadas por Adquirente e bandeira de acordo
      com o pfjCodigo e range de data de recebimento.
    */
    List<Lancamento> obterLancamentosDeRecebimentosGeral(QueryFilterVO filters);
    
    /*
      Obtem uma lista de lançamentos de recebimentos agrupadas por Adquirente, bandeira e filiais de acordo
      com o pfjCodigo e range de data de recebimento.
    */
    List<Lancamento> obterLancamentosDeRecebimentosFiliais(QueryFilterVO filters);
    
    
    /*
      Obtem uma lista de lançamentos de recebimentos de uma filial específica agrupadas por Adquirente, bandeira de acordo
      com o pfjCodigo e range de data de recebimento.
    */
    List<Lancamento> obterLancamentosDeRecebimentosPorFilial(QueryCustomFilterVO filters);    

        /*
      Obtem uma lista de lançamentos de previsões agrupadas por Adquirente e bandeira de acordo
      com o pfjCodigo e range de data de previsões.
    */
    List<Lancamento> obterLancamentosDePrevisoesGeral(QueryFilterVO filters);
    
    /*
      Obtem uma lista de lançamentos de previsões agrupadas por Adquirente, bandeira e filiais de acordo
      com o pfjCodigo e range de data de previsões.
    */
    List<Lancamento> obterLancamentosDePrevisoesFiliais(QueryFilterVO filters);
    
    
    /*
      Obtem uma lista de lançamentos de previsões de uma filial específica agrupadas por Adquirente, bandeira de acordo
      com o pfjCodigo e range de data de previsões.
    */
    List<Lancamento> obterLancamentosDePrevisoesPorFilial(QueryCustomFilterVO filters);    


}
