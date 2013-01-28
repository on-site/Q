package com.on_site.q;

import com.on_site.fn.ElementPredicate;

import java.util.regex.Pattern;

import org.w3c.dom.Element;

/**
 * A place for custom selectors that aren't built in with Frizzle, but
 * can be added easily if they are desired.
 *
 * @author Mike Virata-Stone
 */
public class Selectors {
    /**
     * @see #addRegex()
     */
    public static final Pseudo REGEX = new RegexPseudo();

    private Selectors() {
    }

    /**
     * Globally add all the pseudo selectors defined in this class.
     */
    public static void addAll() {
        addRegex();
    }

    /**
     * Add the regex pseudo selector.  It expects an argument that is
     * the regex to match.  A matching element must have the node text
     * match the regex, either partially or completely.  Note that
     * elements below the node being matched will not be included in
     * the node text, but their content will be included.
     *
     * For example, the following will work as expected:
     *
     * <code>$(":regex(some sub string)", document);</code>
     *
     * <code>$(":regex(^Matching\\s+an\\s+entire\\s+string\\.$)", document);</code>
     */
    public static void addRegex() {
        Q.globalExpr(REGEX);
    }

    private static class RegexPseudo extends Pseudo {
        private RegexPseudo() {
            super("regex");
        }

        @Override
        public ElementPredicate apply(String argument) {
            return new RegexPredicate(argument);
        }

        private static class RegexPredicate implements ElementPredicate {
            private final Pattern regex;

            private RegexPredicate(String regex) {
                this.regex = Pattern.compile(regex);
            }

            @Override
            public boolean apply(Element element) {
                return regex.matcher(element.getTextContent()).find();
            }
        }
    }
}
