package com.on_site.q;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.on_site.frizzle.Frizzle;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

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

    private Q $select() {
        return $(new Element[0], document()).setPreviousQ(this);
    }

    private Q $select(Element element) {
        return $(element).setPreviousQ(this);
    }

    private Q $select(Collection<Element> elements) {
        return $(elements.toArray(new Element[elements.size()]), document()).setPreviousQ(this);
    }

    private Q setPreviousQ(Q previousQ) {
        this.previousQ = previousQ;
        return this;
    }

    // -------------- Attributes --------------

    /**
     * Retrieve the value of the given attribute name for the first
     * selected element.  Returns null if there are no selected
     * elements, or if the first element doesn't have the given
     * attribute.
     *
     * @param name The name of the attribute to retrieve.
     * @return The retrieved attribute value.
     */
    public String attr(String name) {
        if (isEmpty()) {
            return null;
        }

        if (!get(0).hasAttribute(name)) {
            return null;
        }

        return frizzle().attr(get(0), name);
    }

    /**
     * Set the attribute to the given value with the given name for
     * all selected elements.
     *
     * @param name The name of the attribute to set.
     * @param value The new value of the attribute.
     * @return This Q.
     */
    public Q attr(String name, String value) {
        if (!isEmpty()) {
            for (Element element : this) {
                element.setAttribute(name, value);
            }
        }

        return this;
    }

    /**
     * For each key/value pair of the given map, set the attribute
     * with the key value as the name to the value of the key/value
     * pair, for all selected elements.
     *
     * @param attributes A hash of attribute name/value pairs to set.
     * @return This Q.
     */
    public Q attr(Map<String, String> attributes) {
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            attr(entry.getKey(), entry.getValue());
        }

        return this;
    }

    /**
     * Set the attribute to the value returned from the map function
     * for each selected element for the given attribute name.
     *
     * @param name The name of the attribute to set.
     * @param map A mapping function of element to attribute value.
     * @return This Q.
     */
    public Q attr(String name, Function<Element, String> map) {
        for (Element element : this) {
            element.setAttribute(name, map.apply(element));
        }

        return this;
    }

    /**
     * Remove the attribute with the given name from all selected
     * elements.
     *
     * @param name The name of the attribute to remove.
     * @return This Q.
     */
    public Q removeAttr(String name) {
        for (Element element : this) {
            element.removeAttribute(name);
        }

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

    // -------------- Manipulation --------------

    /**
     * Retrieve the xml for the first selected element as a string
     * (just the content, not including the element itself).  If there
     * are no elements selected, this returns null.  If the element
     * has no children (even if it is a singular element like
     * &lt;example/&gt;), an empty string is returned.
     *
     * @return The content of the first selected element.
     * @throws XmlException If there is a problem transforming the
     * nested xml as a string.
     */
    public String xml() throws XmlException {
        if (isEmpty()) {
            return null;
        }

        NodeList nodes = get(0).getChildNodes();

        if (nodes.getLength() == 0) {
            return "";
        }

        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            StringWriter result = new StringWriter();
            StreamResult output = new StreamResult(result);

            for (int i = 0; i < nodes.getLength(); i++) {
                transformer.transform(new DOMSource(nodes.item(i)), output);
            }

            return result.toString();
        } catch (TransformerException e) {
            throw new XmlException(e);
        }
    }

    /**
     * Set the content of each selected element to be the given xml
     * string.  The current Q is returned.
     *
     * @param xml The xml to set as the content for all selected
     * elements.
     * @return This Q.
     * @throws XmlException If there is a problem parsing the xml or
     * inserting it into each element.
     */
    public Q xml(String xml) throws XmlException {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader("<root>" + xml + "</root>")));
            NodeList nodes = document.getDocumentElement().getChildNodes();

            for (Element element : this) {
                element.setTextContent("");

                for (int i = 0; i < nodes.getLength(); i++) {
                    Node node = document().importNode(nodes.item(i), true);
                    element.appendChild(node);
                }
            }
        } catch (IOException e) {
            throw new XmlException(e);
        } catch (ParserConfigurationException e) {
            throw new XmlException(e);
        } catch (SAXException e) {
            throw new XmlException(e);
        }

        return this;
    }

    /**
     * Set the content of each selected element to be the xml string
     * returned from applying the given map function with the element.
     * The current Q is returned.
     *
     * Note that the element is not cleared until after the xml string
     * has been returned (so you may inspect the xml contents within
     * the function).
     *
     * @param map A mapping function of element to xml string.
     * @return This Q.
     * @throws XmlException If there is a problem parsing the xml or
     * inserting it into each element.
     */
    public Q xml(Function<Element, String> map) throws XmlException {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

            for (Element element : this) {
                String xml = map.apply(element);
                Document document = builder.parse(new InputSource(new StringReader("<root>" + xml + "</root>")));
                NodeList nodes = document.getDocumentElement().getChildNodes();
                element.setTextContent("");

                for (int i = 0; i < nodes.getLength(); i++) {
                    Node node = document().importNode(nodes.item(i), true);
                    element.appendChild(node);
                }
            }
        } catch (IOException e) {
            throw new XmlException(e);
        } catch (ParserConfigurationException e) {
            throw new XmlException(e);
        } catch (SAXException e) {
            throw new XmlException(e);
        }

        return this;
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
     * @return A new Q with the retrieved element.
     */
    public Q eq(int index) {
        return $select(get(index));
    }

    /**
     * Filter these selected elements down to those that match the
     * given selector.
     *
     * @param selector The selector to filter elements by.
     * @return A new Q with the filtered elements.
     */
    public Q filter(final String selector) {
        return filter(new Predicate<Element>() {
            @Override
            public boolean apply(Element element) {
                return frizzle().matchesSelector(element, selector);
            }
        });
    }

    /**
     * Filter these selected elements down to those that pass the
     * given predicate (ie, the predicate returns true when the
     * element is passed to apply).
     *
     * @param predicate The predicate to filter elements by.
     * @return A new Q with the filtered elements.
     */
    public Q filter(Predicate<Element> predicate) {
        List<Element> result = new LinkedList<Element>();

        for (Element element : this) {
            if (predicate.apply(element)) {
                result.add(element);
            }
        }

        return $select(result);
    }

    /**
     * Filter these selected elements to just the given element (if it
     * is included).
     *
     * @param element The element to filter down to.
     * @return A new Q with the filtered elements.
     */
    public Q filter(final Element element) {
        return filter(new Predicate<Element>() {
            @Override
            public boolean apply(Element e) {
                return e == element;
            }
        });
    }

    /**
     * Filter these selected elements down to those that are included
     * in the given Q instance.
     *
     * @param q A set of elements to filter down to.
     * @return A new Q with the filtered elements.
     */
    public Q filter(final Q q) {
        return filter(new Predicate<Element>() {
            @Override
            public boolean apply(Element element) {
                return q.asList().contains(element);
            }
        });
    }

    /**
     * Find all children of the selected elements that match the given
     * selector.
     *
     * @param selector The selector to find elements of.
     * @return A new Q with the found elements.
     */
    public Q find(String selector) {
        List<Element> result = new LinkedList<Element>();

        for (Element context : this) {
            for (Element element : frizzle().select(selector, context)) {
                result.add(element);
            }
        }

        return $select(result);
    }

    /**
     * Find all children of the selected elements that are one of the
     * selected elements in the given q.
     *
     * @param q A Q of elements to find in the selected elements.
     * @return A new Q with the found elements.
     */
    public Q find(Q q) {
        List<Element> result = new LinkedList<Element>();

        for (Element context : this) {
            for (Element element : q) {
                if (frizzle.contains(context, element)) {
                    result.add(element);
                }
            }
        }

        return $select(result);
    }

    /**
     * Returns a Q with the given element if it is a child of one of
     * the selected elements (otherwise an empty Q).
     *
     * @param q A Q of elements to find in the selected elements.
     * @return A new Q with the found elements.
     */
    public Q find(Element element) {
        List<Element> result = new LinkedList<Element>();

        for (Element context : this) {
            if (frizzle.contains(context, element)) {
                result.add(element);
            }
        }

        return $select(result);
    }

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
            return $select();
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

        return $select(result);
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
            return $select();
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

        return $select(result);
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
