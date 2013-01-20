package com.on_site.q;

import com.google.common.collect.Iterators;
import com.on_site.frizzle.Frizzle;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * The Q class implements an interface to interact with XML documents,
 * allowing easy manipulation and querying.
 */
public class Q implements Iterable<Element> {
    private final String selector;
    private final Element[] elements;
    private final Document document;
    private Q previousQ;
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
        this("", asArray(element), null);
    }

    /**
     * Construct with an array of elements.
     *
     * @param elements The elements to select.
     */
    public Q(Element[] elements) {
        this(elements, null);
    }

    private Q(Element[] elements, Document document) {
        this("", Arrays.copyOf(elements, elements.length), document);
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

    private static Q $(Element[] elements, Document document) {
        return new Q(elements, document);
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

    private static Element[] asArray(Element element) {
        if (element == null) {
            return new Element[0];
        }

        return new Element[] { element };
    }

    private List<Element> asList() {
        if (list == null) {
            list = Arrays.asList(elements);
        }

        return list;
    }

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

    private Q setPreviousQ(Q previousQ) {
        this.previousQ = previousQ;
        return this;
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

    /**
     * Get the element at the given index.  If the index is negative,
     * it will start at the end.  If the index is out of range, null
     * will be returned.
     *
     * @param index The index to get.
     * @return The element at the given index, or null.
     */
    public Element get(int index) {
        if (index < 0) {
            index = size() + index;
        }

        if (index < 0 || index >= size()) {
            return null;
        }

        return elements[index];
    }

    /**
     * Retrieve the index of the first element amongst its siblings.
     *
     * @return The index among the siblings of the first element.
     */
    public int index() {
        if (get(0) == null) {
            return -1;
        }

        return first().prevAll().size();
    }

    /**
     * Retrieve the index of the first element in this Q in the given
     * selector.
     *
     * @param selector The selector to index into.
     * @return The index of the first element in this Q inside the
     * given selector.
     */
    public int index(String selector) {
        if (document() == null) {
            return -1;
        }

        return $(selector, document()).index(get(0));
    }

    /**
     * Retrieve the index of the given element in the selected
     * elements of this Q.
     *
     * @param element The element to retrieve the index.
     * @return The index of the element in this Q.
     */
    public int index(Element element) {
        return asList().indexOf(element);
    }

    /**
     * This behaves the same as index(Element), but the first selected
     * element in the given Q is used as the element.
     *
     * @param q The Q to retrieve the index of the first element.
     * @return The index of the first item in the q in this Q.
     */
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

    /**
     * Return the selected elements as an array.
     *
     * @return The selected elements as an array.
     */
    public Element[] toArray() {
        return asList().toArray(new Element[size()]);
    }

    // -------------- Traversing --------------

    // add()
    // addBack()
    // andSelf()
    // children()
    // closest()
    // contents()

    /**
     * End the current stack of filtered elements.  If this is the top
     * level stack, then what is returned depends on what was
     * selected.  If nothing was selected, then an empty Q is
     * returned.  If there is 1 element selected and it is the root
     * document element, then an empty Q is returned.  Otherwise a Q
     * with just the document element is returned.
     *
     * @return The previous Q instance, a Q with just the document, or
     * an empty Q.
     */
    public Q end() {
        if (previousQ != null) {
            return previousQ;
        }

        if (isEmpty()) {
            return $(new Element[0], document());
        }

        if (size() == 1 && get(0) == document().getDocumentElement()) {
            return $(new Element[0], document());
        }

        return $(document());
    }

    /**
     * Obtain a Q object with just the given index selected.
     *
     * @param index The index to retrieve... a negative number would
     * retrieve starting from the end of the list.
     */
    public Q eq(int index) {
        return $(get(index)).setPreviousQ(this);
    }

    // filter()
    // find()

    /**
     * Reduce the selected elements to the first element.
     *
     * @return A new Q with just the first item.
     */
    public Q first() {
        return eq(0);
    }

    // has()
    // is()

    /**
     * Reduce the selected elements to the last element.
     *
     * @return A new Q with just the last item.
     */
    public Q last() {
        return eq(-1);
    }

    // map()
    // next()

    /**
     * Filter the currently selected elements to be just the next
     * sibling elements of all the selected elements.
     *
     * @return A Q of all next siblings of each selected element.
     */
    public Q nextAll() {
        return nextAll(null);
    }

    /**
     * Filter the currently selected elements to be just the next
     * sibling elements of all the selected elements.  Only include
     * elements that match the given selector (if it is non-null).
     *
     * @param selector Selector to filter the returned elements, if
     * non-null.
     * @return A Q of all next siblings of each selected element.
     */
    public Q nextAll(String selector) {
        if (isEmpty()) {
            return $(new Element[0], document()).setPreviousQ(this);
        }

        List<Element> result = new LinkedList<Element>();

        for (Node current : this) {
            while (current.getNextSibling() != null) {
                current = current.getNextSibling();

                if (!(current instanceof Element)) {
                    continue;
                }

                if (selector != null && !frizzle().matchesSelector((Element) current, selector)) {
                    continue;
                }

                result.add((Element) current);
            }
        }

        return $(result.toArray(new Element[result.size()]), document()).setPreviousQ(this);
    }

    // nextUntil()
    // not()
    // offsetParent()
    // parent()
    // parents()
    // parentsUntil()
    // prev()

    /**
     * Filter the currently selected elements to be just the previous
     * sibling elements of all the selected elements.
     *
     * @return A Q of all previous siblings of each selected element.
     */
    public Q prevAll() {
        return prevAll(null);
    }

    /**
     * Filter the currently selected elements to be just the previous
     * sibling elements of all the selected elements.  Only include
     * elements that match the given selector (if it is non-null).
     *
     * @param selector Selector to filter the returned elements, if
     * non-null.
     * @return A Q of all previous siblings of each selected element.
     */
    public Q prevAll(String selector) {
        if (isEmpty()) {
            return $(new Element[0], document()).setPreviousQ(this);
        }

        List<Element> result = new LinkedList<Element>();

        for (Node current : this) {
            while (current.getPreviousSibling() != null) {
                current = current.getPreviousSibling();

                if (!(current instanceof Element)) {
                    continue;
                }

                if (selector != null && !frizzle().matchesSelector((Element) current, selector)) {
                    continue;
                }

                result.add((Element) current);
            }
        }

        return $(result.toArray(new Element[result.size()]), document()).setPreviousQ(this);
    }

    // siblings()
    // slice()

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
        if (o instanceof Q) {
            return o == this;
        }

        return asList().equals(o);
    }

    public int hashCode() {
        return asList().hashCode();
    }

    // -------------- Uncategorized and untested --------------
    // These are private until they are tested and categorized
}
