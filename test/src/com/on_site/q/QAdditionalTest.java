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
    @Test(expectedExceptions = Exception.class)
    public void exprWithoutSelectorDefined() throws Exception {
        $("<test><sib/><sub/><sib><sub/></sib></test>").find(":nonExistentSelector");
    }

    @Test
    public void globalExprWithSameNameCalledTwice() throws Exception {
        SimplePseudo pseudo = new SimplePseudo("globalExprWithSameNameCalledTwice") {
            @Override
            public boolean apply(Element element, String argument) {
                return element.getNodeName().equals("sub");
            }
        };

        Q.globalExpr(pseudo);

        try {
            Q.globalExpr(pseudo);
        } catch (Exception e) {
            fail("No exception was expected", e);
        }
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void globalExprWithSameNameDifferentPseudoCalledTwice() throws Exception {
        Q.globalExpr(new SimplePseudo("globalExprWithSameNameDifferentPseudoCalledTwice") {
            @Override
            public boolean apply(Element element, String argument) {
                return element.getNodeName().equals("sub");
            }
        });

        Q.globalExpr(new SimplePseudo("globalExprWithSameNameDifferentPseudoCalledTwice") {
            @Override
            public boolean apply(Element element, String argument) {
                return element.getNodeName().equals("sub");
            }
        });
    }

    @Test
    public void globalExpr() throws Exception {
        Q.globalExpr(new SimplePseudo("globalExpr") {
            @Override
            public boolean apply(Element element, String argument) {
                return element.getNodeName().equals("sub");
            }
        });

        Q q = $("<test><sib/><sub/><sib><sub/></sib></test>");
        assertEquals(q.find(":globalExpr").size(), 2, "Size");
        assertEquals(q.find(":globalExpr").get(0), $("sub", q.document()).get(0), "Element");
        assertEquals(q.find(":globalExpr").get(1), $("sub", q.document()).get(1), "Element");

        q = $("test :globalExpr", $("<test><sib/><sub/><sib><sub/></sib></test>"));
        assertEquals(q.size(), 2, "Size");
    }

    @Test
    public void exprWithSameNameCalledTwice() throws Exception {
        Pseudo pseudo = new SimplePseudo("exprWithSameNameCalledTwice") {
            @Override
            public boolean apply(Element element, String argument) {
                return element.getNodeName().equals("sub");
            }
        };

        Q q = $("<test><sib/><sub/><sib><sub/></sib></test>").expr(pseudo);

        try {
            q.expr(pseudo);
        } catch (Exception e) {
            fail("No exception was expected", e);
        }
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void exprWithSameNameDifferentPseudoCalledTwice() throws Exception {
        Q q = $("<test><sib/><sub/><sib><sub/></sib></test>");
        q.expr(new SimplePseudo("exprWithSameNameDifferentPseudoCalledTwice") {
            @Override
            public boolean apply(Element element, String argument) {
                return element.getNodeName().equals("sub");
            }
        });

        q.expr(new SimplePseudo("exprWithSameNameDifferentPseudoCalledTwice") {
            @Override
            public boolean apply(Element element, String argument) {
                return element.getNodeName().equals("sub");
            }
        });
    }

    @Test
    public void expr() throws Exception {
        Q q = $("<test><sib/><sub/><sib><sub/></sib></test>").expr(new SimplePseudo("expr") {
            @Override
            public boolean apply(Element element, String argument) {
                return element.getNodeName().equals("sub");
            }
        });

        assertEquals(q.find(":expr").size(), 2, "Size");
        assertEquals(q.find(":expr").get(0), $("sub", q.document()).get(0), "Element");
        assertEquals(q.find(":expr").get(1), $("sub", q.document()).get(1), "Element");
    }

    @Test
    public void exprOnNewQSameDocument() throws Exception {
        Q q = $("<test><sib/><sub/><sib><sub/></sib></test>").expr(new SimplePseudo("exprOnNewQSameDocument") {
            @Override
            public boolean apply(Element element, String argument) {
                return element.getNodeName().equals("sub");
            }
        });

        q = $(":exprOnNewQSameDocument", q.document());
        assertEquals(q.size(), 2, "Size");
        assertEquals(q.get(0), $("sub", q.document()).get(0), "Element");
        assertEquals(q.get(1), $("sub", q.document()).get(1), "Element");
    }

    @Test(expectedExceptions = Exception.class)
    public void exprOnAnotherDocument() throws Exception {
        Q q = $("<test><sib/><sub/><sib><sub/></sib></test>").expr(new SimplePseudo("exprOnAnotherDocument") {
            @Override
            public boolean apply(Element element, String argument) {
                return element.getNodeName().equals("sub");
            }
        });

        $("test :exprOnAnotherDocument", $("<test><sib/><sub/><sib><sub/></sib></test>"));
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
