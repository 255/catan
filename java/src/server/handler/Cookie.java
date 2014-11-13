package server.handler;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * A class representing a cookie.
 */
public class Cookie {
    private static final URLDecoder decoder = new URLDecoder();
    private static final String ENCODING = "UTF-8";

    private String name;
    private String value;

    public Cookie(String rawCookie) {
        String cookie;
        try {
            cookie = decoder.decode(rawCookie, ENCODING);
        } catch (UnsupportedEncodingException e) {
            assert false : "Invalid encoding selected for cookies.";
            e.printStackTrace();
            return;
        }

        int equalsIndex = cookie.indexOf('=');

        if (equalsIndex <= 0 || equalsIndex >= cookie.length()) {
            throw new IllegalArgumentException("Improperly formatted cookie: \"" + cookie + "\"");
        }

        name = cookie.substring(0, equalsIndex);
        value = cookie.substring(equalsIndex + 1);

        // remove surrounding quotation marks on the value, if any
        if (value.startsWith("\"") && value.endsWith("\"")) {
            value = value.substring(1, value.length()-1);
        }
    }

    public boolean nameIs(String name) {
        return this.name.equalsIgnoreCase(name);
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
