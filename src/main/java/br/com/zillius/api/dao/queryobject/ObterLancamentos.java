/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.dao.queryobject;

import java.util.Date;

/**
 *
 * @author Daniel Classe de configuração de DSL de lançamentos
 */
public class ObterLancamentos extends LancamentosQueryBase implements Lancamentos {

    /*
     Construtor inicial para configuração básica de DSL
     */
    public ObterLancamentos(String schema, Date dataInicial, Date dataFinal, String clienteId) {
        super(schema);
        construirQueryBasica(dataInicial, dataFinal, clienteId);
    }

    /*
     Construtor de incremento de Value Object
     */
    private ObterLancamentos(String schema, StringBuilder campos, StringBuilder estruturaBasica, StringBuilder filtros, StringBuilder agrupamentosBasicos, String dataInicial, String dataFinal, String clienteId) {
        super(schema);
        incrementarQuery(campos, estruturaBasica, filtros, agrupamentosBasicos, dataInicial, dataFinal, clienteId);
    }

    /*
     Construir o objeto Lancamentos
     */
    private Lancamentos construirLancamentos() {
        return new ObterLancamentos(this.schema, campos, estruturaBasica, filtros, agrupamentosBasicos, dataInicial, dataFinal, clienteId);
    }

    /*
     Construir a query
     */
    @Override
    public String toQuery() {

        montarQuery();
        return this.query.toString();
    }

    /*
     Agrupar por bandeira
     */
    @Override
    public Lancamentos PorBandeira() {

        campos.append("      , LO.Conta_Contabil as Bandeira ");
        agrupamentosBasicos.append(", LO.Conta_Contabil ");

        return construirLancamentos();
    }

    /*
     Agrupar por forma de pagamentos
     */
    @Override
    public Lancamentos PorFormaDePagamento() {

        campos.append("      , LO.referencia9 as FormaPagamento ");
        agrupamentosBasicos.append(", LO.Referencia9 ");

        return construirLancamentos();
    }

    /*
     Agrupar por adquirente
     */
    @Override
    public Lancamentos PorAdquirente() {

        campos.append("      , LO.Referencia6 as Adquirente");
        agrupamentosBasicos.append(", LO.Referencia6 ");

        return construirLancamentos();
    }

    /*
     Agrupar por parcela
     */
    @Override
    public Lancamentos PorParcela() {

        campos.append("     , Substr(LO.Referencia1,-2) as parcelas ");
        agrupamentosBasicos.append(", Substr(LO.Referencia1,-2) ");

        return construirLancamentos();
    }

    /*
     Agrupar por filiais
     */
    @Override
    public Lancamentos PorFiliais() {

        campos.append("      , F.Filial_Nome as Filial ");
        estruturaBasica.append("      , CAIFR_CC_Filial F      ");
        filtros.append("      and LO.referencia10 = F.Filial_codigo      ");
        agrupamentosBasicos.append(", F.Filial_Nome ");

        return construirLancamentos();
    }

    /*
     Filtrar por id de uma filial
     */
    @Override
    public Lancamentos PorFilialId(String filialCodigo) {

        campos.append("      , F.Filial_Nome as Filial ");
        estruturaBasica.append("      , CAIFR_CC_Filial F      ");
        filtros.append("      and LO.referencia10 = F.Filial_codigo      ");
        filtros.append("      and F.Filial_codigo = ");
        filtros.append(filialCodigo);
        filtros.append("      ");
        agrupamentosBasicos.append(", F.Filial_Nome ");

        return construirLancamentos();
    }

    /*
     Agrupar por vendas
     */
    @Override
    public Lancamentos DeVendas() {

        campos.append(",  SUM(Decode(LO.Credito_Debito, 'C', LO.Valor_Contabil * -1, LO.Valor_Contabil)) as valor      ");
        filtros.append("      and LO.Conta_Id = -5 ");

        return construirLancamentos();
    }

    /*
     Agrupar por previsões
     */
    @Override
    public Lancamentos DePrevisoes() {

        campos.append(", SUM(Decode(LO.Credito_Debito, 'C', LO.Valor_Contabil, LO.Valor_Contabil  * -1)) as valor      ");
        filtros.append("      and LO.Conta_Id = -6 ");

        return construirLancamentos();
    }

    /*
     Agrupar por recebimentos
     */
    @Override
    public Lancamentos DeRecebimentos() {

        campos.append(", SUM(Decode(LO.Credito_Debito, 'C', LO.Valor_Contabil * -1, LO.Valor_Contabil)) as valor      ");
        filtros.append("      and LO.Conta_Id = -7 ");

        return construirLancamentos();
    }

    /*
     Filtrar por lançamentos particinados
     */
    @Override
    public Lancamentos Particionados() {

        filtros.append("      and LO.flag_split = 'S'      ");

        return construirLancamentos();
    }

    /*
     Filtrar por lançamentos não particionados
     */
    @Override
    public Lancamentos NaoParticionados() {

        filtros.append("      and LO.flag_split = 'N'      ");

        return construirLancamentos();
    }

    /*
     Filtrar por lançamentos particionados
     */
    @Override
    public Lancamentos Particionadas() {
        return Particionados();
    }

    /*
     Filtrar por lançamentos não particionados
     */
    @Override
    public Lancamentos NaoParticionadas() {
        return NaoParticionados();
    }

}
