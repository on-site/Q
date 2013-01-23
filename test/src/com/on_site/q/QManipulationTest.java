package com.on_site.q;

import static com.on_site.q.Q.$;

import com.google.common.base.Function;
import com.on_site.util.TestBase;

import org.testng.annotations.Test;
import org.w3c.dom.Element;

public class QManipulationTest extends TestBase {
    @Test
    public void text() throws Exception {
        Q q = $("sub", $("<test><sub>Content <b>with some</b> sub content. <endingTag value=\"something\"/></sub><sub>content</sub><sub /></test>"));
        assertEquals(q.text(), "Content with some sub content. ", "Text");
        assertEquals($("sub:eq(2)", q.document()).text(), "", "Text");
        assertEquals($("nothing", q.document()).text(), null, "Text");
    }

    @Test
    public void textText() throws Exception {
        Q q = $("sub", $("<test><sub>Content <b>with some</b> sub content. <endingTag value=\"something\"/></sub><sub>content</sub><sub /></test>"));
        q.text("this is a test of text");
        assertEquals($("sub:eq(0)", q.document()).text(), "this is a test of text", "Text");
        assertEquals($("sub:eq(1)", q.document()).text(), "this is a test of text", "Text");
        assertEquals($("sub:eq(2)", q.document()).text(), "this is a test of text", "Text");
    }

    @Test
    public void textMap() throws Exception {
        Q q = $("sub", document("<test><sub>Content <b>with some</b> sub content. <endingTag value=\"something\"/></sub><sub>content</sub><sub /></test>"));
        final int[] i = new int[] { 0 };
        q.text(new Function<Element, String>() {
            @Override
            public String apply(Element element) {
                int n = i[0]++;
                return "this is test " + n + " of text";
            }
        });
        assertEquals($("sub:eq(0)", q.document()).text(), "this is test 0 of text", "Text");
        assertEquals($("sub:eq(1)", q.document()).text(), "this is test 1 of text", "Text");
        assertEquals($("sub:eq(2)", q.document()).text(), "this is test 2 of text", "Text");
    }

    @Test
    public void textTextWithXmlCharacters() throws Exception {
        // TODO
    }

    @Test
    public void xml() throws Exception {
        Q q = $("sub", document("<test><sub>Content <b>with some</b> sub content. <endingTag value=\"something\"/></sub><sub>content</sub><sub /></test>"));
        assertEquals(q.xml(), "Content <b>with some</b> sub content. <endingTag value=\"something\"/>", "XML");
        assertEquals($("sub:eq(2)", q.document()).xml(), "", "XML");
        assertEquals($("nothing", q.document()).xml(), null, "XML");
    }

    @Test
    public void xmlXml() throws Exception {
        Q q = $("sub", document("<test><sub>Content <b>with some</b> sub content. <endingTag value=\"something\"/></sub><sub>content</sub><sub /></test>"));
        q.xml("this <b>is</b> a test<br id=\"123\"/> of xml<br/>");
        assertEquals($("sub:eq(0)", q.document()).xml(), "this <b>is</b> a test<br id=\"123\"/> of xml<br/>", "XML");
        assertEquals($("sub:eq(1)", q.document()).xml(), "this <b>is</b> a test<br id=\"123\"/> of xml<br/>", "XML");
        assertEquals($("sub:eq(2)", q.document()).xml(), "this <b>is</b> a test<br id=\"123\"/> of xml<br/>", "XML");
    }

    @Test
    public void xmlMap() throws Exception {
        Q q = $("sub", document("<test><sub>Content <b>with some</b> sub content. <endingTag value=\"something\"/></sub><sub>content</sub><sub /></test>"));
        final int[] i = new int[] { 0 };
        q.xml(new Function<Element, String>() {
            @Override
            public String apply(Element element) {
                int n = i[0]++;
                return "this <b>is</b> test " + n + "<br" + n + " id=\"123\"/> of xml<br/>";
            }
        });
        assertEquals($("sub:eq(0)", q.document()).xml(), "this <b>is</b> test 0<br0 id=\"123\"/> of xml<br/>", "XML");
        assertEquals($("sub:eq(1)", q.document()).xml(), "this <b>is</b> test 1<br1 id=\"123\"/> of xml<br/>", "XML");
        assertEquals($("sub:eq(2)", q.document()).xml(), "this <b>is</b> test 2<br2 id=\"123\"/> of xml<br/>", "XML");
    }
}
