Select * from (
          Select rownum rn, t.* 
            from (
                  SELECT B.FILIAL_NOME AS FILIAL_NOME, B.FILIAL_CODIGO AS FILIAL,
                         Count(decode(c.conta_id, a.conta_id,1,NULL)) as Qtd_conta,
                         Count(decode(c.conta_id_par, a.conta_id,1,NULL)) as Qtd_conta_par,
                         SUM(Decode(A.CONTA_ID, C.CONTA_ID, Decode(A.CREDITO_DEBITO,'C',A.VALOR_CONTABIL,-1*A.VALOR_CONTABIL),0)) TOTAL_PREVISTO,
                         SUM(Decode(A.CONTA_ID, C.CONTA_ID_PAR, Decode(A.CREDITO_DEBITO,'C',A.VALOR_CONTABIL,-1*A.VALOR_CONTABIL),0)) TOTAL_PAGO,
                         (SUM(Decode(A.CONTA_ID, C.CONTA_ID_PAR, Decode(A.CREDITO_DEBITO,'C',A.VALOR_CONTABIL,-1*A.VALOR_CONTABIL),0))-
                         SUM(Decode(A.CONTA_ID, C.CONTA_ID, Decode(A.CREDITO_DEBITO,'C',A.VALOR_CONTABIL,-1*A.VALOR_CONTABIL),0))) AS TOTAL_DIFERENCA
                    FROM CAIFR_RC_LANCTO_ORIGEM A,
                         CAIFR_CC_FILIAL B,
                         CAIFR_RC_RECON C
                   WHERE A.CONTA_ID IN (C.CONTA_ID, C.CONTA_ID_PAR)
                     AND A.DATA_CONTABIL BETWEEN to_date('25/05/2012','dd/mm/yyyy') AND to_date('25/05/2012','dd/mm/yyyy')
                     AND A.REFERENCIA10 = B.FILIAL_CODIGO
                     AND B.PFJ_CODIGO   = '25.1.1' -- #CODIGO_EMPRESA#
                     AND C.RECON_ID     = 6
                  /*
                     AND A.REFERENCIA6   LIKE '%' || pREFERENCIA6 || '%'
                     AND A.CONTA_CONTABIL LIKE '%' || pCONTA_CONTABIL || '%'
                     AND B.FILIAL_CODIGO LIKE '%' || pREFERENCIA10 || '%'
                     AND CODIGO_LANCAMENTO NOT IN ('AJCRE','AJDEB','AJU','AND','CANC','CHBK','EST','POS','TAX','ACL','ADJ','TAR',
                                                   'AVSR','CANCVD','DES','DFROC','DFROD','ENT','OUT+','OUT-','REJ','RO','ROAGIP',
                                                   'ROAURA','ROBANES','ROCABA','ROCREDZ','ROCRSYS','RODINE','ROELO','ROEXPL',
                                                   'ROFIC','ROHIPE','ROMAST','ROSICREDI','ROSORO','ROVISA','ROCASA','ROLOSA')
                  */
                   GROUP BY B.FILIAL_NOME, B.FILIAL_CODIGO
                  ) t )
where rn between 1 and 100--- paginação
