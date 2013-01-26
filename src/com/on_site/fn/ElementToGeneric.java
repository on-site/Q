package com.on_site.fn;

import com.google.common.base.Function;

import org.w3c.dom.Element;

/**
 * Mapping function from Element to a generic type.
 *
 * @author Mike Virata-Stone
 */
public interface ElementToGeneric<T> extends Function<Element, T> {
}
