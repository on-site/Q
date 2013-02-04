package com.on_site.q;

import static com.on_site.q.Q.$;

import com.on_site.fn.ElementPredicate;
import com.on_site.fn.ElementTo;
import com.on_site.fn.ElementToElement;
import com.on_site.fn.ElementToElements;
import com.on_site.fn.ElementToQ;
import com.on_site.util.TestBase;

import java.util.List;

import org.testng.annotations.Test;
import org.w3c.dom.Element;

public class QTraversingTest extends TestBase {
    @Test
    public void addSelector() throws Exception {
        Q q = $("<test><sub/> <sib/> <sub><sib/></sub> <sib><sub/><other/></sib> <other/> <sub/></test>");
        Q subs = $("sub", q.document());
        Q sibs = $("sib", q.document());
        assertEquals(subs.add("sib").size(), 7, "Size");
        assertEquals(subs.add("sib").get(0), subs.get(0), "Element");
        assertEquals(subs.add("sib").get(1), subs.get(1), "Element");
        assertEquals(subs.add("sib").get(2), subs.get(2), "Element");
        assertEquals(subs.add("sib").get(3), subs.get(3), "Element");
        assertEquals(subs.add("sib").get(4), sibs.get(0), "Element");
        assertEquals(subs.add("sib").get(5), sibs.get(1), "Element");
        assertEquals(subs.add("sib").get(6), sibs.get(2), "Element");

        assertEquals(subs.add("subs").size(), 4, "Size");
        assertEquals(subs.add("subs").get(0), subs.get(0), "Element");
        assertEquals(subs.add("subs").get(1), subs.get(1), "Element");
        assertEquals(subs.add("subs").get(2), subs.get(2), "Element");
        assertEquals(subs.add("subs").get(3), subs.get(3), "Element");

        assertEquals(subs.add((String) null).size(), 4, "Size");
        assertEquals(subs.add((String) null).get(0), subs.get(0), "Element");
        assertEquals(subs.add((String) null).get(1), subs.get(1), "Element");
        assertEquals(subs.add((String) null).get(2), subs.get(2), "Element");
        assertEquals(subs.add((String) null).get(3), subs.get(3), "Element");
    }

    @Test
    public void addElement() throws Exception {
        Q q = $("<test><sub/> <sib/> <sub><sib/></sub> <sib><sub/><other/></sib> <other/> <sub/></test>");
        Q subs = $("sub", q.document());
        Q sibs = $("sib", q.document());
        assertEquals(subs.add(sibs.get(1)).size(), 5, "Size");
        assertEquals(subs.add(sibs.get(1)).get(0), subs.get(0), "Element");
        assertEquals(subs.add(sibs.get(1)).get(1), subs.get(1), "Element");
        assertEquals(subs.add(sibs.get(1)).get(2), subs.get(2), "Element");
        assertEquals(subs.add(sibs.get(1)).get(3), subs.get(3), "Element");
        assertEquals(subs.add(sibs.get(1)).get(4), sibs.get(1), "Element");

        assertEquals(subs.add("subs").size(), 4, "Size");
        assertEquals(subs.add(subs.get(2)).get(0), subs.get(0), "Element");
        assertEquals(subs.add(subs.get(2)).get(1), subs.get(1), "Element");
        assertEquals(subs.add(subs.get(2)).get(2), subs.get(2), "Element");
        assertEquals(subs.add(subs.get(2)).get(3), subs.get(3), "Element");

        assertEquals(subs.add((Element) null).size(), 4, "Size");
        assertEquals(subs.add((Element) null).get(0), subs.get(0), "Element");
        assertEquals(subs.add((Element) null).get(1), subs.get(1), "Element");
        assertEquals(subs.add((Element) null).get(2), subs.get(2), "Element");
        assertEquals(subs.add((Element) null).get(3), subs.get(3), "Element");
    }

    @Test
    public void addElements() throws Exception {
        Q q = $("<test><sub/> <sib/> <sub><sib/></sub> <sib><sub/><other/></sib> <other/> <sub/></test>");
        Q subs = $("sub", q.document());
        Q sibs = $("sib", q.document());
        assertEquals(subs.add(sibs.get()).size(), 7, "Size");
        assertEquals(subs.add(sibs.get()).get(0), subs.get(0), "Element");
        assertEquals(subs.add(sibs.get()).get(1), subs.get(1), "Element");
        assertEquals(subs.add(sibs.get()).get(2), subs.get(2), "Element");
        assertEquals(subs.add(sibs.get()).get(3), subs.get(3), "Element");
        assertEquals(subs.add(sibs.get()).get(4), sibs.get(0), "Element");
        assertEquals(subs.add(sibs.get()).get(5), sibs.get(1), "Element");
        assertEquals(subs.add(sibs.get()).get(6), sibs.get(2), "Element");

        assertEquals(subs.add(subs.get()).size(), 4, "Size");
        assertEquals(subs.add(subs.get()).get(0), subs.get(0), "Element");
        assertEquals(subs.add(subs.get()).get(1), subs.get(1), "Element");
        assertEquals(subs.add(subs.get()).get(2), subs.get(2), "Element");
        assertEquals(subs.add(subs.get()).get(3), subs.get(3), "Element");

        assertEquals(subs.add((Element[]) null).size(), 4, "Size");
        assertEquals(subs.add((Element[]) null).get(0), subs.get(0), "Element");
        assertEquals(subs.add((Element[]) null).get(1), subs.get(1), "Element");
        assertEquals(subs.add((Element[]) null).get(2), subs.get(2), "Element");
        assertEquals(subs.add((Element[]) null).get(3), subs.get(3), "Element");

        assertEquals(subs.add(new Element[] { null }).size(), 4, "Size");
        assertEquals(subs.add(new Element[] { null }).get(0), subs.get(0), "Element");
        assertEquals(subs.add(new Element[] { null }).get(1), subs.get(1), "Element");
        assertEquals(subs.add(new Element[] { null }).get(2), subs.get(2), "Element");
        assertEquals(subs.add(new Element[] { null }).get(3), subs.get(3), "Element");
    }

    @Test
    public void addQ() throws Exception {
        Q q = $("<test><sub/> <sib/> <sub><sib/></sub> <sib><sub/><other/></sib> <other/> <sub/></test>");
        Q subs = $("sub", q.document());
        Q sibs = $("sib", q.document());
        assertEquals(subs.add(sibs).size(), 7, "Size");
        assertEquals(subs.add(sibs).get(0), subs.get(0), "Element");
        assertEquals(subs.add(sibs).get(1), subs.get(1), "Element");
        assertEquals(subs.add(sibs).get(2), subs.get(2), "Element");
        assertEquals(subs.add(sibs).get(3), subs.get(3), "Element");
        assertEquals(subs.add(sibs).get(4), sibs.get(0), "Element");
        assertEquals(subs.add(sibs).get(5), sibs.get(1), "Element");
        assertEquals(subs.add(sibs).get(6), sibs.get(2), "Element");

        assertEquals(subs.add(subs).size(), 4, "Size");
        assertEquals(subs.add(subs).get(0), subs.get(0), "Element");
        assertEquals(subs.add(subs).get(1), subs.get(1), "Element");
        assertEquals(subs.add(subs).get(2), subs.get(2), "Element");
        assertEquals(subs.add(subs).get(3), subs.get(3), "Element");

        assertEquals(subs.add((Q) null).size(), 4, "Size");
        assertEquals(subs.add((Q) null).get(0), subs.get(0), "Element");
        assertEquals(subs.add((Q) null).get(1), subs.get(1), "Element");
        assertEquals(subs.add((Q) null).get(2), subs.get(2), "Element");
        assertEquals(subs.add((Q) null).get(3), subs.get(3), "Element");
    }

    @Test
    public void addSelectorContext() throws Exception {
        Q q = $("<test><sub/> <sib/> <sub><sib/></sub> <sib><sub/><other/></sib> <other/> <sub/></test>");
        Q subs = $("sub", q.document());
        Q sibs = $("sib", q.document());
        assertEquals(subs.add("sib", subs.get(1)).size(), 5, "Size");
        assertEquals(subs.add("sib", subs.get(1)).get(0), subs.get(0), "Element");
        assertEquals(subs.add("sib", subs.get(1)).get(1), subs.get(1), "Element");
        assertEquals(subs.add("sib", subs.get(1)).get(2), subs.get(2), "Element");
        assertEquals(subs.add("sib", subs.get(1)).get(3), subs.get(3), "Element");
        assertEquals(subs.add("sib", subs.get(1)).get(4), sibs.get(1), "Element");

        assertEquals(subs.add("sub", sibs.get(2)).size(), 4, "Size");
        assertEquals(subs.add("sub", sibs.get(2)).get(0), subs.get(0), "Element");
        assertEquals(subs.add("sub", sibs.get(2)).get(1), subs.get(1), "Element");
        assertEquals(subs.add("sub", sibs.get(2)).get(2), subs.get(2), "Element");
        assertEquals(subs.add("sub", sibs.get(2)).get(3), subs.get(3), "Element");

        assertEquals(subs.add("sib", null).size(), 7, "Size");
        assertEquals(subs.add("sib", null).get(0), subs.get(0), "Element");
        assertEquals(subs.add("sib", null).get(1), subs.get(1), "Element");
        assertEquals(subs.add("sib", null).get(2), subs.get(2), "Element");
        assertEquals(subs.add("sib", null).get(3), subs.get(3), "Element");
        assertEquals(subs.add("sib", null).get(4), sibs.get(0), "Element");
        assertEquals(subs.add("sib", null).get(5), sibs.get(1), "Element");
        assertEquals(subs.add("sib", null).get(6), sibs.get(2), "Element");

        assertEquals(subs.add(null, subs.get(1)).size(), 4, "Size");
        assertEquals(subs.add(null, subs.get(1)).get(0), subs.get(0), "Element");
        assertEquals(subs.add(null, subs.get(1)).get(1), subs.get(1), "Element");
        assertEquals(subs.add(null, subs.get(1)).get(2), subs.get(2), "Element");
        assertEquals(subs.add(null, subs.get(1)).get(3), subs.get(3), "Element");
    }

    @Test
    public void children() throws Exception {
        Q q = $("sub", $("<test><sub/> <something /> <sub><p>Some sub <b>content</b></p></sub> <sub><b>More</b> sub <p>content <br/></p></sub></test>"));
        assertEquals(q.children().size(), 3, "Size");
        assertEquals(q.children().get(0), $("sub:eq(1) p", q.document()).get(0), "Element");
        assertEquals(q.children().get(1), $("sub:eq(2) b", q.document()).get(0), "Element");
        assertEquals(q.children().get(2), $("sub:eq(2) p", q.document()).get(0), "Element");

        assertEquals($("sub", $("<test><sub/></test>")).children().isEmpty(), true, "isEmpty");
    }

    @Test
    public void childrenSelector() throws Exception {
        Q q = $("sub", $("<test><sub/> <something /> <sub><p>Some sub <b>content</b></p></sub> <sub><b>More</b> sub <p>content <br/></p></sub></test>"));
        assertEquals(q.children("p").size(), 2, "Size");
        assertEquals(q.children("p").get(0), $("sub:eq(1) p", q.document()).get(0), "Element");
        assertEquals(q.children("p").get(1), $("sub:eq(2) p", q.document()).get(0), "Element");

        assertEquals(q.children("nothing").isEmpty(), true, "isEmpty");
    }

    @Test
    public void end() throws Exception {
        Q q = $("sub", $("<test><sub/><sub>content</sub></test>"));
        assertEquals(q.eq(0).end(), q, "The resulting Q");
        assertEquals(q.end().size(), 1, "Size");
        assertEquals(q.end().get(0), $("test", q.document()).get(0), "Selected item");
        assertEquals(q.end().end().size(), 0, "Size");
        assertEquals($("argle", q.document()).end().size(), 0, "Size");

        // Testing other filtering methods to ensure they pop properly
        assertEquals(q.add("sub").end(), q, "The resulting Q");
        assertEquals(q.children().end(), q, "The resulting Q");
        assertEquals(q.filter("sub:eq(0)").end(), q, "The resulting Q");
        assertEquals(q.find("sub:eq(0)").end(), q, "The resulting Q");
        assertEquals(q.first().end(), q, "The resulting Q");
        assertEquals(q.has("sub").end(), q, "The resulting Q");
        assertEquals(q.last().end(), q, "The resulting Q");
        assertEquals(q.map(new ElementToElement() {
            @Override
            public Element apply(Element element) {
                return element;
            }
        }).end(), q, "The resulting Q");
        assertEquals(q.next().end(), q, "The resulting Q");
        assertEquals(q.nextAll().end(), q, "The resulting Q");
        assertEquals(q.prev().end(), q, "The resulting Q");
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

        Q filtered = q.filter(new ElementPredicate() {
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
        Q q = $("sub", $("<test><sub/><sub>content<sub>More</sub></sub></test>"));
        assertEquals($("test", q.document()).find("sub").size(), 3, "Size");
        assertEquals($("test", q.document()).find("sub").get(0), q.get(0), "Element");
        assertEquals($("test", q.document()).find("sub").get(1), q.get(1), "Element");
        assertEquals($("test", q.document()).find("sub").get(2), q.get(2), "Element");
        assertEquals($("test", q.document()).find("sub:eq(1)").size(), 1, "Size");
        assertEquals($("test", q.document()).find("sub:eq(1)").get(0), q.get(1), "Element");
    }

    @Test
    public void findQ() throws Exception {
        Q q = $("sub", $("<test><sib/><container><sub/></container><sub>content<sub>More</sub></sub></test>"));
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
        Q q = $("sub", $("<test><sib/><container><sub/></container><sub>content<sub>More</sub></sub></test>"));
        assertEquals($("test", q.document()).find($("sub:eq(1)", q.document()).get(0)).size(), 1, "Size");
        assertEquals($("test", q.document()).find($("sub:eq(1)", q.document()).get(0)).get(0), q.get(1), "Element");
        assertEquals($("container", q.document()).find($("sub:eq(1)", q.document()).get(0)).isEmpty(), true, "isEmpty");
    }

    @Test
    public void first() throws Exception {
        Q q = $("sub", $("<test><sub/><sub>content</sub></test>"));
        assertEquals(q.first().size(), 1, "Size");
        assertEquals(q.first().get(0), q.get(0), "Selected item");
        assertEquals(q.first().first().get(0), q.get(0), "Selected item");
    }

    @Test
    public void hasSelector() throws Exception {
        Q q = $("sub", $("<test><container><sub><sib/></sub></container><sub>content<sub>More</sub> <sib/></sub></test>"));
        assertEquals(q.has("sib").size(), 2, "Size");
        assertEquals(q.has("sib").get(0), q.get(0), "Element");
        assertEquals(q.has("sib").get(1), q.get(1), "Element");
        assertEquals(q.has("sub").size(), 1, "Size");
        assertEquals(q.has("sub").get(0), q.get(1), "Element");
        assertEquals(q.has("container").isEmpty(), true, "isEmpty");
    }

    @Test
    public void hasElement() throws Exception {
        Q q = $("sub", $("<test><container><sub><sib/></sub></container><sub>content<sub>More <sib/></sub></sub></test>"));
        assertEquals(q.has($("sib:eq(1)", q.document()).get(0)).size(), 2, "Size");
        assertEquals(q.has($("sib:eq(1)", q.document()).get(0)).get(0), q.get(1), "Element");
        assertEquals(q.has($("sib:eq(1)", q.document()).get(0)).get(1), q.get(2), "Element");
        assertEquals(q.has($("container", q.document()).get(0)).isEmpty(), true, "isEmpty");
    }

    @Test
    public void isSelector() throws Exception {
        Q q = $("sub", $("<test><sub/><sub>content</sub></test>"));
        assertEquals(q.is("sub"), true, "Is sub");
        assertEquals(q.is("sib"), false, "Is sib");
    }

    @Test
    public void isPredicate() throws Exception {
        Q q = $("sub", $("<test><sub/><sub>content</sub></test>"));
        assertEquals(q.is(new ElementPredicate() {
            @Override
            public boolean apply(Element element) {
                return true;
            }
        }), true, "Is true");
        assertEquals(q.is(new ElementPredicate() {
            @Override
            public boolean apply(Element element) {
                return false;
            }
        }), false, "Is false");
    }

    @Test
    public void isQ() throws Exception {
        Q q = $("sub", $("<test><sub/><sub>content</sub></test>"));
        assertEquals(q.is(q), true, "Is sub");
        assertEquals(q.is($("sub:eq(1)", q.document())), true, "Is sub");
        assertEquals(q.is($("test", q.document())), false, "Is test");
    }

    @Test
    public void isElement() throws Exception {
        Q q = $("sub", $("<test><sub/><sub>content</sub></test>"));
        assertEquals(q.is(q.get(0)), true, "Is sub");
        assertEquals(q.is($("test", q.document()).get(0)), false, "Is test");
    }

    @Test
    public void last() throws Exception {
        Q q = $("sub", $("<test><sub/><sub>content</sub></test>"));
        assertEquals(q.last().size(), 1, "Size");
        assertEquals(q.last().get(0), q.get(1), "Selected item");
        assertEquals(q.last().last().get(0), q.get(1), "Selected item");
    }

    @Test
    public void mapToElement() throws Exception {
        Q q = $("<test><sub><sib/><sib/></sub> <sub/> <sub><sib/></sub></test>");
        final Q sibs = q.find("sib");
        Q mapped = q.find("sub").map(new ElementToElement() {
            @Override
            public Element apply(Element element) {
                return $(element).find("sib").get(0);
            }
        });
        assertEquals(mapped.size(), 2, "Size");
        assertEquals(mapped.get(0), sibs.get(0), "Element");
        assertEquals(mapped.get(1), sibs.get(2), "Element");

        mapped = q.find("sub").map(new ElementToElement() {
            @Override
            public Element apply(Element element) {
                return sibs.get(0);
            }
        });
        assertEquals(mapped.size(), 1, "Size");
        assertEquals(mapped.get(0), sibs.get(0), "Element");
    }

    @Test
    public void mapToElements() throws Exception {
        Q q = $("<test><sub><sib/><sib/></sub> <sub/> <sub><sib/></sub></test>");
        final Q sibs = q.find("sib");
        Q mapped = q.find("sub").map(new ElementToElements() {
            @Override
            public Element[] apply(Element element) {
                return $(element).find("sib").get();
            }
        });
        assertEquals(mapped.size(), 3, "Size");
        assertEquals(mapped.get(0), sibs.get(0), "Element");
        assertEquals(mapped.get(1), sibs.get(1), "Element");
        assertEquals(mapped.get(2), sibs.get(2), "Element");

        mapped = q.find("sub").map(new ElementToElements() {
            @Override
            public Element[] apply(Element element) {
                return new Element[] { sibs.get(0), null, sibs.get(1) };
            }
        });
        assertEquals(mapped.size(), 2, "Size");
        assertEquals(mapped.get(0), sibs.get(0), "Element");
        assertEquals(mapped.get(1), sibs.get(1), "Element");

        mapped = q.find("sub").map(new ElementToElements() {
            @Override
            public Element[] apply(Element element) {
                return null;
            }
        });
        assertEquals(mapped.size(), 0, "Size");
    }

    @Test
    public void mapToQ() throws Exception {
        Q q = $("<test><sub><sib/><sib/><foo/></sub> <sub/> <sub><bar/><sib/></sub></test>");
        final Q sibs = q.find("sib");
        Q mapped = q.find("sub").map(new ElementToQ() {
            @Override
            public Q apply(Element element) {
                return $(element).find("sib");
            }
        });
        assertEquals(mapped.size(), 3, "Size");
        assertEquals(mapped.get(0), sibs.get(0), "Element");
        assertEquals(mapped.get(1), sibs.get(1), "Element");
        assertEquals(mapped.get(2), sibs.get(2), "Element");

        mapped = q.find("sub").map(new ElementToQ() {
            @Override
            public Q apply(Element element) {
                return $(new Element[] { sibs.get(0), sibs.get(1) });
            }
        });
        assertEquals(mapped.size(), 2, "Size");
        assertEquals(mapped.get(0), sibs.get(0), "Element");
        assertEquals(mapped.get(1), sibs.get(1), "Element");

        mapped = q.find("sub").map(new ElementToQ() {
            @Override
            public Q apply(Element element) {
                return $();
            }
        });
        assertEquals(mapped.size(), 0, "Size");

        mapped = q.find("sub").map(new ElementToQ() {
            @Override
            public Q apply(Element element) {
                return null;
            }
        });
        assertEquals(mapped.size(), 0, "Size");
    }

    @Test
    public void mapToGeneric() throws Exception {
        Q q = $("<test><sub id='1'><foo/></sub> <sub id='2'/> <sub id='3'><bar/><sib/></sub></test>");
        final Q sibs = q.find("sib");
        List<String> mapped = q.find("sub").map(new ElementTo<String>() {
            @Override
            public String apply(Element element) {
                return $(element).attr("id");
            }
        });
        assertEquals(mapped.size(), 3, "Size");
        assertEquals(mapped.get(0), "1", "Item");
        assertEquals(mapped.get(1), "2", "Item");
        assertEquals(mapped.get(2), "3", "Item");

        mapped = q.find("sub").map(new ElementTo<String>() {
            @Override
            public String apply(Element element) {
                String id = $(element).attr("id");

                if (id.equals("2")) {
                    return null;
                }

                return id;
            }
        });
        assertEquals(mapped.size(), 3, "Size");
        assertEquals(mapped.get(0), "1", "Item");
        assertEquals(mapped.get(1), null, "Item");
        assertEquals(mapped.get(2), "3", "Item");
    }

    @Test
    public void next() throws Exception {
        Q q = $("sub", document("<test><sub/><sub>content</sub> sibling content <sub>More content</sub></test>"));
        assertEquals($("sub:eq(2)", q.document()).next().isEmpty(), true, "isEmpty");
        assertEquals($("sub:eq(1)", q.document()).next().size(), 1, "Size");
        assertEquals($("sub:eq(1)", q.document()).next().get(0), q.get(2), "Element");
        assertEquals($("sub:eq(0)", q.document()).next().size(), 1, "Size");
        assertEquals($("sub:eq(0)", q.document()).next().get(0), q.get(1), "Element");

        q = $("sub", document("<test><container1><sub/><sub>content</sub> sibling content <sub>More content</sub></container1>" +
                              "<container2><sub/><sub>content</sub> sibling content <sub>More content</sub></container2></test>"));
        assertEquals($("container1 sub:eq(0), container2 sub:eq(1)", q.document()).next().size(), 2, "Size");
        assertEquals($("container1 sub:eq(0), container2 sub:eq(1)", q.document()).next().get(0), q.get(1), "Element");
        assertEquals($("container1 sub:eq(0), container2 sub:eq(1)", q.document()).next().get(1), q.get(5), "Element");
    }

    @Test
    public void nextSelector() throws Exception {
        Q q = $("sub", document("<test><sub/><sib/><sub>content</sub> sibling content <sub>More content</sub></test>"));
        assertEquals($("sub:eq(0)", q.document()).next("sub").isEmpty(), true, "isEmpty");
        assertEquals($("sub:eq(0)", q.document()).next("sib").size(), 1, "Size");
        assertEquals($("sub:eq(0)", q.document()).next("sib").get(0), $("sib", q.document()).get(0), "Element");
        assertEquals($("sub:eq(0)", q.document()).next("argle").isEmpty(), true, "isEmpty");
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
    public void parent() throws Exception {
        Q q = $("inner", $("<test>" +
                           "  <container1><outer1><inner/></outer1></container1>" +
                           "  <container2><outer2>Some<sib/> <inner/> content with <sibling/></outer2></container2>" +
                           "</test>"));
        assertEquals(q.parent().size(), 2, "Size");
        assertEquals(q.parent().get(0), $("outer1", q.document()).get(0), "Element");
        assertEquals(q.parent().get(1), $("outer2", q.document()).get(0), "Element");
        assertEquals($("sib, sibling", q.document()).parent().size(), 1, "Size");
        assertEquals($("test", q.document()).parent().isEmpty(), true, "isEmpty");
    }

    @Test
    public void parentSelector() throws Exception {
        Q q = $("inner", $("<test>" +
                           "  <container1><outer1><inner/></outer1></container1>" +
                           "  <container2><outer2>Some<sib/> <inner/> content with <sibling/></outer2></container2>" +
                           "</test>"));
        assertEquals(q.parent("outer1").size(), 1, "Size");
        assertEquals(q.parent("outer1").get(0), $("outer1", q.document()).get(0), "Element");
        assertEquals(q.parent("outer2").size(), 1, "Size");
        assertEquals(q.parent("outer2").get(0), $("outer2", q.document()).get(0), "Element");
        assertEquals(q.parent("test").isEmpty(), true, "isEmpty");
        assertEquals($("test", q.document()).parent("*").isEmpty(), true, "isEmpty");
    }

    @Test
    public void parents() throws Exception {
    }

    @Test
    public void parentsSelector() throws Exception {
    }

    @Test
    public void prev() throws Exception {
        Q q = $("sub", document("<test><sub/><sub>content</sub> sibling content <sub>More content</sub></test>"));
        assertEquals($("sub:eq(0)", q.document()).prev().isEmpty(), true, "isEmpty");
        assertEquals($("sub:eq(1)", q.document()).prev().size(), 1, "Size");
        assertEquals($("sub:eq(1)", q.document()).prev().get(0), q.get(0), "Element");
        assertEquals($("sub:eq(2)", q.document()).prev().size(), 1, "Size");
        assertEquals($("sub:eq(2)", q.document()).prev().get(0), q.get(1), "Element");

        q = $("sub", document("<test><container1><sub/><sub>content</sub> sibling content <sub>More content</sub></container1>" +
                              "<container2><sub/><sub>content</sub> sibling content <sub>More content</sub></container2></test>"));
        assertEquals($("container1 sub:eq(2), container2 sub:eq(1)", q.document()).prev().size(), 2, "Size");
        assertEquals($("container1 sub:eq(2), container2 sub:eq(1)", q.document()).prev().get(0), q.get(1), "Element");
        assertEquals($("container1 sub:eq(2), container2 sub:eq(1)", q.document()).prev().get(1), q.get(3), "Element");
    }

    @Test
    public void prevSelector() throws Exception {
        Q q = $("sub", document("<test><sub/><sib/><sub>content</sub> sibling content <sub>More content</sub></test>"));
        assertEquals($("sub:eq(2)", q.document()).prev("sub").size(), 1, "Size");
        assertEquals($("sub:eq(2)", q.document()).prev("sub").get(0), q.get(1), "Element");
        assertEquals($("sub:eq(2)", q.document()).prev("sib").isEmpty(), true, "isEmpty");
        assertEquals($("sub:eq(2)", q.document()).prev("argle").isEmpty(), true, "isEmpty");
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
