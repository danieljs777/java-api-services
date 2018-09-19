/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.dao.queryobject;

import java.util.Date;

/**
 *
 * @author Daniel Interface para DSL de formação de queries de Recebimentoe e Vendas de conciliações com métodos agrupadores
 */
public interface Conciliacoes {

    /*
        Forma a query de acordo com a configuração do DSL
    */
    String toQuery();

    /*
        Filtra e agrupa as conciliações por recebimentos
    */
    Conciliacoes DeRecebimentos();

    /*
        Filtra e agrupa as conciliações por vendas
    */
    Conciliacoes DeVendas();

    /*
        Agrupamento por filiais
    */
    Conciliacoes PorFiliais();

    /*
        Filtra por id de uma filial
    */
    Conciliacoes PorFilialId(String filialId);

    /*
        Filtra por clienteId
    */
    Conciliacoes PorClienteId(String clienteId);

    /*
        Filtra por período
    */
    Conciliacoes PorPeriodo(Date dataInicial, Date dataFinal);
}
