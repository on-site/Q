package com.on_site.fn;

import com.google.common.base.Function;
import com.on_site.q.Q;

import org.w3c.dom.Element;

/**
 * Mapping function from Element to a Q.
 *
 * @author Mike Virata-Stone
 */
public interface ElementToQ extends Function<Element, Q> {
}
