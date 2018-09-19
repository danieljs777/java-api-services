@XmlJavaTypeAdapters({
    @XmlJavaTypeAdapter(value = DateAdapter.class, type = Date.class),
    @XmlJavaTypeAdapter(value = CurrencyAdapter.class, type = BigDecimal.class)
})
package br.com.zillius.api.model;

import br.com.zillius.api.resources.adapters.CurrencyAdapter;
import br.com.zillius.api.resources.adapters.DateAdapter;
import java.math.BigDecimal;
import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
