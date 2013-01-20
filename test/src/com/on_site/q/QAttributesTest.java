package com.on_site.q;

import static com.on_site.q.Q.$;

import com.on_site.util.TestBase;

import org.testng.annotations.Test;

public class QAttributesTest extends TestBase {
    @Test
    public void attrName() throws Exception {
        Q q = $("sub", document("<test><sub foo='something' bar='' /><sub>content</sub></test>"));
        assertEquals(q.attr("foo"), "something", "Attribute value");
        assertEquals(q.attr("bar"), "", "Attribute value");
        assertEquals(q.attr("baz"), null, "Attribute value");
        assertEquals($("sub:eq(1)", q.document()).attr("foo"), null, "Attribute value");
        assertEquals($("nothing", q.document()).attr("foo"), null, "Attribute value");
    }

    @Test
    public void attrNameValue() throws Exception {
    }

    @Test
    public void attrAttributes() throws Exception {
    }

    @Test
    public void attrNameMap() throws Exception {
    }
}
