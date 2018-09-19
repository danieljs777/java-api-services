/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.dao.queryobject;

/**
 *
 * @author Daniel Classe base do DSL de formação de queries para conciliação
 */
public class ConciliacoesQueryBase {

    protected String schema;

    public ConciliacoesQueryBase(String schema) {
        this.schema = schema;
    }

    /*
     Campos do SELECT
     */
    protected StringBuilder campos;

    /*
     Cláusulas FROM e parte dos JOINS básicos 
     */
    protected StringBuilder estruturaBasica;

    /*
     Filtros - Cláusulas Where
     */
    protected StringBuilder filtros;

    /*
     Agrupamentos
     */
    protected StringBuilder agrupamentosBasicos;

    /*
     Query
     */
    protected StringBuilder query;

    /*
     Data inicial
     */
    protected String dataInicial;

    /*
     Data Final
     */
    protected String dataFinal;

    /*
     Id do cliente
     */
    protected String clienteId;

    /*
     Método responsável por orquestrar a construção da query básica de conciliação
     */
    protected void construirQueryBasica() {

        campos = new StringBuilder();
        estruturaBasica = new StringBuilder();
        filtros = new StringBuilder();
        agrupamentosBasicos = new StringBuilder();
        query = new StringBuilder();

        montarCamposBasicos();
        montarEstruturaBasica();
        montarFiltrosBasicos();
        montarAgrupamentosBasicos();
    }

    /*
     Montar a parte básica da cláusula SELECT
     */
    protected void montarCamposBasicos() {
        campos.append("SELECT Count(decode(c.conta_id, a.conta_id,1,NULL)) as Qtd_conta,"
                + "                          A.Referencia6 as Adquirente, A.Conta_Contabil as Bandeira, "
                + "                         Count(decode(c.conta_id_par, a.conta_id,1,NULL)) as Qtd_conta_par,"
                + "                         Sum(Decode(A.CONTA_ID, C.CONTA_ID, Decode(A.CREDITO_DEBITO,'D',A.VALOR_CONTABIL,-1*A.VALOR_CONTABIL),0)) AS VLR_TOTAL_VENDA, "
                + "                         Sum(Decode(A.CONTA_ID, C.CONTA_ID_PAR, Decode(A.CREDITO_DEBITO,'D',A.VALOR_CONTABIL,-1*A.VALOR_CONTABIL),0)) AS VLR_TOTAL_REGISTRADO,"
                + "                         (Sum(Decode(A.CONTA_ID, C.CONTA_ID, Decode(A.CREDITO_DEBITO,'D',A.VALOR_CONTABIL,-1*A.VALOR_CONTABIL),0)) -"
                + "                          Sum(Decode(A.CONTA_ID, C.CONTA_ID_PAR, Decode(A.CREDITO_DEBITO,'D',A.VALOR_CONTABIL,-1*A.VALOR_CONTABIL),0)) )AS TOTAL_DIFERENCA  ");
    }

    /*
     Montar a parte básica da cláusula FROM
     */
    protected void montarEstruturaBasica() {

        estruturaBasica.append("FROM " + this.schema + ".CAIFR_RC_LANCTO_ORIGEM A, "
                + "  " + this.schema + "                .CAIFR_CC_FILIAL B, "
                + "  " + this.schema + "                .CAIFR_RC_RECON C ");
    }

    /*
     Montar a parte básica da cláusula WHERE
     */
    protected void montarFiltrosBasicos() {

        filtros.append(" WHERE A.CONTA_ID IN (C.CONTA_ID, C.CONTA_ID_PAR)  "
                + "                     AND A.REFERENCIA10 = B.FILIAL_CODIGO  ");
    }

    /*
     Montar a parte básica dos agrupamentos
     */
    protected void montarAgrupamentosBasicos() {
        agrupamentosBasicos.append(" group by A.Referencia6, A.Conta_Contabil  ");
    }

    /*
     Juntar SELEC, FROM, WHERE, GROUP BY formando a query
     */
    protected void montarQuery() {
        query.append(campos.toString());
        query.append(estruturaBasica.toString());
        query.append(filtros.toString());
        query.append(agrupamentosBasicos.toString());
    }
}
