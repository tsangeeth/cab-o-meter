package com.sangeeth.cab.web.authentication;

import jakarta.servlet.http.HttpSession;

public class AuthenticationStore {

    static final String SESSION_KEY = "cab.authentication";

    private AuthenticationStore() {
    }

    public static Authentication get(HttpSession session) {
        if (session == null) {
            return null;
        }
        Object value = session.getAttribute(SESSION_KEY);
        return value instanceof Authentication authentication ? authentication : null;
    }

    public static void store(HttpSession session, Authentication authentication) {
        session.setAttribute(SESSION_KEY, authentication);
    }

    public static void clear(HttpSession session) {
        if (session != null) {
            session.removeAttribute(SESSION_KEY);
            session.invalidate();
        }
    }
}
