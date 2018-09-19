
-- Totalzão
                  SELECT Count(decode(c.conta_id, a.conta_id,1,NULL)) as Qtd_conta,
                         Count(decode(c.conta_id_par, a.conta_id,1,NULL)) as Qtd_conta_par,
                         Sum(Decode(A.CONTA_ID, C.CONTA_ID, Decode(A.CREDITO_DEBITO,'D',A.VALOR_CONTABIL,-1*A.VALOR_CONTABIL),0)) AS VLR_TOTAL_VENDA, --Valor Conta
                         Sum(Decode(A.CONTA_ID, C.CONTA_ID_PAR, Decode(A.CREDITO_DEBITO,'D',A.VALOR_CONTABIL,-1*A.VALOR_CONTABIL),0)) AS VLR_TOTAL_REGISTRADO, --Valor Contra Partida
                         (Sum(Decode(A.CONTA_ID, C.CONTA_ID, Decode(A.CREDITO_DEBITO,'D',A.VALOR_CONTABIL,-1*A.VALOR_CONTABIL),0)) -
                          Sum(Decode(A.CONTA_ID, C.CONTA_ID_PAR, Decode(A.CREDITO_DEBITO,'D',A.VALOR_CONTABIL,-1*A.VALOR_CONTABIL),0)) )AS TOTAL_DIFERENCA -- Valor diferença
                    FROM CAIFR_RC_LANCTO_ORIGEM A,
                         CAIFR_CC_FILIAL B,
                         CAIFR_RC_RECON C
                   WHERE A.CONTA_ID IN (C.CONTA_ID, C.CONTA_ID_PAR)
                     AND A.REFERENCIA10 = B.FILIAL_CODIGO
                     AND A.DATA_CONTABIL BETWEEN to_date('25/05/2012','dd/mm/yyyy') AND to_date('25/05/2012','dd/mm/yyyy')
                     
                  /*
                     AND A.REFERENCIA6   LIKE '%' || pREFERENCIA6 || '%'          -- adquirente
                     AND A.CONTA_CONTABIL LIKE '%' || pCONTA_CONTABIL || '%'      -- Bandeira
                     AND B.FILIAL_CODIGO LIKE '%' || pREFERENCIA10 || '%'         -- filial
                  */
                     AND B.PFJ_CODIGO='25.1.1' --#CODIGO_EMPRESA#
                     AND C.RECON_ID = 5;


---- Bandeira e Adquirente.

Select * from (
          Select rownum rn, t.* 
            from (
                  SELECT Count(decode(c.conta_id, a.conta_id,1,NULL)) as Qtd_conta,
                         Count(decode(c.conta_id_par, a.conta_id,1,NULL)) as Qtd_conta_par,
                         A.Conta_Contabil as Bandeira,
                         A.Referencia6 as Adquirente,
                         Sum(Decode(A.CONTA_ID, C.CONTA_ID, Decode(A.CREDITO_DEBITO,'D',A.VALOR_CONTABIL,-1*A.VALOR_CONTABIL),0)) AS VLR_TOTAL_VENDA, --Valor Conta
                         Sum(Decode(A.CONTA_ID, C.CONTA_ID_PAR, Decode(A.CREDITO_DEBITO,'D',A.VALOR_CONTABIL,-1*A.VALOR_CONTABIL),0)) AS VLR_TOTAL_REGISTRADO, --Valor Contra Partida
                         (Sum(Decode(A.CONTA_ID, C.CONTA_ID, Decode(A.CREDITO_DEBITO,'D',A.VALOR_CONTABIL,-1*A.VALOR_CONTABIL),0)) -
                          Sum(Decode(A.CONTA_ID, C.CONTA_ID_PAR, Decode(A.CREDITO_DEBITO,'D',A.VALOR_CONTABIL,-1*A.VALOR_CONTABIL),0)) )AS TOTAL_DIFERENCA -- Valor diferença
                    FROM CAIFR_RC_LANCTO_ORIGEM A,
                         CAIFR_CC_FILIAL B,
                         CAIFR_RC_RECON C
                   WHERE A.CONTA_ID IN (C.CONTA_ID, C.CONTA_ID_PAR)
                     AND A.DATA_CONTABIL BETWEEN to_date('25/05/2012','dd/mm/yyyy') AND to_date('25/05/2012','dd/mm/yyyy')
                     AND A.REFERENCIA10 = B.FILIAL_CODIGO
                  /*
                     AND A.REFERENCIA6   LIKE '%' || pREFERENCIA6 || '%'          -- adquirente
                     AND A.CONTA_CONTABIL LIKE '%' || pCONTA_CONTABIL || '%'      -- Bandeira
                     AND B.FILIAL_CODIGO LIKE '%' || pREFERENCIA10 || '%'         -- filial
                  */
                     AND B.PFJ_CODIGO='25.1.1' --#CODIGO_EMPRESA#
                     AND C.RECON_ID = 5
                   GROUP BY A.Conta_Contabil, A.Referencia6
                  ) t )
where rn between 1 and 100;--- paginação

---- Por filial, Bandeira e Adquirente.

Select * from (
          Select rownum rn, t.* 
            from (
                  SELECT B.FILIAL_NOME AS FILIAL_NOME, B.FILIAL_CODIGO AS FILIAL,
                         Count(decode(c.conta_id, a.conta_id,1,NULL)) as Qtd_conta,
                         Count(decode(c.conta_id_par, a.conta_id,1,NULL)) as Qtd_conta_par,
                         A.Conta_Contabil as Bandeira,
                         A.Referencia6 as Adquirente,
                         Sum(Decode(A.CONTA_ID, C.CONTA_ID, Decode(A.CREDITO_DEBITO,'D',A.VALOR_CONTABIL,-1*A.VALOR_CONTABIL),0)) AS VLR_TOTAL_VENDA, --Valor Conta
                         Sum(Decode(A.CONTA_ID, C.CONTA_ID_PAR, Decode(A.CREDITO_DEBITO,'D',A.VALOR_CONTABIL,-1*A.VALOR_CONTABIL),0)) AS VLR_TOTAL_REGISTRADO, --Valor Contra Partida
                         (Sum(Decode(A.CONTA_ID, C.CONTA_ID, Decode(A.CREDITO_DEBITO,'D',A.VALOR_CONTABIL,-1*A.VALOR_CONTABIL),0)) -
                          Sum(Decode(A.CONTA_ID, C.CONTA_ID_PAR, Decode(A.CREDITO_DEBITO,'D',A.VALOR_CONTABIL,-1*A.VALOR_CONTABIL),0)) )AS TOTAL_DIFERENCA -- Valor diferença
                    FROM CAIFR_RC_LANCTO_ORIGEM A,
                         CAIFR_CC_FILIAL B,
                         CAIFR_RC_RECON C
                   WHERE A.CONTA_ID IN (C.CONTA_ID, C.CONTA_ID_PAR)
                     AND A.DATA_CONTABIL BETWEEN to_date('25/05/2012','dd/mm/yyyy') AND to_date('25/05/2012','dd/mm/yyyy')
                     AND A.REFERENCIA10 = B.FILIAL_CODIGO
                  /*
                     AND A.REFERENCIA6   LIKE '%' || pREFERENCIA6 || '%'          -- adquirente
                     AND A.CONTA_CONTABIL LIKE '%' || pCONTA_CONTABIL || '%'      -- Bandeira
                     AND B.FILIAL_CODIGO LIKE '%' || pREFERENCIA10 || '%'         -- filial
                  */
                     AND B.PFJ_CODIGO='25.1.1' --#CODIGO_EMPRESA#
                     AND C.RECON_ID = 5
                   GROUP BY B.FILIAL_NOME, B.FILIAL_CODIGO,Conta_Contabil, Referencia6
                  ) t )
where rn between 1 and 100--- paginação




SELECT Count(decode(c.conta_id, a.conta_id,1,NULL)) as Qtd_conta, 
A.Referencia6 as Adquirente, A.Conta_Contabil as Bandeira,    
Count(decode(c.conta_id_par, a.conta_id,1,NULL)) as Qtd_conta_par,   
Sum(Decode(A.CONTA_ID, C.CONTA_ID, Decode(A.CREDITO_DEBITO,'D',A.VALOR_CONTABIL,-1*A.VALOR_CONTABIL),0)) AS VLR_TOTAL_VENDA,  
Sum(Decode(A.CONTA_ID, C.CONTA_ID_PAR, Decode(A.CREDITO_DEBITO,'D',A.VALOR_CONTABIL,-1*A.VALOR_CONTABIL),0)) AS VLR_TOTAL_REGISTRADO, 
(Sum(Decode(A.CONTA_ID, C.CONTA_ID, Decode(A.CREDITO_DEBITO,'D',A.VALOR_CONTABIL,-1*A.VALOR_CONTABIL),0)) -          
Sum(Decode(A.CONTA_ID, C.CONTA_ID_PAR, Decode(A.CREDITO_DEBITO,'D',A.VALOR_CONTABIL,-1*A.VALOR_CONTABIL),0)) )AS TOTAL_DIFERENCA  
FROM CAIFR_RC_LANCTO_ORIGEM A,                     
CAIFR_CC_FILIAL B,                     
CAIFR_RC_RECON C  
WHERE A.CONTA_ID IN (C.CONTA_ID, C.CONTA_ID_PAR)          
AND A.REFERENCIA10 = B.FILIAL_CODIGO  
AND C.RECON_ID = 5
AND B.PFJ_CODIGO='25.1.1'  
AND A.DATA_CONTABIL BETWEEN to_date('25/05/2012','dd/mm/yyyy') AND to_date('25/05/2012','dd/mm/yyyy')  
AND B.FILIAL_CODIGO ='011'   group by A.Referencia6, A.Conta_Contabil  ;

