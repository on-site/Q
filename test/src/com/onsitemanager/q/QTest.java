package com.onsitemanager.q;

import static com.onsitemanager.q.Q.$;

import com.onsitemanager.util.TestBase;

import org.testng.annotations.Test;

public class QTest extends TestBase {
    @Test
    public void emptyConstructor() {
        assertSelectedSize($(), 0);
    }

    @Test
    public void documentConstructor() throws Exception {
        Q q = $(document("<test><sub /><sub>content</sub></test>"));
        assertSelectedSize(q, 1);
        assertNodeName(q.get(0), "test");
    }

    @Test
    public void selectorDocumentConstructor() throws Exception {
        Q q = $("sub", document("<test><sub /><sub>content</sub></test>"));
        assertSelectedSize(q, 2);
        assertNodeName(q.get(0), "sub");
        assertNodeName(q.get(1), "sub");
        assertNodeText(q.get(0), "");
        assertNodeText(q.get(1), "content");
    }

    @Test
    public void selectorQConstructor() throws Exception {
        Q q = $("sub", $(document("<test><sub /><sub>content</sub></test>")));
        assertSelectedSize(q, 2);
        assertNodeName(q.get(0), "sub");
        assertNodeName(q.get(1), "sub");
        assertNodeText(q.get(0), "");
        assertNodeText(q.get(1), "content");

        q = $("sub", $("sub", document("<test><sub /><sub>content</sub></test>")));
        assertSelectedSize(q, 0);

        q = $("sub", $("sub", document("<test><sub /><sub>inside: <sub>nested</sub></sub></test>")));
        assertSelectedSize(q, 1);
        assertNodeName(q.get(0), "sub");
        assertNodeText(q.get(0), "nested");
    }
}
