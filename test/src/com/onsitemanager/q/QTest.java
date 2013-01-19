package com.onsitemanager.q;

import static com.onsitemanager.q.Q.$;

import com.onsitemanager.util.TestBase;

import org.testng.annotations.Test;

public class QTest extends TestBase {
    @Test
    public void emptyConstructor() {
        assertSelectedSize($(), 0);
    }

    @Test
    public void documentConstructor() throws Exception {
        Q q = $(document("<test><sub /><sub>content</sub></test>"));
        assertSelectedSize(q, 1);
        assertNodeName(q.get(0), "test");
    }
}
