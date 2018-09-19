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
 * @author Daniel Classe Base de formação de query via DSL para lançamentos
 */
public class LancamentosQueryBase {

    protected String schema;

    public LancamentosQueryBase(String schema) {
        this.schema = schema;
    }

    /*
     Campos - SELECT
     */
    protected StringBuilder campos;

    /*
     Estrutura básica cláusula FROM
     */
    protected StringBuilder estruturaBasica;

    /*
     Filtros Cláusula WHERE
     */
    protected StringBuilder filtros;

    /*
     Agrupamentos - GROUP BY
     */
    protected StringBuilder agrupamentosBasicos;

    /*
     Query
     */
    protected StringBuilder query;

    /*
     Data Inicial
     */
    protected String dataInicial;

    /*
     Data Final
     */
    protected String dataFinal;

    /*
     Cliente id
     */
    protected String clienteId;

    /*
     Método responsável por construir a query básica de lançamentos juntando campos, estutura básica, filtros, agrupamentos
     */
    protected void construirQueryBasica(Date dataInicial, Date dataFinal, String clienteId) {

        campos = new StringBuilder();
        estruturaBasica = new StringBuilder();
        filtros = new StringBuilder();
        agrupamentosBasicos = new StringBuilder();
        query = new StringBuilder();

        SimpleDateFormat dt1 = new SimpleDateFormat("dd/MM/yy");

        this.dataInicial = dt1.format(dataInicial);
        this.dataFinal = dt1.format(dataFinal);
        this.clienteId = clienteId;

        montarCamposBasicos();
        montarEstruturaBasica();
        montarFiltrosBasicos();
        montarAgrupamentosBasicos();
    }

    /*
     incrementar a query adicionando filtros e outras partes
     */
    protected void incrementarQuery(StringBuilder campos, StringBuilder estruturaBasica, StringBuilder filtros, StringBuilder agrupamentosBasicos, String dataInicial, String dataFinal, String clienteId) {

        this.campos = campos;
        this.estruturaBasica = estruturaBasica;
        this.filtros = filtros;
        this.agrupamentosBasicos = agrupamentosBasicos;
        this.query = new StringBuilder();

        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
        this.clienteId = clienteId;
    }

    /*
     Montar campos básicos - SELECT
     */
    protected void montarCamposBasicos() {
        campos.append("  select       "
                + "       SUBSTR(LC.Referencia25,1,4) as Banco,      "
                + "       SUBSTR(LC.Referencia25,5,5) as Agencia,      "
                + "       Substr(LC.Referencia25,10,14) as ContaBancaria      ");
    }

    /*
     Montar estrutura básica FROM
     */
    protected void montarEstruturaBasica() {

        estruturaBasica.append("from " + this.schema + ".CAIFR_RC_LANCTO_ORIGEM LO,     "
                + "     " + this.schema + ".CAIFR_RC_LANCTO_COMPL LC ");
    }

    /*
     Montar filtros básicos - WHERE
     */
    protected void montarFiltrosBasicos() {

        filtros.append("       where LO.Lanc_Orig_id = LC.Lanc_orig_id     "
                + "and LO.Data_Contabil Between To_Date('");
        filtros.append(dataInicial);
        filtros.append("', 'dd/MM/RR') and To_Date('");
        filtros.append(dataFinal);
        filtros.append("', 'dd/MM/RR')"
                + "           and LO.pfj_codigo = '");
        filtros.append(clienteId);
        filtros.append("' ");
    }

    /*
     Montar agrupamentos básicos - GROUP BY
     */
    protected void montarAgrupamentosBasicos() {
        agrupamentosBasicos.append(" group by SUBSTR(LC.Referencia25,1,4), SUBSTR(LC.Referencia25,5,5), Substr(LC.Referencia25,10,14) ");
    }

    /*
     Montar a query
     */
    protected void montarQuery() {
        query.append(campos.toString());
        query.append(estruturaBasica.toString());
        query.append(filtros.toString());
        query.append(agrupamentosBasicos.toString());
        //query.append(";");
    }
}
