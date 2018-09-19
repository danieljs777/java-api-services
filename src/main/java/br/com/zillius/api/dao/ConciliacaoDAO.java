/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.dao;

import br.com.zillius.api.model.Conciliacao;
import br.com.zillius.api.model.DrillDownType;
import br.com.zillius.api.model.QueryCustomFilterVO;
import br.com.zillius.api.model.QueryFilterVO;
import br.com.zillius.api.model.ResourceType;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Daniel Interface de acesso a dados de conciliação
 */
public interface ConciliacaoDAO extends BaseDestinationDAO{
           
    /*
      Obtem uma lista de conciliações de vendas agrupadas por Adquirente e bandeira de acordo
      com o pfjCodigo e range de data de venda.
    */    
    List<Conciliacao> obterConciliacoesDeVendasGeral(QueryFilterVO filters);
    
    /*
      Obtem uma lista de conciliações de vendas agrupadas por Adquirente, bandeira e filiais de acordo
      com o pfjCodigo e range de data de venda.
    */    
    List<Conciliacao> obterConciliacoesDeVendasFiliais(QueryFilterVO filters);
    
    /*
      Obtem uma lista de conciliações de vendas de uma filial específica agrupadas por Adquirente, bandeira de acordo
      com o pfjCodigo e range de data de venda.
    */    
    List<Conciliacao> obterConciliacoesDeVendasPorFilial(QueryCustomFilterVO filters);
    
    /*
      Obtem uma lista de conciliações de recebimentos agrupadas por Adquirente e bandeira de acordo
      com o pfjCodigo e range de data de recebimento.
    */
    List<Conciliacao> obterConciliacoesDeRecebimentosGeral(QueryFilterVO filters);
    
    /*
      Obtem uma lista de conciliações de recebimentos agrupadas por Adquirente, bandeira e filiais de acordo
      com o pfjCodigo e range de data de recebimento.
    */
    List<Conciliacao> obterConciliacoesDeRecebimentosFiliais(QueryFilterVO filters);
    
    
    /*
      Obtem uma lista de conciliações de recebimentos de uma filial específica agrupadas por Adquirente, bandeira de acordo
      com o pfjCodigo e range de data de recebimento.
    */
    List<Conciliacao> obterConciliacoesDeRecebimentosPorFilial(QueryCustomFilterVO filters);

}
