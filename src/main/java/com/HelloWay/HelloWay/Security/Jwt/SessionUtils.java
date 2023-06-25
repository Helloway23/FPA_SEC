package com.HelloWay.HelloWay.Security.Jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;


import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SessionUtils  {

    @Autowired
    private HttpServletResponse response;

    @Value("${employeemanager.app.jwtCookieName}")
    private String jwtCookie;

    private static final Map<String, HttpSession> activeSessions = new HashMap<>();

    public void disconnectUsers(List<String> sessionIds) {
        for (String sessionId : sessionIds) {
            HttpSession session = getSessionById(sessionId);
            if (session != null) {
                // Perform actions to disconnect the user
                session.invalidate();
                removeSession(session);
                removeSessionCookie(sessionId);
            }
        }
    }

    private void removeSessionCookie(String sessionId) {

        ResponseCookie cookie = ResponseCookie.from(jwtCookie, null).path("/api").build();
        Cookie sessionCookie = new Cookie("alaeddine", sessionId);
        sessionCookie.setMaxAge(0);
        sessionCookie.setPath("/api");
        response.addCookie(sessionCookie);

        Cookie sessionCookie1 = new Cookie("JSESSIONID", sessionId);
        sessionCookie1.setMaxAge(0);
        sessionCookie1.setPath("/api");
        response.addCookie(sessionCookie1);
    }

    public HttpSession getSessionById(String sessionId) {
        return activeSessions.get(sessionId);
    }

    public void addSession(HttpSession session) {
        activeSessions.put(session.getId(), session);
    }

    public void removeSession(HttpSession session) {
        activeSessions.remove(session.getId());
    }
}
