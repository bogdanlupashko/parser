package org.bl.parsetest.qname;

/**
 * QName
 *
 * @version 0.1
 *
 * @author blupashko
 * @since 17.03.2015
 */

public class QName {
    private String prefix;
    private String localName;

    public QName(String prefix, String localName){
        this.prefix = prefix;
        this.localName = localName;
    }

    public String getPrefix(){
        return prefix;
    }

    public String getLocalName(){
        return localName;
    }

    public String getAsString(){
        if (!"".equals(prefix)) {
            return "[" + prefix + ":]" + localName;
        }else {
            return localName;
        }
    }

}
