package com.onsitemanager.util;

import com.onsitemanager.q.Q;
import com.onsitemanager.util.DOMUtil;

import org.testng.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class TestBase extends Assert {
    public static Document document(String xml) throws SAXException {
        return DOMUtil.documentFromString(xml);
    }

    public static void assertSelectedSize(Q q, int expected) {
        assertEquals(q.size(), expected, "Size");
        int count = 0;

        for (Element element : q) {
            count++;
        }

        assertEquals(q.size(), expected, "Count in for loop");
    }

    public static void assertNodeName(Node node, String expected) {
        assertEquals(node.getNodeName(), expected, "Node name");
    }
}
