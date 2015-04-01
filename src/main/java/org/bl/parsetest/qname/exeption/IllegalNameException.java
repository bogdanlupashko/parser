package org.bl.parsetest.qname.exeption;

/**
 * IllegalNameException
 *
 * @author blupashko
 * @version 0.1
 * @since 17.03.2015
 */

public class IllegalNameException extends Exception {

    private static final String MESSAGE = "\n Please use one of these examples: \n \t 1. 'name' \n \t 2. 'prefix:name' \n \t 3. 'prefix:na me'";

    public IllegalNameException() {
        this(MESSAGE);
    }

    public IllegalNameException(String message) {
        super(message);
    }
}
