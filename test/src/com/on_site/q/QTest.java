package com.on_site.q;

import static com.on_site.q.Q.$;

import com.google.common.io.Files;
import com.on_site.util.TestBase;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.StringReader;

import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
    public void elementConstructor() throws Exception {
        Document doc = document("<test><sub /><sub>content</sub></test>");
        Q q = $($("sub", doc).get(0));
        assertSelectedSize(q, 1);
        assertNodeName(q.get(0), "sub");
        assertEquals(q.document(), doc, "The element's document");

        q = $($("sub", doc).get());
        assertSelectedSize(q, 2);
        assertNodeName(q.get(0), "sub");
        assertNodeName(q.get(1), "sub");
        assertEquals(q.document(), doc, "The element's document");

        q = $((Element) null);
        assertSelectedSize(q, 0);
    }

    @Test
    public void selectorDocumentConstructor() throws Exception {
        Q q = $("sub", document("<test><sub /><sub>content</sub></test>"));
        assertSelectedSize(q, 2);
        assertNodeName(q.get(0), "sub");
        assertNodeName(q.get(1), "sub");
        assertNodeText(q.get(0), "");
        assertNodeText(q.get(1), "content");

        q = $("sub", document("<test><sub>Some <sub>inner content</sub></sub><sub>content</sub></test>"));
        assertSelectedSize(q, 3);
        assertNodeName(q.get(0), "sub");
        assertNodeName(q.get(1), "sub");
        assertNodeName(q.get(2), "sub");
        assertNodeText(q.get(0), "Some inner content");
        assertNodeText(q.get(1), "inner content");
        assertNodeText(q.get(2), "content");
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

    @Test
    public void xmlQConstructor() throws Exception {
        Q q = $("<test><sub /><sub>content</sub></test>");
        assertSelectedSize(q, 1);
        assertNodeName(q.get(0), "test");

        q = $("This <sub />is <child>some <sub />content</child> that is technically invalid xml.");
        assertSelectedSize(q, 2);
        assertNodeName(q.get(0), "sub");
        assertNodeName(q.get(1), "child");
        assertNodeText(q.get(1), "some content");
        assertNodeName(q.get(0).getParentNode(), "root");
        assertNodeText(q.get(0).getParentNode(), "This is some content that is technically invalid xml.");
    }

    public void xmlFileQConstructor() throws Exception {
        File in = tempFile();
        Files.write("<test><sub /><sub>content</sub></test>".getBytes(), in);
        Q q = $(in);
        assertSelectedSize(q, 1);
        assertNodeName(q.get(0), "test");

        in = tempFile();
        Files.write("This <sub />is <child>some <sub />content</child> that is technically invalid xml.".getBytes(), in);
        q = $(in);
        assertSelectedSize(q, 2);
        assertNodeName(q.get(0), "sub");
        assertNodeName(q.get(1), "child");
        assertNodeText(q.get(1), "some content");
        assertNodeName(q.get(0).getParentNode(), "root");
        assertNodeText(q.get(0).getParentNode(), "This is some content that is technically invalid xml.");
    }

    public void xmlInputStreamQConstructor() throws Exception {
        ByteArrayInputStream in = new ByteArrayInputStream("<test><sub /><sub>content</sub></test>".getBytes());
        Q q = $(in);
        in.close();
        assertSelectedSize(q, 1);
        assertNodeName(q.get(0), "test");

        in = new ByteArrayInputStream("This <sub />is <child>some <sub />content</child> that is technically invalid xml.".getBytes());
        q = $(in);
        in.close();
        assertSelectedSize(q, 2);
        assertNodeName(q.get(0), "sub");
        assertNodeName(q.get(1), "child");
        assertNodeText(q.get(1), "some content");
        assertNodeName(q.get(0).getParentNode(), "root");
        assertNodeText(q.get(0).getParentNode(), "This is some content that is technically invalid xml.");
    }

    public void xmlReaderQConstructor() throws Exception {
        StringReader in = new StringReader("<test><sub /><sub>content</sub></test>");
        Q q = $(in);
        in.close();
        assertSelectedSize(q, 1);
        assertNodeName(q.get(0), "test");

        in = new StringReader("This <sub />is <child>some <sub />content</child> that is technically invalid xml.");
        q = $(in);
        in.close();
        assertSelectedSize(q, 2);
        assertNodeName(q.get(0), "sub");
        assertNodeName(q.get(1), "child");
        assertNodeText(q.get(1), "some content");
        assertNodeName(q.get(0).getParentNode(), "root");
        assertNodeText(q.get(0).getParentNode(), "This is some content that is technically invalid xml.");
    }
}
