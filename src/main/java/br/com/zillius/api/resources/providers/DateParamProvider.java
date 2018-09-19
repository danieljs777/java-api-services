/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.zillius.api.resources.providers;

import br.com.zillius.api.resources.filters.ApiFilter;
import org.glassfish.jersey.server.internal.inject.ExtractorException;

import java.lang.reflect.Type;
import java.lang.annotation.Annotation;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Logger;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;

/**
 * @author Daniel
 */
@Provider
public class DateParamProvider implements ParamConverterProvider {

    private static final Logger logger = Logger.getLogger(ApiFilter.class.getName());

    public static final ThreadLocal<DateFormat> FORMATTER = new ThreadLocal<DateFormat>() {

        @Override
        protected DateFormat initialValue() {
            return DateFormat.getDateInstance(DateFormat.MEDIUM, new Locale("pt", "BR"));
        }
    };

    @Override
    public <T> ParamConverter<T> getConverter(final Class<T> rawType, final Type genericType,
                                              final Annotation[] annotations) {

        return (rawType != Date.class) ? null : new ParamConverter<T>() {

            @Override
            public T fromString(final String value) {
                if (value == null) {
                    throw new IllegalArgumentException("Supplied value is null");
                }
                try {
                    return rawType.cast(FORMATTER.get().parse(value));
                } catch (final ParseException ex) {
                    throw new ExtractorException(ex);
                }
            }

            @Override
            public String toString(final T value) throws IllegalArgumentException {
                return FORMATTER.get().format(value);
            }
        };
    }
}
