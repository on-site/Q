package com.on_site.q;

/**
 * An exception that represents something gone wrong with parsing or
 * otherwise processing XML.
 *
 * @author Mike Virata-Stone
 */
public class XmlException extends RuntimeException {
    /**
     * Construct the exception with the given message.
     *
     * @param message The message for this exception.
     */
    public XmlException(String message) {
        super(message);
    }

    /**
     * Construct the exception with the given cause.
     *
     * @param cause The cause of this exception.
     */
    public XmlException(Throwable cause) {
        super(cause);
    }

    /**
     * Construct the exception with the given message and cause.
     *
     * @param message The message for this exception.
     * @param cause The cause of this exception.
     */
    public XmlException(String message, Throwable cause) {
        super(message, cause);
    }
}
