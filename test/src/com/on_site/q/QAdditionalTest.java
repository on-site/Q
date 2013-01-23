package com.on_site.q;

import static com.on_site.q.Q.$;

import com.on_site.util.TestBase;

import java.io.ByteArrayOutputStream;
import java.io.StringWriter;

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
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Q q = $("<test>This is a <b>valid</b> document with<br/> a root.</test>");
        q.write(out);
        out.close();
        assertEquals(out.toString(), "<test>This is a <b>valid</b> document with<br/> a root.</test>", "XML");

        out = new ByteArrayOutputStream();
        q = $("This is <b>an</b> example <br/> xml document");
        q.write(out);
        out.close();
        assertEquals(out.toString(), "<root>This is <b>an</b> example <br/> xml document</root>", "XML");

        out = new ByteArrayOutputStream();
        $().write(out);
        out.close();
        assertEquals(out.toString(), "", "XML");
    }

    @Test
    public void writeWriter() throws Exception {
        StringWriter out = new StringWriter();
        Q q = $("<test>This is a <b>valid</b> document with<br/> a root.</test>");
        q.write(out);
        out.close();
        assertEquals(out.toString(), "<test>This is a <b>valid</b> document with<br/> a root.</test>", "XML");

        out = new StringWriter();
        q = $("This is <b>an</b> example <br/> xml document");
        q.write(out);
        out.close();
        assertEquals(out.toString(), "<root>This is <b>an</b> example <br/> xml document</root>", "XML");

        out = new StringWriter();
        $().write(out);
        out.close();
        assertEquals(out.toString(), "", "XML");
    }
}
