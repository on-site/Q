package com.on_site.q;

import static com.on_site.q.Q.$;

import com.on_site.fn.ElementToString;
import com.on_site.util.TestBase;

import org.testng.annotations.Test;
import org.w3c.dom.Element;

public class QManipulationTest extends TestBase {
    @Test
    public void appendXml() throws Exception {
        Q q = $("sub", $("<test><sub></sub><sub /> a <sib/> <sub>Some <i>content</i> here</sub></test>"));
        q.append("<b>this is a test</b>");
        assertEquals(q.write(), "<test><sub><b>this is a test</b></sub><sub><b>this is a test</b></sub> a <sib/> <sub>Some <i>content</i> here<b>this is a test</b></sub></test>", "XML");

        q = $("sub", $("<test><sub>one</sub> <sub>two</sub></test>"));
        q.append("<three>four</three> and <five/>");
        assertEquals(q.write(), "<test><sub>one<three>four</three> and <five/></sub> <sub>two<three>four</three> and <five/></sub></test>", "XML");
    }

    @Test
    public void appendElement() throws Exception {
        Q q = $("sub", $("<test><sub></sub><sub /> a <sib/> <sub>Some <i>content</i> here</sub></test>"));
        q.append($("<b>this is a test</b>").get(0));
        assertEquals(q.write(), "<test><sub><b>this is a test</b></sub><sub><b>this is a test</b></sub> a <sib/> <sub>Some <i>content</i> here<b>this is a test</b></sub></test>", "XML");

        q = $("sub", $("<test><sub /> a <sib><b>with</b> stuff</sib> <sub>Some <i>content</i> here</sub></test>"));
        q.append($("sib", q.document()).get(0));
        assertEquals(q.write(), "<test><sub><sib><b>with</b> stuff</sib></sub> a <sib><b>with</b> stuff</sib> <sub>Some <i>content</i> here<sib><b>with</b> stuff</sib></sub></test>", "XML");

        q = $("sub", $("<test><sub>content</sub> <sub>simple </sub></test>"));
        q.append($("sub:eq(0)", q.document()).get(0));
        assertEquals(q.write(), "<test><sub>content<sub>content</sub></sub> <sub>simple <sub>content</sub></sub></test>", "XML");
    }

    @Test
    public void appendQ() throws Exception {
        Q q = $("sub", $("<test><sub></sub><sub /> a <sib/> <sub>Some <i>content</i> here</sub></test>"));
        q.append($("<b>this is a test</b>"));
        assertEquals(q.write(), "<test><sub><b>this is a test</b></sub><sub><b>this is a test</b></sub> a <sib/> <sub>Some <i>content</i> here<b>this is a test</b></sub></test>", "XML");

        q = $("sub", $("<test><sub /> a <sib><b>with</b> stuff</sib> <sub>Some <i>content</i> here</sub></test>"));
        q.append($("sib", q.document()));
        assertEquals(q.write(), "<test><sub><sib><b>with</b> stuff</sib></sub> a <sib><b>with</b> stuff</sib> <sub>Some <i>content</i> here<sib><b>with</b> stuff</sib></sub></test>", "XML");

        q = $("sub", $("<test><sub>content</sub> <sub>simple </sub></test>"));
        q.append($("sub:eq(0)", q.document()));
        assertEquals(q.write(), "<test><sub>content<sub>content</sub></sub> <sub>simple <sub>content</sub></sub></test>", "XML");

        q = $("sub", $("<test><sub>one</sub> <sub>two</sub></test>"));
        q.append($("<three>four</three> and <five/>"));
        assertEquals(q.write(), "<test><sub>one<three>four</three><five/></sub> <sub>two<three>four</three><five/></sub></test>", "XML");
    }

    @Test
    public void appendFnToXml() throws Exception {
        Q q = $("sub", $("<test><sub></sub><sub /> a <sib/> <sub>Some <i>content</i> here</sub></test>"));
        final int[] i = { 0 };

        q.append(new ElementToString() {
            @Override
            public String apply(Element element) {
                i[0]++;
                return "<b>this is " + i[0] + " test</b>";
            }
        });

        assertEquals(q.write(), "<test><sub><b>this is 1 test</b></sub><sub><b>this is 2 test</b></sub> a <sib/> <sub>Some <i>content</i> here<b>this is 3 test</b></sub></test>", "XML");

        q = $("sub", $("<test><sub>one</sub> <sub>two</sub></test>"));
        i[0] = 0;

        q.append(new ElementToString() {
            @Override
            public String apply(Element element) {
                i[0]++;
                return "<three>four</three> " + i[0] + " and <five/>";
            }
        });

        assertEquals(q.write(), "<test><sub>one<three>four</three> 1 and <five/></sub> <sub>two<three>four</three> 2 and <five/></sub></test>", "XML");
    }

    @Test
    public void appendFnToElement() throws Exception {
        // TODO
    }

    @Test
    public void appendFnToQ() throws Exception {
        // TODO
    }

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
    public void textObject() throws Exception {
        Q q = $("sub", $("<test><sub>Content <b>with some</b> sub content. <endingTag value=\"something\"/></sub><sub>content</sub><sub /></test>"));
        q.text(123);
        assertEquals($("sub:eq(0)", q.document()).text(), "123", "Text");
        assertEquals($("sub:eq(1)", q.document()).text(), "123", "Text");
        assertEquals($("sub:eq(2)", q.document()).text(), "123", "Text");

        q.text((Object) null);
        assertEquals($("sub:eq(0)", q.document()).text(), "", "Text");
        assertEquals($("sub:eq(1)", q.document()).text(), "", "Text");
        assertEquals($("sub:eq(2)", q.document()).text(), "", "Text");
    }

    @Test
    public void textMap() throws Exception {
        Q q = $("sub", document("<test><sub>Content <b>with some</b> sub content. <endingTag value=\"something\"/></sub><sub>content</sub><sub /></test>"));
        final int[] i = new int[] { 0 };
        q.text(new ElementToString() {
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
        Q q = $("sub", $("<test><sub>Some <b>content</b></sub></test>"));
        q.text("this is a <test/> & it's \"fun\"");
        assertEquals(q.text(), "this is a <test/> & it's \"fun\"", "Text");
        assertEquals(q.xml(), "this is a &lt;test/&gt; &amp; it's \"fun\"", "XML");
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
        q.xml(new ElementToString() {
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
