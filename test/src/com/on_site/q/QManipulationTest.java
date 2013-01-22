package com.on_site.q;

import static com.on_site.q.Q.$;

import com.google.common.base.Function;
import com.on_site.util.TestBase;

import org.testng.annotations.Test;

public class QManipulationTest extends TestBase {
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
    }
}
