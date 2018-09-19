/*
clienteId (int, optional): C�digo do cliente,
filialId (int, optional): C�digo da filial,
key (string, optional): chave de identifica��o do lan�amento,
statusTransacao (int) = ['01 - CONCILIADA' or '02 - CONCILIADA MANUALMENTE' or '03 - PENDENTE(N�O CONCILIADA)' or '04 - TODOS']: C�digo de identifica��o do status da transa��o, seguindo as op��es:,
adquirente (string) = ['01 - CIELO' or '02 - REDECARD' or '03 - AMEX' or '04 - HIPERCARD' or '05 - AURA' or '06 - GETNET' or '07 - CREDSYSTEM' or '08 - SODEXO' or '09 - TICKET' or '99 - OUTROS']: Descri��o da operadora que foi realizada a transa��o, seguindo as op��es:,
lojaFilial (string, optional): CNPJ do estabelecimento,
bandeira (string) = ['01 - MASTERCARD' or '02 - VISA' or '03 - AMEX' or '04 - HIPERCARD' or '05 - AURA' or '06 - DINERSCLUB' or '07 - DISCOVERY' or '99 - OUTROS']: C�digo de identifica��o da bandeira do cart�o, seguindo as op��es:,
tipoTransacao (string) = ['01 - CREDITO' or '02 - DEBITO' or '03 - VALE REFEI��O' or '04 - VALE ALIMENTA��O' or '05 - PR� DATADA']: C�digo de tipo de transa��o, seguindo as op��es:,
numeroDaParcela (int, optional): N�mero da parcela da transa��o,
quantidadeDeParcelas (int, optional): Quantidade de parcelas da transa��o,
nsu (int, optional): C�digo que identifica a transa��o(Quando menor que 15 d�gitos completar com zeros a esquerda).,
autoriza��o (int, optional): C�digo que identifica a transa��o,
tid (int, optional): C�digo que identifica a transa��o,
valorBruto (double, optional): Valor da parcela de venda,
valorLiquido (double, optional): Valor l�quido da parcela(Descontado a taxa de administra��o),
dataVenda (date, optional): Data da Venda no formato DD/MM/YYYY.,
origem (string, optional): campo fixo Adquirente,
dataDePrevisaoDoVencimento (date, optional): Data de previs�o de vencimento da parcela no formato DD/MM/YYYY,
notaFiscal (string, optional): Credit,
referencias (array[Referencia], optional): N�mero de refer�ncia para identificar a transa��o ou qualquer outra informa��o que pode ser enviada para refer�ncia futura,
 */

Select * from CAIFR_RC_LANCTO_ORIGEM LO where ROWNUM <=100;

select * from CAIFR_CC_FILIAL where rownum <=10;

select * from CAIFR_RC_Mod_recon MR;
select * from CAIFR_RC_recon_lancto;

select * from CAIFR_RC_RECONCILIACAO; -- Campo Modalidade_id = 1 � manual != 1 � autom�tico <>

-- JOIN LEFT 

-- campo(+) = campo

select * from caifr_rc_recon ;
 --LO.Reconciliado_Id as statusTransacao, -- Verificar
 
SELECT LO.PFJ_CODIGO as clienteId,
       F.FILIAL_CODIGO as filialId,
       CAIFR_CC_PKG.BUSCA_REFER_VENDA(LO.LANC_ORIG_ID, LO.PFJ_CODIGO , NULL, 'REFERENCIA12') as key,
       Case When R.Modalidade_id = 1 then '02' Else Case     when R.modalidade_id > 1 then '01' Else  Case     when R.modalidade_id = -1 then '03' end end end as statusTransacao,
       LO.Referencia10 as lojaFilial,
       LO.Referencia6 as Adquirente,
       LO.Conta_Contabil as Bandeira,
       Referencia9 as tipoTransacao,
       Substr(LO.Referencia1, 1,2) as numeroDaParcela,
       Substr(LO.Referencia1, 3,2) as quantidadeDeParcelas,
       LO.Documento as nsu,
       LO.Referencia4 as autorizacao,
       LO.Referencia3 as tid,
       Decode(Credito_Debito, 'D', Valor_Contabil, Valor_Contabil * -1) as valorBruto,
       Decode(Credito_Debito, 'D', valor, valor * -1) as valorLiquido,
       LO.Data_Contabil as dataVenda,
       'ADQUIRENTE' as origem,
       LO.data_lancamento as dataDePrevisaoDoVencimento,
       CAIFR_CC_PKG.BUSCA_REFER_VENDA(LO.LANC_ORIG_ID, LO.PFJ_CODIGO , NULL, 'REFERENCIA11') AS notaFiscal,
       'campo 1' as Referencia13_Desc,
       'valor 1' as Referencia13_Valor,
       'campo 2' as Referencia14_Desc,
       'valor 2' as Referencia14_Valor,
       'campo 3' as Referencia15_Desc,
       'valor 3' as Referencia15_Valor,
       'campo 4' as Referencia16_Desc,
       'valor 4' as Referencia16_Valor,
       'campo 5' as Referencia17_Desc,
       'valor 5' as Referencia17_Valor,
       'campo 6' as Referencia18_Desc,
       'valor 6' as Referencia18_Valor,
       'campo 7' as Referencia19_Desc,
       'valor 7' as Referencia19_Valor,
       'campo 8' as Referencia20_Desc,
       'valor 8' as Referencia20_Valor
  FROM CAIFR_RC_LANCTO_ORIGEM LO,
       CAIFR_CC_FILIAL F,
       CAIFR_RC_LANCTO_COMPL LC,
       CAIFR_RC_RECONCILIACAO R
 WHERE LO.REFERENCIA10 = F.FILIAL_CODIGO
   AND LO.Lanc_Orig_id = LC.Lanc_orig_id 
   AND LO.Reconciliado_Id (+)= R.reconciliado_id
   AND LO.PFJ_CODIGO='25.1.1' 
   AND LO.Conta_Id = -5 
   AND LO.DATA_CONTABIL BETWEEN to_date('25/05/2012','dd/mm/yyyy') AND to_date('25/05/2012','dd/mm/yyyy')
   AND ROWNUM <=100;

select * from caifr_rc_itf_campos where refer_relat is not null 
select * from caifr_rc_conta 
select * from caifr_rc_lancto_origem l, caifr_rc_lancto_compl c  where l.lanc_orig_id = c.lanc_orig_id (+) and l.conta_id = 5