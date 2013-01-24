package com.on_site.q;

import static com.on_site.q.Q.$;

import com.google.common.base.Function;
import com.on_site.util.TestBase;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;

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
        Q q = $("sub", document("<test><sub foo='something' bar='' /><sub>content</sub></test>"));
        q.attr("foo", "newvalue");
        assertEquals(q.eq(0).attr("foo"), "newvalue", "Attribute value");
        assertEquals(q.eq(1).attr("foo"), "newvalue", "Attribute value");
    }

    @Test
    public void attrNameObject() throws Exception {
        Q q = $("sub", document("<test><sub foo='something' bar='' /><sub>content</sub></test>"));
        q.attr("foo", 123);
        assertEquals(q.eq(0).attr("foo"), "123", "Attribute value");
        assertEquals(q.eq(1).attr("foo"), "123", "Attribute value");

        q.attr("foo", (Object) null);
        assertEquals(q.eq(0).attr("foo"), "", "Attribute value");
        assertEquals(q.eq(1).attr("foo"), "", "Attribute value");
    }

    @Test
    public void attrAttributes() throws Exception {
        Q q = $("sub", document("<test><sub foo='something' bar='' /><sub>content</sub></test>"));
        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("foo", "newvalue");
        attributes.put("bar", "anothervalue");
        attributes.put("baz", "one more value");
        q.attr(attributes);
        assertEquals(q.eq(0).attr("foo"), "newvalue", "Attribute value");
        assertEquals(q.eq(0).attr("bar"), "anothervalue", "Attribute value");
        assertEquals(q.eq(0).attr("baz"), "one more value", "Attribute value");
        assertEquals(q.eq(1).attr("foo"), "newvalue", "Attribute value");
        assertEquals(q.eq(1).attr("bar"), "anothervalue", "Attribute value");
        assertEquals(q.eq(1).attr("baz"), "one more value", "Attribute value");
    }

    @Test
    public void attrNameMap() throws Exception {
        Q q = $("sub", document("<test><sub foo='something' bar='' /><sub>content</sub></test>"));
        final int[] i = new int[] { 0 };
        q.attr("foo", new Function<Element, String>() {
            @Override
            public String apply(Element element) {
                return "newvalue" + i[0]++;
            }
        });
        assertEquals(q.eq(0).attr("foo"), "newvalue0", "Attribute value");
        assertEquals(q.eq(1).attr("foo"), "newvalue1", "Attribute value");
    }

    @Test
    public void removeAttrName() throws Exception {
        Q q = $("sub", document("<test><sub foo='something' bar='' /><sub>content</sub></test>"));
        q.removeAttr("foo");
        assertEquals(q.eq(0).attr("foo"), null, "Attribute value");
        assertEquals(q.eq(1).attr("foo"), null, "Attribute value");
    }

    @Test
    public void attrNameValueWithInvalidAttributes() throws Exception {
        Q q = $("sub", $("<test><sub foo=\"something\"/></test>"));
        q.attr("foo", "<something/> \"disallowed\" & isn't it fun!");
        assertEquals(q.attr("foo"), "<something/> \"disallowed\" & isn't it fun!", "Attribute value");
        assertEquals(q.parent().xml(), "<sub foo=\"&lt;something/&gt; &quot;disallowed&quot; &amp; isn't it fun!\"/>", "XML");
    }

    @Test(expectedExceptions = DOMException.class)
    public void attrNameValueWithIllegalAttrKeyValues() throws Exception {
        Q q = $("sub", $("<test><sub/></test>"));
        q.attr("<something/> \"disallowed\" & isn't it fun!", "value");
    }
}
