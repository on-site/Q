package com.on_site.q;

import static com.on_site.q.Q.$;

import com.on_site.util.TestBase;

import org.testng.annotations.Test;

public class QMiscellaneousTest extends TestBase {
    @Test
    public void get() throws Exception {
        Q q = $("sub", document("<test><sub/><sub>content</sub></test>"));
        assertEquals(q.get().length, 2, "Result length");
        assertEquals(q.get()[0], q.toArray()[0], "Result[0]");
        assertEquals(q.get()[1], q.toArray()[1], "Result[1]");
    }

    @Test
    public void getIndex() throws Exception {
        Q q = $("sub", document("<test><sub/><sub>content</sub></test>"));
        assertEquals(q.get(0), q.toArray()[0], "Element[0]");
        assertEquals(q.get(1), q.toArray()[1], "Element[1]");
        assertEquals(q.get(2), null, "Element[2]");
        assertEquals(q.get(-1), q.toArray()[1], "Element[-1]");
        assertEquals(q.get(-2), q.toArray()[0], "Element[-2]");
        assertEquals(q.get(-3), null, "Element[-3]");
    }

    @Test
    public void index() throws Exception {
        Q q = $("sub", document("<test><sub/><sub>content</sub></test>"));
        assertEquals($().index(), -1, "Index");
        assertEquals($("test", q.document()).index(), 0, "Index");
        assertEquals($(q.get(0)).index(), 0, "Index");
        assertEquals($(q.get(1)).index(), 1, "Index");
    }

    @Test
    public void indexSelector() throws Exception {
        Q q = $("sub", document("<test><sub/><sub>content</sub></test>"));
        assertEquals($().index("sub"), -1, "Index");
        assertEquals(q.index("sub"), 0, "Index");
        assertEquals($("sub:eq(1)", q.document()).index("sub"), 1, "Index");
    }

    @Test
    public void indexElement() throws Exception {
        Q q = $("sub", document("<test><sub/><sub>content</sub></test>"));
        assertEquals(q.index(q.get(0)), 0, "Index");
        assertEquals(q.index(q.get(1)), 1, "Index");
    }

    @Test
    public void indexQ() throws Exception {
        Q q = $("sub", document("<test><sub/><sub>content</sub></test>"));
        assertEquals(q.index($()), -1, "Index");
        assertEquals(q.index(q), 0, "Index");
        assertEquals(q.index($("sub:eq(1)", q.document())), 1, "Index");
    }

    public void size() throws Exception {
        Q q = $(document("<test><sub/><sub>content</sub></test>"));
        assertEquals($().size(), 0, "Size");
        assertEquals($("test", q.document()).size(), 1, "Size");
        assertEquals($("sub", q.document()).size(), 2, "Size");
    }

    public void toArray() throws Exception {
        Q q = $("sub", document("<test><sub/><sub>content</sub></test>"));
        assertEquals($().toArray().length, 0, "Array size");
        assertEquals(q.toArray().length, 1, "Array size");
        assertEquals(q.toArray()[0], q.get(0), "Element[0]");
        assertEquals(q.toArray()[1], q.get(1), "Element[1]");
    }
}
