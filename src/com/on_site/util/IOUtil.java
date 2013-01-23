package com.on_site.util;

import com.google.common.io.ByteStreams;
import com.google.common.io.CharStreams;
import com.google.common.io.InputSupplier;

import java.io.InputStream;
import java.io.IOException;
import java.io.Reader;

public class IOUtil {
    private IOUtil() {
        /* Disable instantiation for static class. */
    }

    public static InputStream join(InputStream... streams) throws IOException {
        InputStreamSupplier[] suppliers = new InputStreamSupplier[streams.length];

        for (int i = 0; i < streams.length; i++) {
            suppliers[i] = new InputStreamSupplier(streams[i]);
        }

        return ByteStreams.join(suppliers).getInput();
    }

    public static Reader join(Reader... readers) throws IOException {
        ReaderSupplier[] suppliers = new ReaderSupplier[readers.length];

        for (int i = 0; i < readers.length; i++) {
            suppliers[i] = new ReaderSupplier(readers[i]);
        }

        return CharStreams.join(suppliers).getInput();
    }

    public static class InputStreamSupplier implements InputSupplier<InputStream> {
        private final InputStream stream;

        public InputStreamSupplier(InputStream stream) {
            this.stream = stream;
        }

        public InputStream getInput() {
            return stream;
        }
    }

    public static class ReaderSupplier implements InputSupplier<Reader> {
        private final Reader reader;

        public ReaderSupplier(Reader reader) {
            this.reader = reader;
        }

        public Reader getInput() {
            return reader;
        }
    }
}
