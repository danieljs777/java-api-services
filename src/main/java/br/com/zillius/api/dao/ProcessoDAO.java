/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.dao;

import br.com.zillius.api.model.Processo;

/**
 *
 * @author Daniel
 */
public interface ProcessoDAO extends BaseDestinationDAO {
    
    Processo getProcessStatus(long processId);
}
