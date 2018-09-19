/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.zillius.api.resources.adapters;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author Daniel Classe adaptadora para moeda
 */
public class CurrencyAdapter extends XmlAdapter<String, BigDecimal> {
    
    private static final ThreadLocal<NumberFormat> FORMATTER = new ThreadLocal<NumberFormat>() {

        @Override
        protected NumberFormat initialValue() {
            final DecimalFormat decimalFormat = new DecimalFormat("#0.00", new DecimalFormatSymbols(Locale.US));
            decimalFormat.setParseBigDecimal(true);
            
            return decimalFormat;
        }
        
    };

    @Override
    public BigDecimal unmarshal(String v) throws Exception {
        return (BigDecimal) FORMATTER.get().parse(v);
    }
    
    @Override
    public String marshal(BigDecimal v) throws Exception {
        return FORMATTER.get().format(v);
    }
    
}
