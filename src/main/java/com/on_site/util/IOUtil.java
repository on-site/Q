package com.on_site.util;

import com.google.common.io.ByteSource;
import com.google.common.io.CharSource;

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
        InputStreamSource[] sources = new InputStreamSource[streams.length];

        for (int i = 0; i < streams.length; i++) {
            sources[i] = new InputStreamSource(streams[i]);
        }

        return ByteSource.concat(sources).openStream();
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
        ReaderSource[] sources = new ReaderSource[readers.length];

        for (int i = 0; i < readers.length; i++) {
            sources[i] = new ReaderSource(readers[i]);
        }

        return CharSource.concat(sources).openStream();
    }

    /**
     * ByteSource for InputStream objects.
     */
    public static class InputStreamSource extends ByteSource {
        private final InputStream stream;

        /**
         * Construct the source with the given stream.
         *
         * @param stream The stream to construct with.
         */
        public InputStreamSource(InputStream stream) {
            this.stream = stream;
        }

        /**
         * Get the stream input.
         *
         * @return The stream this object was constructed with.
         */
        @Override
        public InputStream openStream() {
            return stream;
        }
    }

    /**
     * CharSource for Reader objects.
     */
    public static class ReaderSource extends CharSource {
        private final Reader reader;

        /**
         * Construct the source with the given reader.
         *
         * @param reader The reader to construct with.
         */
        public ReaderSource(Reader reader) {
            this.reader = reader;
        }

        /**
         * Get the reader input.
         *
         * @return The reader this object was constructed with.
         */
        @Override
        public Reader openStream() {
            return reader;
        }
    }
}
