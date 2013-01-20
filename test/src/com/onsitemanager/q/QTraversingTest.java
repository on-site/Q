package com.onsitemanager.q;

import static com.onsitemanager.q.Q.$;

import com.onsitemanager.util.TestBase;

import org.testng.annotations.Test;

public class QTraversingTest extends TestBase {
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
    public void end() throws Exception {
        Q q = $("sub", document("<test><sub/><sub>content</sub></test>"));
        assertEquals(q.eq(0).end(), q, "The resulting Q");
        assertEquals(q.end().size(), 1, "Size");
        assertEquals(q.end().get(0), $("test", q.document()).get(0), "Selected item");
        assertEquals(q.end().end().size(), 0, "Size");
        assertEquals($("argle", q.document()).end().size(), 0, "Size");
    }
}
