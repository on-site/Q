package com.onsitemanager.q;

import com.google.common.collect.Iterators;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * The Q class implements an interface to interact with XML documents,
 * allowing easy manipulation and querying.
 */
public class Q implements List<Element> {
    private final String selector;
    private final Element[] elements;
    private final Document document;
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

    private Q(String selector, Element[] elements, Document document) {
        this.selector = selector;
        this.elements = elements;
        this.document = document;
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
     * Select elements from the document based on the given Frizzle
     * selector.
     *
     * @param selector The selector used to select elements.
     * @param document The document to select from.
     */
    public static Q $(String selector, Document document) {
        throw new RuntimeException("TODO");
    }

    // -------------- List implemented methods --------------

    private List<Element> asList() {
        if (list == null) {
            list = Arrays.asList(elements);
        }

        return list;
    }

    /**
     * Returns the number of elements selected.
     *
     * @return The number of elements selected.
     */
    @Override
    public int size() {
        return elements.length;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean contains(Object o) {
        return asList().contains(o);
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

    @Override
    public Object[] toArray() {
        return asList().toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return asList().toArray(a);
    }

    @Override
    public boolean add(Element e) {
        throw new UnsupportedOperationException("Q objects are immutable.");
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Q objects are immutable.");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return asList().containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends Element> c) {
        throw new UnsupportedOperationException("Q objects are immutable.");
    }

    @Override
    public boolean addAll(int index, Collection<? extends Element> c) {
        throw new UnsupportedOperationException("Q objects are immutable.");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Q objects are immutable.");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Q objects are immutable.");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Q objects are immutable.");
    }

    @Override
    public boolean equals(Object o) {
        return asList().equals(o);
    }

    @Override
    public int hashCode() {
        return asList().hashCode();
    }

    @Override
    public Element get(int index) {
        return elements[index];
    }

    @Override
    public Element set(int index, Element element) {
        throw new UnsupportedOperationException("Q objects are immutable.");
    }

    @Override
    public void add(int index, Element element) {
        throw new UnsupportedOperationException("Q objects are immutable.");
    }

    @Override
    public Element remove(int index) {
        throw new UnsupportedOperationException("Q objects are immutable.");
    }

    @Override
    public int indexOf(Object o) {
        return asList().indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return asList().lastIndexOf(o);
    }

    @Override
    public ListIterator<Element> listIterator() {
        return asList().listIterator();
    }

    @Override
    public ListIterator<Element> listIterator(int index) {
        return asList().listIterator(index);
    }

    @Override
    public List<Element> subList(int fromIndex, int toIndex) {
        return asList().subList(fromIndex, toIndex);
    }
}
