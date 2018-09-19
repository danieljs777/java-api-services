
-- FormaPagto
-- bandeira
-- Cielo

select  To_Date('01/06/12', 'dd/MM/RR') from dual;

select SUM(Decode(Credito_Debito, 'C', Valor_Contabil * -1, Valor_Contabil)) as valor, 
       Substr(Referencia1,-2) as QuantidadeDeParcelas, 
       SUBSTR(Referencia25,1,4) as Banco, 
       SUBSTR(Referencia25,5,5) as Agencia, 
       Substr(LC.Referencia25,10,14) as ContaBancaria, 
       referencia9 as FormaPagamento,
       Conta_Contabil as Bandeira,
       Referencia6 as Adquirente
from CAIFR_RC_LANCTO_ORIGEM LO,
     CAIFR_RC_LANCTO_COMPL LC
where LO.Lanc_Orig_id = LC.Lanc_orig_id
and Data_Contabil Between To_Date('01/06/12', 'dd/MM/RR') and To_Date('05/06/12', 'dd/MM/RR')
and pfj_codigo = '25.1.1'
and flag_split = 'N'
and Conta_Id = -5
group by Substr(Referencia1,-2), SUBSTR(Referencia25,1,4), SUBSTR(Referencia25,5,5), Substr(LC.Referencia25,10,14), Referencia9, Conta_Contabil, Referencia6;

select  
       SUBSTR(LC.Referencia25,1,4) as Banco, 
       SUBSTR(LC.Referencia25,5,5) as Agencia, 
       Substr(LC.Referencia25,10,14) as ContaBancaria 
,  SUM(Decode(LO.Credito_Debito, 'C', LO.Valor_Contabil * -1, LO.Valor_Contabil)) as valor 

 , LO.Referencia6 as Adquirente
 , LO.Conta_Contabil as Bandeira 
 , LO.referencia9 as FormaPagamento 
, Substr(LO.Referencia1,-2) as parcelas from CAIFR_RC_LANCTO_ORIGEM LO,
     CAIFR_RC_LANCTO_COMPL LC  
 where LO.Lanc_Orig_id = LC.Lanc_orig_id
and LO.Data_Contabil Between To_Date('01/05/12', 'dd/MM/RR') and To_Date('01/07/12', 'dd/MM/RR')   
   and LO.pfj_codigo = '25.1.1' 
 and LO.Conta_Id = -5
 and LO.flag_split = 'N' 
 group by SUBSTR(LC.Referencia25,1,4), SUBSTR(LC.Referencia25,5,5), Substr(LC.Referencia25,10,14) , LO.Referencia6, LO.Conta_Contabil , LO.Referencia9 , Substr(LO.Referencia1,-2);

  select              SUBSTR(LC.Referencia25,1,4) as Banco,             SUBSTR(LC.Referencia25,5,5) as Agencia,             Substr(LC.Referencia25,10,14) as ContaBancaria      ,  SUM(Decode(LO.Credito_Debito, 'C', LO.Valor_Contabil * -1, LO.Valor_Contabil)) as valor            , LO.Referencia6 as Adquirente      , LO.Conta_Contabil as Bandeira       , LO.referencia9 as FormaPagamento      , Substr(LO.Referencia1,-2) as parcelas from CAIFR_RC_LANCTO_ORIGEM LO,          CAIFR_RC_LANCTO_COMPL LC        where LO.Lanc_Orig_id = LC.Lanc_orig_id     and LO.Data_Contabil Between To_Date('01/05/12', 'dd/MM/RR') and To_Date('01/07/12', 'dd/MM/RR')           and LO.pfj_codigo = '25.1.1'       and LO.Conta_Id = -5      and LO.flag_split = 'N'       group by SUBSTR(LC.Referencia25,1,4), SUBSTR(LC.Referencia25,5,5), Substr(LC.Referencia25,10,14) , LO.Referencia6, LO.Conta_Contabil , LO.Referencia9 , Substr(LO.Referencia1,-2);

  select  
       SUBSTR(LC.Referencia25,1,4) as Banco, 
       SUBSTR(LC.Referencia25,5,5) as Agencia, 
       Substr(LC.Referencia25,10,14) as ContaBancaria 
,  SUM(Decode(LO.Credito_Debito, 'C', LO.Valor_Contabil * -1, LO.Valor_Contabil)) as valor 

 , LO.Referencia6 as Adquirente
 , LO.Conta_Contabil as Bandeira 
 , LO.referencia9 as FormaPagamento 
, Substr(LO.Referencia1,-2) as parcelas 
 , F.Filial_Nome as Filial from CAIFR_RC_LANCTO_ORIGEM LO,
     CAIFR_RC_LANCTO_COMPL LC 
 , CAIFR_CC_Filial F 
 
 where LO.Lanc_Orig_id = LC.Lanc_orig_id
and LO.Data_Contabil Between To_Date('01/05/12', 'dd/MM/RR') and To_Date('01/07/12', 'dd/MM/RR')   
   and LO.pfj_codigo = '25.1.1' 
 and LO.flag_split = 'N' 

 and LO.Conta_Id = -5
 and LO.referencia10 = F.Filial_codigo 
 group by SUBSTR(LC.Referencia25,1,4), SUBSTR(LC.Referencia25,5,5), Substr(LC.Referencia25,10,14) , LO.Referencia6, LO.Conta_Contabil , LO.Referencia9 , Substr(LO.Referencia1,-2), F.Filial_Nome;




