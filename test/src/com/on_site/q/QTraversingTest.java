package com.on_site.q;

import static com.on_site.q.Q.$;

import com.google.common.base.Predicate;
import com.on_site.util.TestBase;

import org.testng.annotations.Test;
import org.w3c.dom.Element;

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
        assertEquals(q.filter("sub:eq(0)").end(), q, "The resulting Q");
        assertEquals(q.find("sub:eq(0)").end(), q, "The resulting Q");
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
    public void filterSelector() throws Exception {
        Q q = $("sub, sib", document("<test><sib/><container><sub/><sib/></container><sub>content<sub>More</sub></sub></test>"));
        assertEquals(q.filter("sib").size(), 2, "Size");
        assertEquals(q.filter("sib").get(0), q.get(0), "Element");
        assertEquals(q.filter("sib").get(1), q.get(2), "Element");
    }

    @Test
    public void filterPredicate() throws Exception {
        Q q = $("sub, sib", document("<test><sib/><container><sub/><sib/></container><sub>content<sub>More</sub></sub></test>"));

        Q filtered = q.filter(new Predicate<Element>() {
            @Override
            public boolean apply(Element element) {
                return element.getNodeName().equals("sib");
            }
        });

        assertEquals(filtered.size(), 2, "Size");
        assertEquals(filtered.get(0), q.get(0), "Element");
        assertEquals(filtered.get(1), q.get(2), "Element");
    }

    @Test
    public void filterElement() throws Exception {
        Q q = $("sub, sib", document("<test><sib/><container><sub/><sib/></container><sub>content<sub>More</sub></sub></test>"));
        assertEquals(q.filter(q.get(2)).size(), 1, "Size");
        assertEquals(q.filter(q.get(2)).get(0), q.get(2), "Element");
    }

    @Test
    public void filterQ() throws Exception {
        Q q = $("sub, sib", document("<test><sib/><container><sub/><sib/></container><sub>content<sub>More</sub></sub></test>"));
        assertEquals(q.filter($("sib", q.document())).size(), 2, "Size");
        assertEquals(q.filter($("sib", q.document())).get(0), q.get(0), "Element");
        assertEquals(q.filter($("sib", q.document())).get(1), q.get(2), "Element");
    }

    @Test
    public void findSelector() throws Exception {
        Q q = $("sub", document("<test><sub/><sub>content<sub>More</sub></sub></test>"));
        assertEquals($("test", q.document()).find("sub").size(), 3, "Size");
        assertEquals($("test", q.document()).find("sub").get(0), q.get(0), "Element");
        assertEquals($("test", q.document()).find("sub").get(1), q.get(1), "Element");
        assertEquals($("test", q.document()).find("sub").get(2), q.get(2), "Element");
        assertEquals($("test", q.document()).find("sub:eq(1)").size(), 1, "Size");
        assertEquals($("test", q.document()).find("sub:eq(1)").get(0), q.get(1), "Element");
    }

    @Test
    public void findQ() throws Exception {
        Q q = $("sub", document("<test><sib/><container><sub/></container><sub>content<sub>More</sub></sub></test>"));
        assertEquals($("test", q.document()).find(q).size(), 3, "Size");
        assertEquals($("test", q.document()).find(q).get(0), q.get(0), "Element");
        assertEquals($("test", q.document()).find(q).get(1), q.get(1), "Element");
        assertEquals($("test", q.document()).find(q).get(2), q.get(2), "Element");
        assertEquals($("test", q.document()).find($("sub:eq(1)", q.document())).size(), 1, "Size");
        assertEquals($("test", q.document()).find($("sub:eq(1)", q.document())).get(0), q.get(1), "Element");
        assertEquals($("container", q.document()).find($("sub", q.document())).size(), 1, "Size");
        assertEquals($("container", q.document()).find($("sub", q.document())).get(0), q.get(0), "Element");
        assertEquals($("container", q.document()).find($("sub:eq(1)", q.document())).isEmpty(), true, "isEmpty");
    }

    @Test
    public void findElement() throws Exception {
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
