package com.on_site.fn;

import com.google.common.base.Function;

import org.w3c.dom.Element;

/**
 * Mapping function from Element to an array of Elements.
 *
 * @author Mike Virata-Stone
 */
public interface ElementToElements extends Function<Element, Element[]> {
}
