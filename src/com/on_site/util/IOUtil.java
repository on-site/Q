package com.on_site.util;

import com.google.common.io.ByteStreams;
import com.google.common.io.CharStreams;
import com.google.common.io.InputSupplier;

import java.io.InputStream;
import java.io.IOException;
import java.io.Reader;

/**
 * Utility class to allow input stream/reader utility functions.
 *
 * @author Mike Virata-Stone
 */
public class IOUtil {
    private IOUtil() {
        /* Disable instantiation for static class. */
    }

    /**
     * Join all the given streams into 1 continuous stream.  The
     * streams are streamed in the order they are passed to the
     * method.
     *
     * @param streams The streams to join.
     * @return The joined streams into 1 InputStream.
     */
    public static InputStream join(InputStream... streams) throws IOException {
        InputStreamSupplier[] suppliers = new InputStreamSupplier[streams.length];

        for (int i = 0; i < streams.length; i++) {
            suppliers[i] = new InputStreamSupplier(streams[i]);
        }

        return ByteStreams.join(suppliers).getInput();
    }

    /**
     * Join all the given readers into 1 continuous reader.  The
     * readers are streamed in the order they are passed to the
     * method.
     *
     * @param readers The readers to join.
     * @return The joined readers into 1 Reader.
     */
    public static Reader join(Reader... readers) throws IOException {
        ReaderSupplier[] suppliers = new ReaderSupplier[readers.length];

        for (int i = 0; i < readers.length; i++) {
            suppliers[i] = new ReaderSupplier(readers[i]);
        }

        return CharStreams.join(suppliers).getInput();
    }

    /**
     * InputSupplier for InputStream objects.
     */
    public static class InputStreamSupplier implements InputSupplier<InputStream> {
        private final InputStream stream;

        /**
         * Construct the supplier with the given stream.
         *
         * @param stream The stream to construct with.
         */
        public InputStreamSupplier(InputStream stream) {
            this.stream = stream;
        }

        /**
         * Get the stream input.
         *
         * @return The stream this object was constructed with.
         */
        public InputStream getInput() {
            return stream;
        }
    }

    /**
     * InputSupplier for Reader objects.
     */
    public static class ReaderSupplier implements InputSupplier<Reader> {
        private final Reader reader;

        /**
         * Construct the supplier with the given reader.
         *
         * @param reader The reader to construct with.
         */
        public ReaderSupplier(Reader reader) {
            this.reader = reader;
        }

        /**
         * Get the reader input.
         *
         * @return The reader this object was constructed with.
         */
        public Reader getInput() {
            return reader;
        }
    }
}
