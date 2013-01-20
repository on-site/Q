package com.onsitemanager.q;

import static com.onsitemanager.q.Q.$;

import com.onsitemanager.util.TestBase;

import org.testng.annotations.Test;

public class QTraversingTest extends TestBase {
    @Test
    public void end() throws Exception {
        Q q = $("sub", document("<test><sub/><sub>content</sub></test>"));
        assertEquals(q.eq(0).end(), q, "The resulting Q");
        assertEquals(q.end().size(), 1, "Size");
        assertEquals(q.end().get(0), $("test", q.document()).get(0), "Selected item");
        assertEquals(q.end().end().size(), 0, "Size");
        assertEquals($("argle", q.document()).end().size(), 0, "Size");

        // Testing other filtering methods to ensure they pop properly
        assertEquals(q.first().end(), q, "The resulting Q");
        assertEquals(q.last().end(), q, "The resulting Q");
        assertEquals(q.prevAll().end(), q, "The resulting Q");
    }

    @Test
    public void eqIndex() throws Exception {
        Q q = $("sub", document("<test><sub/><sub>content</sub></test>"));
        assertEquals(q.eq(0).size(), 1, "Size");
        assertEquals(q.eq(0).get(0), q.get(0), "Matched element");
        assertEquals(q.eq(1).size(), 1, "Size");
        assertEquals(q.eq(1).get(0), q.get(1), "Matched element");
        assertEquals(q.eq(-1).size(), 1, "Size");
        assertEquals(q.eq(-1).get(0), q.get(1), "Matched element");
        assertEquals(q.eq(-2).size(), 1, "Size");
        assertEquals(q.eq(-2).get(0), q.get(0), "Matched element");
        assertEquals(q.eq(-3).size(), 0, "Size");
        assertEquals(q.eq(2).size(), 0, "Size");
    }

    @Test
    public void first() throws Exception {
        Q q = $("sub", document("<test><sub/><sub>content</sub></test>"));
        assertEquals(q.first().size(), 1, "Size");
        assertEquals(q.first().get(0), q.get(0), "Selected item");
        assertEquals(q.first().first().get(0), q.get(0), "Selected item");
    }

    @Test
    public void last() throws Exception {
        Q q = $("sub", document("<test><sub/><sub>content</sub></test>"));
        assertEquals(q.last().size(), 1, "Size");
        assertEquals(q.last().get(0), q.get(1), "Selected item");
        assertEquals(q.last().last().get(0), q.get(1), "Selected item");
    }

    @Test
    public void nextAll() throws Exception {
        Q q = $("sub", document("<test><sub/><sub>content</sub> sibling content <sub>More content</sub></test>"));
        assertEquals($("sub:eq(2)", q.document()).nextAll().isEmpty(), true, "isEmpty");
        assertEquals($("sub:eq(1)", q.document()).nextAll().size(), 1, "Size");
        assertEquals($("sub:eq(1)", q.document()).nextAll().get(0), q.get(2), "Element");
        assertEquals($("sub:eq(0)", q.document()).nextAll().size(), 2, "Size");
        assertEquals($("sub:eq(0)", q.document()).nextAll().get(0), q.get(1), "Element");
        assertEquals($("sub:eq(0)", q.document()).nextAll().get(1), q.get(2), "Element");

        q = $("sub", document("<test><container1><sub/><sub>content</sub> sibling content <sub>More content</sub></container1>" +
                              "<container2><sub/><sub>content</sub> sibling content <sub>More content</sub></container2></test>"));
        assertEquals($("container1 sub:eq(0), container2 sub:eq(1)", q.document()).nextAll().size(), 3, "Size");
        assertEquals($("container1 sub:eq(0), container2 sub:eq(1)", q.document()).nextAll().get(0), q.get(1), "Element");
        assertEquals($("container1 sub:eq(0), container2 sub:eq(1)", q.document()).nextAll().get(1), q.get(2), "Element");
        assertEquals($("container1 sub:eq(0), container2 sub:eq(1)", q.document()).nextAll().get(2), q.get(5), "Element");
    }

    @Test
    public void nextAllSelector() throws Exception {
        Q q = $("sub", document("<test><sub/><sib/><sub>content</sub> sibling content <sub>More content</sub></test>"));
        assertEquals($("sub:eq(0)", q.document()).nextAll("sub").size(), 2, "Size");
        assertEquals($("sub:eq(0)", q.document()).nextAll("sub").get(0), q.get(1), "Element");
        assertEquals($("sub:eq(0)", q.document()).nextAll("sub").get(1), q.get(2), "Element");
        assertEquals($("sub:eq(0)", q.document()).nextAll("sib").size(), 1, "Size");
        assertEquals($("sub:eq(0)", q.document()).nextAll("sib").get(0), $("sib", q.document()).get(0), "Element");
        assertEquals($("sub:eq(0)", q.document()).nextAll("argle").isEmpty(), true, "isEmpty");
    }

    @Test
    public void prevAll() throws Exception {
        Q q = $("sub", document("<test><sub/><sub>content</sub> sibling content <sub>More content</sub></test>"));
        assertEquals($("sub:eq(0)", q.document()).prevAll().isEmpty(), true, "isEmpty");
        assertEquals($("sub:eq(1)", q.document()).prevAll().size(), 1, "Size");
        assertEquals($("sub:eq(1)", q.document()).prevAll().get(0), q.get(0), "Element");
        assertEquals($("sub:eq(2)", q.document()).prevAll().size(), 2, "Size");
        assertEquals($("sub:eq(2)", q.document()).prevAll().get(0), q.get(1), "Element");
        assertEquals($("sub:eq(2)", q.document()).prevAll().get(1), q.get(0), "Element");

        q = $("sub", document("<test><container1><sub/><sub>content</sub> sibling content <sub>More content</sub></container1>" +
                              "<container2><sub/><sub>content</sub> sibling content <sub>More content</sub></container2></test>"));
        assertEquals($("container1 sub:eq(2), container2 sub:eq(1)", q.document()).prevAll().size(), 3, "Size");
        assertEquals($("container1 sub:eq(2), container2 sub:eq(1)", q.document()).prevAll().get(0), q.get(1), "Element");
        assertEquals($("container1 sub:eq(2), container2 sub:eq(1)", q.document()).prevAll().get(1), q.get(0), "Element");
        assertEquals($("container1 sub:eq(2), container2 sub:eq(1)", q.document()).prevAll().get(2), q.get(3), "Element");
    }

    @Test
    public void prevAllSelector() throws Exception {
        Q q = $("sub", document("<test><sub/><sib/><sub>content</sub> sibling content <sub>More content</sub></test>"));
        assertEquals($("sub:eq(2)", q.document()).prevAll("sub").size(), 2, "Size");
        assertEquals($("sub:eq(2)", q.document()).prevAll("sub").get(0), q.get(1), "Element");
        assertEquals($("sub:eq(2)", q.document()).prevAll("sub").get(1), q.get(0), "Element");
        assertEquals($("sub:eq(2)", q.document()).prevAll("sib").size(), 1, "Size");
        assertEquals($("sub:eq(2)", q.document()).prevAll("sib").get(0), $("sib", q.document()).get(0), "Element");
        assertEquals($("sub:eq(2)", q.document()).prevAll("argle").isEmpty(), true, "isEmpty");
    }
}
