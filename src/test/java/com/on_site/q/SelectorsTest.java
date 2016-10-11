package com.on_site.q;

import static com.on_site.q.Q.$;

import com.on_site.util.TestBase;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

public class SelectorsTest extends TestBase {
    @BeforeClass
    public void addSelectors() {
        Selectors.addAll();
    }

    @Test
    public void regex() throws Exception {
        Q q = $("<test><sub>This is a <b>test</b> of the regex selector.</sub> <sub>Alternate <b>sub</b> tag.</sub></test>");
        Q subs = q.find("sub");
        Q filtered = q.find("sub:regex(a\\s+test\\s+of\\s+the\\s+regex)");
        assertEquals(filtered.size(), 1, "Size");
        assertEquals(filtered.get(0), subs.get(0), "Element");

        filtered = q.find("sub:regex(^Alternate sub tag.$)");
        assertEquals(filtered.size(), 1, "Size");
        assertEquals(filtered.get(0), subs.get(1), "Element");
    }
}
