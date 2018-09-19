/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.zillius.api.resources.adapters;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author Daniel
 */
public class DateAdapter extends XmlAdapter<String, Date> {
    
    private static final ThreadLocal<DateFormat> FORMATTER = new ThreadLocal<DateFormat>() {

        @Override
        protected DateFormat initialValue() {
           
            return DateFormat
                    .getDateInstance(DateFormat.MEDIUM, new Locale("pt", "BR"));
        }
        
    };

    @Override
    public Date unmarshal(String v) throws Exception {
        return FORMATTER.get().parse(v);
    }
    
    @Override
    public String marshal(Date v) throws Exception {
        return FORMATTER.get().format(v);
    }
    
}
