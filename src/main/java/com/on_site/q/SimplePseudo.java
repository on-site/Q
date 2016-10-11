package com.on_site.q;

import com.on_site.fn.ElementPredicate;

import org.w3c.dom.Element;

/**
 * This class allows simple custom pseudo selectors to be created via
 * the Q.expr method.  The caller need only implement the apply
 * method.  This differs from Pseudo because the argument and element
 * are passed in every time, rather than requiring the return of a
 * predicate per argument.  This can be convenient if there is no
 * expected argument, or the argument needs no preprocessing.
 *
 * @author Mike Virata-Stone
 */
public abstract class SimplePseudo extends Pseudo {
    public SimplePseudo(String name) {
        super(name);
    }

    /**
     * This method implements the logic of the pseudo selector.  It is
     * called with each element along with the argument passed to the
     * selector (if one was given).
     *
     * @param element The element being processed against this pseudo
     * selector.
     * @param argument The argument passed to the pseudo selector, or
     * null if none was supplied.
     * @return Whether or not the selector should apply to the given
     * element.
     */
    public abstract boolean apply(Element element, String argument);

    @Override
    public final ElementPredicate apply(String argument) {
        return new Wrapper(argument);
    }

    private class Wrapper implements ElementPredicate {
        private final String argument;

        private Wrapper(String argument) {
            this.argument = argument;
        }

        @Override
        public boolean apply(Element element) {
            return SimplePseudo.this.apply(element, argument);
        }
    }
}
