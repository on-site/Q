package com.on_site.q;

import static com.on_site.q.Q.$;

import com.on_site.util.TestBase;

import org.testng.annotations.Test;

public class QAdditionalTest extends TestBase {
    @Test
    public void write() throws Exception {
        Q q = $("<test>This is a <b>valid</b> document with<br/> a root.</test>");
        assertEquals(q.write(), "<test>This is a <b>valid</b> document with<br/> a root.</test>", "XML");

        q = $("This is <b>an</b> example <br/> xml document");
        assertEquals(q.write(), "<root>This is <b>an</b> example <br/> xml document</root>", "XML");

        assertEquals($().write(), null, "XML");
    }

    @Test
    public void writeFile() throws Exception {
    }

    @Test
    public void writeOutputStream() throws Exception {
    }

    @Test
    public void writeWriter() throws Exception {
    }
}
