package org.bl.parsetest.qname;

import org.bl.parsetest.qname.exeption.IllegalNameException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser
 *
 * @author blupashko
 * @version 0.1
 * @since 18.03.2015
 */

public class Parser {

    private static Logger LOG = LoggerFactory.getLogger(Parser.class.getName());

    private static final String XML_NAME_START_CHAR = "[\\_A-Z\\a-z" +
            "\\u00C0-\\u00D6\\u00D8-\\u00F6\\u00F8-\\u02FF\\u0370-\\u037D\\u037F-\\u1FFF" +
            "\\u200C-\\u200D\\u2070-\\u218F\\u2C00-\\u2FEF\\u3001-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFFD]",

    XML_NAME_CHAR = XML_NAME_START_CHAR + "|[\\-\\.0-9\\u00B7\\u0300-\\u036F\\u203F-\\u2040]",

    /**
     * Any valid XML Name for more details please see http://www.w3.org/TR/xml11/#sec-common-syn
     */

    PREFIX = "^" + XML_NAME_START_CHAR + "(" + XML_NAME_CHAR + ")*",

    /**
     * Any Unicode character except:      '/', ':', '[', ']', '*', ''', '"', '|' or any whitespace character
     */

    NON_SPACE_LOCAL_NAME = "[^\\/\\:\\[\\]\\*\\'\\\"\\|\\s]",

    /**
     * Any Unicode character except: '.', '/', ':', '[', ']', '*', ''', '"', '|' or any whitespace character
     */
    NON_SPACE_SIMPLE_NAME = "[^\\.\\/\\:\\[\\]\\*\\'\\\"\\|\\s]",

    CHAR = "(" + NON_SPACE_LOCAL_NAME + ")|([\\ ])",

    STRING = "(" + CHAR + ")+",

    /**
     * One and two chars patterns for local name
     */

    ONE_CHAR_LOCAL_NAME = NON_SPACE_LOCAL_NAME + "$",

    TWO_CHAR_LOCAL_NAME = NON_SPACE_LOCAL_NAME + "{2}$",

    /**
     * One and two chars patterns for simple name
     */

    ONE_CHAR_SIMPLE_NAME = "^" + NON_SPACE_SIMPLE_NAME + "$",

    TWO_CHAR_SIMPLE_NAME = "^(" + NON_SPACE_SIMPLE_NAME + "\\.)$|(^\\." + NON_SPACE_SIMPLE_NAME + ")$|(^" + NON_SPACE_SIMPLE_NAME + "{2})$",

    /**
     * Three and more chars pattern
     */

    THREE_OR_MORE_CHAR_NAME = NON_SPACE_LOCAL_NAME + STRING + NON_SPACE_LOCAL_NAME + "$",


    LOCAL_NAME = "(" + THREE_OR_MORE_CHAR_NAME + ")|(" + TWO_CHAR_LOCAL_NAME + ")|(" + ONE_CHAR_LOCAL_NAME + ")",

    /**
     * Final prefixed name pattern
     */
    PREFIXED_NAME = PREFIX + ":(" + LOCAL_NAME + ")",

    /**
     * final simple name pattern
     */

    SIMPLE_NAME = "(^" + THREE_OR_MORE_CHAR_NAME + ")|(" + TWO_CHAR_SIMPLE_NAME + ")|(" + ONE_CHAR_SIMPLE_NAME + ")",

    /**
     * Final regular expression pattern
     */

    NAME = "(" + SIMPLE_NAME + ")|(" + PREFIXED_NAME + ")";


    private static final Pattern PATTERN = Pattern.compile(NAME);


    public static void main(String[] args) throws IllegalNameException {
        if (args.length < 1) {
            throw new IllegalNameException();
        }
        String name = "";
        for (int i = 0; i < args.length; i++) {
            name = name.concat(args[i]).concat(" ");
        }
        LOG.info("Parsing name: {}", name);
        try {
            QName q = new Parser().parse(name);
            if (!"".equals(q.getPrefix())){
                LOG.info("Prefix: {}, local name: {}", q.getPrefix(), q.getLocalName());
                LOG.info("Full name: {}", q.getAsString());
            }
            else {
                LOG.info("Simple name: {}", q.getLocalName());
            }
        } catch (IllegalNameException e) {
            LOG.error(e.toString());
        }
    }

    /**
     * Implements:
     * - parsing 'name'
     * - creating object QName if name could be parsed
     * - if not - throws IllegalNameException
     *
     * @param name - qualifier name
     * @return parsed qualifier name instance
     * @throws IllegalNameException
     */
    public QName parse(String name) throws IllegalNameException {

        Matcher matcher = PATTERN.matcher(name);
        String[] splitted = name.split(":");

        boolean isMatched = matcher.matches();
        if (isMatched && splitted.length == 2) {
            return new QName(splitted[0], splitted[1]);
        } else if (isMatched) {
            return new QName("", splitted[0]);
        } else {
            throw new IllegalNameException();
        }
    }
}
