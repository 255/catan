package server.handler;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * A list of parsed cookies.
 */
public class CookieJar implements Iterable<Cookie> {
    private static final URLDecoder decoder = new URLDecoder();
    private static final String ENCODING = "UTF-8";

    private Collection<Cookie> m_cookies;

    public CookieJar() {
        m_cookies = new ArrayList<>();
    }

    public CookieJar(Collection<String> listOfCookies) {
        this();
        addCookies(listOfCookies);
    }

    /**
     * Parse a list of cookies.
     * Each item in the collection may itself be a semicolon-separated list of cookies.
     * @param cookieList the list of cookies
     */
    public void addCookies(Collection<String> cookieList) {
        for (String listOfCookies : cookieList) {
            try {
                for (String cookie : decoder.decode(listOfCookies, ENCODING).split(";")) {
                    addCookie(cookie.trim());
                }
            }
            catch (UnsupportedEncodingException e) {
                assert false : "Bad encoding.";
                e.printStackTrace();
            }
        }
    }

    /**
     * Add a single cookie in name="value" format.
     * @param cookie the cookie to add
     */
    public void addCookie(String cookie) {
        m_cookies.add(new Cookie(cookie));
    }

    /**
     * Add a single cookie with separate names and values
     * @param name the name of the cookie to add
     * @param value the value of the cookie to add
     */
    public void addCookie(String name, String value) {
        m_cookies.add(new Cookie(name, value));
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Cookie> iterator() {
        return Collections.unmodifiableCollection(m_cookies).iterator();
    }

    /**
     * Convert the cookies to a ; separated list
     * @return the list of cookies
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Cookie cookie : m_cookies) {
            sb.append(cookie.toString());
            sb.append(';');
        }

        return sb.toString();
    }
}
