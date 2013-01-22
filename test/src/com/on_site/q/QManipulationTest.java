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
    }
}
