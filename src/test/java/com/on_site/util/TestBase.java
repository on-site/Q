package com.on_site.util;

import com.on_site.q.Q;
import com.on_site.util.DOMUtil;

import java.io.File;

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

    public static void assertNodeText(Node node, String expected) {
        assertEquals(node.getTextContent(), expected, "Node text");
    }

    public static File tempFile() throws Exception {
        File temp = File.createTempFile("q_test", ".xml");
        temp.deleteOnExit();
        return temp;
    }
}
