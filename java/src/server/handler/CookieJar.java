package server.handler;

import java.util.*;

/**
 * A list of parsed cookies.
 */
public class CookieJar implements Iterable<Cookie> {
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
            for (String cookie : listOfCookies.split(";")) {
                addCookie(cookie.trim());
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
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Cookie> iterator() {
        return Collections.unmodifiableCollection(m_cookies).iterator();
    }
}
