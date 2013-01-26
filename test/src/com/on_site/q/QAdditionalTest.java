package com.on_site.q;

import static com.on_site.q.Q.$;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.on_site.util.TestBase;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.StringWriter;

import org.testng.annotations.Test;
import org.w3c.dom.Element;

public class QAdditionalTest extends TestBase {
    @Test(expectedExceptions = { Exception.class })
    public void exprWithoutSelectorDefined() throws Exception {
        $("<test><sib/><sub/><sib><sub/></sib></test>").find(":nonExistentSelector");
    }

    @Test(expectedExceptions = { IllegalArgumentException.class })
    public void exprWithSameNameCalledTwice() throws Exception {
        Pseudo pseudo = new Pseudo(":YetAnotherNewSelector") {
            @Override
            public boolean apply(Element element, String argument) {
                return element.getNodeName().equals("sub");
            }
        };

        Q.expr(pseudo);
        Q.expr(pseudo);
    }

    @Test
    public void expr() throws Exception {
        Q.expr(new Pseudo("testSub") {
            @Override
            public boolean apply(Element element, String argument) {
                return element.getNodeName().equals("sub");
            }
        });

        Q q = $("<test><sib/><sub/><sib><sub/></sib></test>");
        assertEquals(q.find(":testSub").size(), 2, "Size");
        assertEquals(q.find(":testSub").get(0), $("sub", q.document()).get(0), "Element");
        assertEquals(q.find(":testSub").get(1), $("sub", q.document()).get(1), "Element");

        q = $("test :testSub", $("<test><sib/><sub/><sib><sub/></sib></test>"));
        assertEquals(q.size(), 2, "Size");
    }

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
        File out = tempFile();
        Q q = $("<test>This is a <b>valid</b> document with<br/> a root.</test>");
        q.write(out);
        assertEquals(Files.toString(out, Charsets.UTF_8), "<test>This is a <b>valid</b> document with<br/> a root.</test>", "XML");

        out = tempFile();
        q = $("This is <b>an</b> example <br/> xml document");
        q.write(out);
        assertEquals(Files.toString(out, Charsets.UTF_8), "<root>This is <b>an</b> example <br/> xml document</root>", "XML");

        out = tempFile();
        $().write(out);
        assertEquals(Files.toString(out, Charsets.UTF_8), "", "XML");
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
