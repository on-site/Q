package com.on_site.q;

import com.on_site.fn.ElementPredicate;

import org.w3c.dom.Element;

/**
 * This class allows custom pseudo selectors to be created via the
 * Q.expr method.  The caller need only implement the apply method.
 *
 * @author Mike Virata-Stone
 */
public abstract class Pseudo extends com.on_site.frizzle.Pseudo {
    private final String name;

    /**
     * Construct the Pseudo with the given name to use.
     *
     * @param The name of the pseudo.
     */
    public Pseudo(String name) {
        this.name = name;
    }

    /**
     * Retrieve the name of this pseudo selector.
     *
     * @return The name.
     */
    public String name() {
        return name;
    }

    @Override
    public abstract ElementPredicate apply(String argument);
}
