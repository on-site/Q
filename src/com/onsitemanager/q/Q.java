package com.onsitemanager.q;

import com.google.common.collect.Iterators;
import com.onsitemanager.util.frizzle.Frizzle;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * The Q class implements an interface to interact with XML documents,
 * allowing easy manipulation and querying.
 */
public class Q implements Iterable<Element> {
    private final String selector;
    private final Element[] elements;
    private final Document document;
    private Frizzle frizzle;
    private List<Element> list;

    // -------------- Constructors --------------

    /**
     * Construct with nothing.
     */
    public Q() {
        this("", new Element[0], null);
    }

    /**
     * Construct with just the document.
     *
     * @param document The document to select.
     */
    public Q(Document document) {
        this("", new Element[] { document.getDocumentElement() }, document);
    }

    /**
     * Construct with just a single element.
     *
     * @param element The element to select.
     */
    public Q(Element element) {
        this("", new Element[] { element }, null);
    }

    /**
     * Construct with an array of elements.
     *
     * @param elements The elements to select.
     */
    public Q(Element[] elements) {
        this("", Arrays.copyOf(elements, elements.length), null);
    }

    /**
     * Select elements from the document based on the given Frizzle
     * selector.
     *
     * @param selector The selector used to select elements.
     * @param document The document to select from.
     */
    public Q(String selector, Document document) {
        this.selector = selector;
        this.document = document;
        this.elements = frizzle().select(selector);
    }

    /**
     * Select elements from the context based on the given Frizzle
     * selector.
     *
     * @param selector The selector used to select elements.
     * @param context The Q context to select from.
     */
    public Q(String selector, Q context) {
        this.selector = selector;
        this.document = context.document();

        List<Element> result = new LinkedList<Element>();

        for (Element element : context.elements) {
            for (Element e : frizzle().select(selector, element)) {
                result.add(e);
            }
        }

        this.elements = result.toArray(new Element[result.size()]);
    }

    private Q(String selector, Element[] elements, Document document) {
        this.selector = selector;
        this.elements = elements;

        if (document == null && elements.length > 0) {
            this.document = elements[0].getOwnerDocument();
        } else {
            this.document = document;
        }
    }

    // -------------- $ factory methods --------------

    /**
     * Select nothing.
     *
     * @return A Q with nothing selected.
     */
    public static Q $() {
        return new Q();
    }

    /**
     * Construct with just the document.
     *
     * @param document The document to select.
     * @return A Q with the document selected.
     */
    public static Q $(Document document) {
        return new Q(document);
    }

    /**
     * Construct with just a single element.
     *
     * @param element The element to select.
     * @return A Q with the element selected.
     */
    public static Q $(Element element) {
        return new Q(element);
    }

    /**
     * Construct with an array of elements.
     *
     * @param elements The elements to select.
     * @return A Q with the elements selected.
     */
    public static Q $(Element[] elements) {
        return new Q(elements);
    }

    /**
     * Select elements from the document based on the given Frizzle
     * selector.
     *
     * @param selector The selector used to select elements.
     * @param document The document to select from.
     * @return A Q with selected items from the document.
     */
    public static Q $(String selector, Document document) {
        return new Q(selector, document);
    }

    /**
     * Select elements from the context based on the given Frizzle
     * selector.
     *
     * @param selector The selector used to select elements.
     * @param context The Q context to select from.
     * @return A Q with selected items from the context.
     */
    public static Q $(String selector, Q context) {
        return new Q(selector, context);
    }

    // -------------- Private convenience methods --------------

    private Frizzle frizzle() {
        if (frizzle != null) {
            return frizzle;
        }

        if (document() == null) {
            throw new IllegalStateException("That operation requires a known document!");
        }

        frizzle = new Frizzle(document());
        return frizzle;
    }

    private List<Element> asList() {
        if (list == null) {
            list = Arrays.asList(elements);
        }

        return list;
    }

    // -------------- Internals --------------

    /**
     * Retrieve the document context for this Q, if there is a known
     * one.
     *
     * @return The document context.
     */
    public Document document() {
        return document;
    }

    // -------------- Miscellaneous --------------

    /**
     * Retrieve an array of all elements selected.
     *
     * @return An array of all elements selected.
     */
    public Element[] get() {
        return toArray();
    }

    public Element get(int index) {
        if (index < 0) {
            return elements[size() + index];
        }

        return elements[index];
    }

    public int index() {
        throw new RuntimeException("TODO");
    }

    public int index(String selector) {
        throw new RuntimeException("TODO");
    }

    public int index(Element element) {
        return asList().indexOf(element);
    }

    public int index(Q q) {
        if (q.isEmpty()) {
            return -1;
        }

        return index(q.get(0));
    }

    /**
     * Returns the number of elements selected.
     *
     * @return The number of elements selected.
     */
    public int size() {
        return elements.length;
    }

    public Element[] toArray() {
        return asList().toArray(new Element[size()]);
    }

    // -------------- Additional utility methods --------------

    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Obtain an Iterator for the elements selected.
     *
     * @return A new iterator for the elements selected.
     */
    @Override
    public Iterator<Element> iterator() {
        return Iterators.forArray(elements);
    }

    public boolean equals(Object o) {
        return asList().equals(o);
    }

    public int hashCode() {
        return asList().hashCode();
    }
}
