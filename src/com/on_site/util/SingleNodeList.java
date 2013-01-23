package com.on_site.util;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * A node list of a single DOM node that is passed in at construction
 * time.
 *
 * @author Mike Virata-Stone
 */
public class SingleNodeList implements NodeList {
    private final Node node;

    /**
     * Construct the list with the given node.
     *
     * @param node The node to construct with.
     */
    public SingleNodeList(Node node) {
        this.node = node;
    }

    @Override
    public Node item(int index) {
        if (index != 0) {
            return null;
        }

        return node;
    }

    @Override
    public int getLength() {
        return 1;
    }
}
