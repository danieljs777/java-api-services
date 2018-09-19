select r.Descricao, lan.* 
  from CAIFR_RC_LANCTO_ORIGEM lan, 
       CaifR_Rc_Recon r  
 where lan.conta_id IN(r.conta_id, r.conta_id_par)
   and rowNUm <= 100; 

SELECT * FROM CAIFR_RC_LANCTO_ORIGEM WHERE RECONCILIADO_ID=757525; -- deltahes de um lan�amento para ver concilia��o

select * from CAIFR_RC_Lancto_Origem LO
 where LO.Lanc_Orig_Id between '329970' and '329985';

select Valor_Contabil as ValorContaA from CAIFR_RC_LANCTO_ORIGEM LO,
(select Reconciliado_Id from CAIFR_RC_LANCTO_ORIGEM where Reconciliado_ID >0 and rowNum <=100) conciliados
where LO.reconciliado_id = conciliados.Reconciliado_id and Conta_id = 5
Union
select Valor_Contabil as ValorContaB from CAIFR_RC_LANCTO_ORIGEM LO,
(select Reconciliado_Id from CAIFR_RC_LANCTO_ORIGEM where Reconciliado_ID >0 and rowNum <=100) conciliados
where LO.reconciliado_id = conciliados.Reconciliado_id and Conta_id = -5;

select f.Filial_Codigo, f.Filial_Nome, Lo.* from CAIFR_RC_LANCTO_ORIGEM LO, 
caifr_cc_filial f
where LO.referencia10 = f.Filial_codigo and Conta_Id = -7;

--CAIFR_CC_ESTAB_SEQ sequence de estabelecimento
select * from CAIFR_CC_OPERADORA;

select CAIFR_CC_ESTAB_SEQ.nextVal from dual;

select * from CAIFR_CC_FILIAL_ESTAB_BIUD;

select  CAIFR_CONEXAO.USUARIO_CONECTADO from dual;

select * from CAIFR_COnexao_Sessao;

insert into caifr_cc_filial_estab values(CAIFR_CC_ESTAB_SEQ.nextVal, '25.1.1', '011',1,'2312312321', 'ADMIN', sysdate, 'ADMIN',sysdate, NULL) ;
insert into Caifr_cc_filial_terminal values(58,'2232434','POS', 'ADMIN', sysdate ,NULL, NULL, 'Descr', 33343, NULL, NULL);
insert into Caifr_cc_taxas values (58,'MASTERCARD','CREDITO',sysdate,NULL,200,20000,1,5,3,'ADMIN',sysdate,NULL,NULL);


select * from caifr_cc_filial f;
select * from caifr_cc_filial_terminal; -- estab_id
select * from caifr_cc_filial_estab; -- Filial_Codigo
select * from caifr_cc_taxas; -- Estab_Id - Taxa por Bandeira por produto e por parcela(Qtd_par_inicial, Qtd_par_final)
select * from Caifr_empresa_uen where rowNum <= 100; -- Empresa

select Decode(Credito_Debito, 'C', Valor_Contabil, Valor_Contabil * -1) from CAIFR_RC_LANCTO_ORIGEM;

select * from CAIFR_RC_Recon;

select * from CAIFR_RC_Lancto_compl;

/*

Cliente cria pfj_codigo

filial -> estabelecimento -> taxa
filial -> estabelecimento -> terminal

Diferen�a entre Pagamento e Recebimento ->  Pagamento � o lan�amento aberto e Recebimento � o lan�amento fechado.

Lan�amento -> conciliado e n�o conciliado
Retorno -> s� conciliado 

Lan�amento -> conciliado e n�o conciliado
Retorno -> s� conciliado 

Flag_Split = 'S' � particionado e 'N' � um lan�amento.

Status de concilia��o = Reconciliado_Id = -1 � pendente, >0 conciliado - diferencia lan�amento de retorno.

Venda - valor cont�bil � o valor bruto e o campo valor � valor l�quido
Recebimento - valor cont�bil � o valor l�quido e o valor � o valor bruto.

Venda =  D+  C-
Recebimento = D+ C-
Pagamento = C+ D-

Credito_Debito = sinal do valorContabil

H14LI0H14LI0 = SENHA DO BANCO.

PFJ_Codigo = Dominio.Grupo.Empresa

Origem_PK = OrigemPK OrigemSistema e ContaId - identifica��o de migra��o de lan�amento entre contas.

Referencia 1 = 2 d�gitos para parcela e 2 d�gitos para quantidade
Referencia 2 = N�mero do resumo
Rederencia 3 = Numero do cart�o ou TID
Referencia 4 = Numero da autoriza��o
Referencia 5 = N�mero do estabelecimento
Referencia 6 = Adquirente
Referencia 7 = Tipo de Captura
Referencia 8 = Numero do terminal
Referencia 9 = Produto
Referencia 10 = c�digo da loja/filial
Referencia 11 = Notafiscal. Compl.

Documento = NSU.


*/


-- FormaPagto - D�bito ou Cr�dito
-- bandeira   - 
-- Cielo

select  To_Date('01/06/12', 'dd/MM/RR') from dual;

-- Total de lan�amentos de venda

select SUM(Decode(Credito_Debito, 'C', Valor_Contabil * -1, Valor_Contabil)) as valor,  
       SUBSTR(Referencia25,1,4) as Banco, 
       SUBSTR(Referencia25,5,5) as Agencia, 
       Substr(LC.Referencia25,10,14) as ContaBancaria
from CAIFR_RC_LANCTO_ORIGEM LO,
     CAIFR_RC_LANCTO_COMPL LC
where LO.Lanc_Orig_id = LC.Lanc_orig_id
and Data_Contabil Between To_Date('01/06/12', 'dd/MM/RR') and To_Date('05/06/12', 'dd/MM/RR')
and pfj_codigo = '25.1.1'
and flag_split = 'N'
and Conta_Id = -5
group by SUBSTR(Referencia25,1,4), SUBSTR(Referencia25,5,5), Substr(LC.Referencia25,10,14);


---- Total de lan�amentos de venda por Parcelas

select SUM(Decode(Credito_Debito, 'C', Valor_Contabil * -1, Valor_Contabil)) as valor, 
       Substr(Referencia1,-2) as parcelas, 
       SUBSTR(Referencia25,1,4) as Banco, 
       SUBSTR(Referencia25,5,5) as Agencia, 
       Substr(LC.Referencia25,10,14) as ContaBancaria
from CAIFR_RC_LANCTO_ORIGEM LO,
     CAIFR_RC_LANCTO_COMPL LC
where LO.Lanc_Orig_id = LC.Lanc_orig_id
and Data_Contabil Between To_Date('01/06/12', 'dd/MM/RR') and To_Date('05/06/12', 'dd/MM/RR')
and pfj_codigo = '25.1.1'
and flag_split = 'N'
and Conta_Id = -5
group by Substr(Referencia1,-2), SUBSTR(Referencia25,1,4), SUBSTR(Referencia25,5,5), Substr(LC.Referencia25,10,14);


-- Total de lan�amentos de venda por Parcela, Bandeira, Forma de pagamento e Adquirente.

select SUM(Decode(Credito_Debito, 'C', Valor_Contabil * -1, Valor_Contabil)) as valor, 
       Substr(Referencia1,-2) as parcelas, 
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

-- -- Total de lan�amentos de venda por Parcela, Bandeira, Forma de pagamento, Adquirente agrupados por Filial.

select SUM(Decode(LO.Credito_Debito, 'C', LO.Valor_Contabil * -1, LO.Valor_Contabil)) as valor, 
       Substr(LO.Referencia1,-2) as parcelas, 
       SUBSTR(LC.Referencia25,1,4) as Banco, 
       SUBSTR(LC.Referencia25,5,5) as Agencia, 
       Substr(LC.Referencia25,10,14) as ContaBancaria, 
       LO.referencia9 as FormaPagamento,
       LO.Conta_Contabil as Bandeira,
       LO.Referencia6 as Adquirente,
       F.Filial_Nome
from CAIFR_RC_LANCTO_ORIGEM LO,
     CAIFR_RC_LANCTO_COMPL LC,
     CAIFR_CC_Filial F
where LO.Lanc_Orig_id = LC.Lanc_orig_id
  and LO.referencia10 = F.Filial_codigo
  and Lo.Data_Contabil Between To_Date('01/06/12', 'dd/MM/RR') and To_Date('05/06/12', 'dd/MM/RR')
  and LO.pfj_codigo = '25.1.1'
  and Lo.flag_split = 'N'
  and Lo.Conta_Id = -5
group by Substr(LO.Referencia1,-2), SUBSTR(LC.Referencia25,1,4), SUBSTR(LC.Referencia25,5,5), Substr(LC.Referencia25,10,14), LO.Referencia9, LO.Conta_Contabil, LO.Referencia6, F.Filial_Nome
order by F.Filial_Nome;


select * from CAIFR_RC_LANCTO_COMPL;

 select SUM(Decode(LO.Credito_Debito, 'C', LO.Valor_Contabil * -1, LO.Valor_Contabil)) as valor,  
       SUBSTR(LC.Referencia25,1,4) as Banco, 
       SUBSTR(LC.Referencia25,5,5) as Agencia, 
       Substr(LC.Referencia25,10,14) as ContaBancaria, LO.Referencia6 as Adquirente, LO.Conta_Contabil as Bandeira, LO.referencia9 as FormaPagamento, Substr(LO.Referencia1,-2) as parcelas , F.Filial_Nome as Filial from CAIFR_RC_LANCTO_ORIGEM LO,
     CAIFR_RC_LANCTO_COMPL LC , CAIFR_CC_Filial F where LO.Lanc_Orig_id = LC.Lanc_orig_id
and LO.Data_Contabil Between To_Date('01/05/12', 'dd/MM/RR') and To_Date('01/07/12', 'dd/MM/RR')      and LO.pfj_codigo = '25.1.1' and LO.flag_split = 'N' and LO.Conta_Id = -5 
and LO.referencia10 = F.Filial_codigo
group by SUBSTR(LC.Referencia25,1,4), SUBSTR(LC.Referencia25,5,5), Substr(LC.Referencia25,10,14), LO.Referencia6, LO.Conta_Contabil, LO.Referencia9, Substr(LO.Referencia1,-2), F.Filial_Nome;

  





