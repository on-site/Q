package com.onsitemanager.q;

import static com.onsitemanager.q.Q.$;

import com.onsitemanager.util.TestBase;

import org.testng.annotations.Test;

public class QMiscellaneousTest extends TestBase {
    @Test
    public void get() throws Exception {
        Q q = $("sub", document("<test><sub/><sub>content</sub></test>"));

        // No arguments
        assertEquals(q.get().length, 2, "Result length");
        assertEquals(q.get()[0], q.toArray()[0], "Result[0]");
        assertEquals(q.get()[1], q.toArray()[1], "Result[1]");

        // Index argument
        assertEquals(q.get(0), q.toArray()[0], "Element[0]");
        assertEquals(q.get(1), q.toArray()[1], "Element[1]");
        assertEquals(q.get(-1), q.toArray()[1], "Element[-1]");
        assertEquals(q.get(-2), q.toArray()[0], "Element[-2]");
    }

    @Test
    public void index() throws Exception {
        Q q = $("sub", document("<test><sub/><sub>content</sub></test>"));

        // No argument

        // Selector

        // Element
        assertEquals(q.index(q.get(0)), 0, "Index");
        assertEquals(q.index(q.get(1)), 1, "Index");

        // Q object
    }

    public void size() throws Exception {
    }

    public void toArray() throws Exception {
    }
}
