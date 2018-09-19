/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.dao.queryobject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Daniel Classe de DSL de Conciliação
 */

public class ObterConciliacoes extends ConciliacoesQueryBase implements Conciliacoes {

    public ObterConciliacoes(String schema) {
        super(schema);
        construirQueryBasica();
    }

    /*
        Construtor com paramentros para gerar outro objeto a partir de dados já coletados aplicando o padrão Value Object
    */
    private ObterConciliacoes(String schema, StringBuilder campos, StringBuilder estruturaBasica, StringBuilder filtros, StringBuilder agrupamentosBasicos) {

        super(schema);
        
        this.campos = campos;
        this.estruturaBasica = estruturaBasica;
        this.filtros = filtros;
        this.agrupamentosBasicos = agrupamentosBasicos;
        
        this.query = new StringBuilder();
    }

    /*
        Construir o objeto conciliações com as configuração de query
    */
    private Conciliacoes construirConciliacoes() {
        return new ObterConciliacoes(this.schema, campos, estruturaBasica, filtros, agrupamentosBasicos);
    }

    /*
        Agrupar por recebimentos
    */
    @Override
    public Conciliacoes DeRecebimentos() {
        filtros.append("  AND C.RECON_ID = 6  ");
        return construirConciliacoes();
    }

    /*
        Agrupar por vendas
    */
    @Override
    public Conciliacoes DeVendas() {
        filtros.append("  AND C.RECON_ID = 5  ");
        return construirConciliacoes();
    }

    /*
        Agrupar por filiais
    */
    @Override
    public Conciliacoes PorFiliais() {
        campos.append("  , B.FILIAL_NOME AS FILIAL_NOME, B.FILIAL_CODIGO AS FILIAL  ");
        agrupamentosBasicos.append(" , B.FILIAL_NOME, B.FILIAL_CODIGO ");

        return construirConciliacoes();
    }

    /*
        Filtrar por id da filial
    */
    @Override
    public Conciliacoes PorFilialId(String filialCodigo) {

        filtros.append(" AND B.FILIAL_CODIGO ='");
        filtros.append(filialCodigo);
        filtros.append("'  ");

        return construirConciliacoes();
    }

    /*
        Filtrar por id do cliente
    */
    @Override
    public Conciliacoes PorClienteId(String clienteId) {

        filtros.append(" AND B.PFJ_CODIGO='");
        filtros.append(clienteId);
        filtros.append("'  ");

        return construirConciliacoes();
    }

    /*
        Filtrar por período
    */
    @Override
    public Conciliacoes PorPeriodo(Date dataInicial, Date dataFinal) {

        SimpleDateFormat dt1 = new SimpleDateFormat("dd/MM/yyyy");

        filtros.append("  AND A.DATA_CONTABIL BETWEEN to_date('");
        filtros.append(dt1.format(dataInicial));
        filtros.append("','dd/mm/yyyy') AND to_date('");
        filtros.append(dt1.format(dataFinal));
        filtros.append("','dd/mm/yyyy')  ");

        return construirConciliacoes();
    }

    /*
        Montar a query
    */
    @Override
    public String toQuery() {
        montarQuery();
        return this.query.toString();
    }

}
