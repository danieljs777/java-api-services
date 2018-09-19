/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.zillius.api.dao.queryobject;

/**
 *
 * @author Daniel Interface de DSL de formação de query para lançamentos
 */

public interface Lancamentos{
    
    /*
        Formação da query
    */
    String toQuery();
    
    /*
        Agrupar por bandeira
    */
    Lancamentos PorBandeira();
    
    /*
        Agrupar por forma de pagamento
    */
    Lancamentos PorFormaDePagamento();
    
    /*
        Agrupar por adquirente
    */
    Lancamentos PorAdquirente();
    
    /*
        Agrupar por parcela
    */
    Lancamentos PorParcela();
    
    /*
        Agrupar por filiais
    */
    Lancamentos PorFiliais();
    
    /*
        Filtrar por id de uma filial
    */
    Lancamentos PorFilialId(String filialId);
    
    /*
        Agrupar por vendas
    */
    Lancamentos DeVendas();
    
    /*
        Agrupar por previsões
    */
    Lancamentos DePrevisoes();
    
    /*
        Agrupar por recebimentos
    */
    Lancamentos DeRecebimentos();
    
    /*
        Filtrar por lançamentos particionados
    */
    Lancamentos Particionados();
    
    /*
        Filtrar por lançamentos não particionados
    */
    Lancamentos NaoParticionados();
    
    /*
        Filtrar por lançamentos particionados
    */
    Lancamentos Particionadas();
    
    /*
        Filtrar por lançamentos não particionados
    */
    Lancamentos NaoParticionadas();
}