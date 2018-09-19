/*
 * Copyright © 2014 zillius. All rights reserved.
 *
 * Created by a.accioly on 7/8/14 8:58 PM
 */

package br.com.zillius.api.exceptions;

/**
 * Sinaliza que uma propriedade do sistema está faltando. Exceção disparada na ausência de propriedades obrigatórias na
 * árvore JNDI, arquivos de propriedades, etc.
 */
public class MissingPropertyException extends IllegalStateException {

    /**
     * Constrói uma {@code MissingPropertyException} sem mensagem de detalhes.
     *
     * <p>Uma mensagem de detalhes é uma String que descreve essa exceção em particular.
     */
    public MissingPropertyException() {
    }

    /**
     * Constrói uma {@code MissingPropertyException} com a mensagem de detalhes especificada.
     *
     * <p>Uma mensagem de detalhes é uma String que descreve essa exceção em particular.
     *
     * @param s a String que contém a mensagem detalhada
     */
    public MissingPropertyException(String s) {
        super(s);
    }

    /**
     * Constrói uma nova exceção com a mensagem de detalhes especificada e uma causa.
     *
     * <p>Note que a mensagem de detalhes associada com a {@code cause} <i>não</i> é automaticamente incorporada na
     * mensagem que detalha essa exceção.
     *
     * @param message a mensagem de detalhes (que posteriormente pode ser recuperada pelo método
     *                {@link Throwable#getMessage()}).
     * @param cause   a causa (que posteriormente pode ser recuperada pelo método
     *                {@link Throwable#getCause()}). (O valor <tt>null</tt> é permitido,
     *                e indica que a causa é inexistente ou desconhecida.)
     */
    public MissingPropertyException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constrói uma nova exceção com a causa especificada e uma mensagem de detalhes que segue a lógica
     * <tt>cause == null ? null : cause.toString()</tt>
     *
     * <p>Esse construtor é útil para exceções que são pouco mais do que <i>Wrappers</i> para outros lançáveis (por
     * exemplo, {@link java.security.PrivilegedActionException}).
     *
     * @param cause a causa (que posteriormente pode ser recuperada pelo método
     *              {@link Throwable#getCause()}). (O valor <tt>null</tt> é permitido,
     *              e indica que a causa é inexistente ou desconhecida.)
     */
    public MissingPropertyException(Throwable cause) {
        super(cause);
    }
}
