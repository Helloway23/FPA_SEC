package com.HelloWay.HelloWay.Security.Jwt;

import com.HelloWay.HelloWay.services.UserDetailsImpl;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static java.lang.System.in;

@Component
public class CustomSessionRegistry implements SessionRegistry {

    private final ConcurrentMap<String, SessionInformation> sessionInformationMap = new ConcurrentHashMap<>();
    private final Map<String, String> usersInformationMap = new LinkedHashMap<>();


    @Override
    public void registerNewSession(String sessionId, Object principal) {
        if (principal instanceof UserDetailsImpl) {
            UserDetailsImpl user = (UserDetailsImpl) principal;
            SessionInformation sessionInformation = new SessionInformation(user, sessionId, new Date());
            sessionInformationMap.put(sessionId, sessionInformation);
        }
    }


    public void setNewUserOnTable(String sessionId,String tableId) {
            usersInformationMap.put(sessionId, tableId);
    }

    @Override
    public void removeSessionInformation(String sessionId) {
        SessionInformation sessionInformation = sessionInformationMap.get(sessionId);
        if (sessionInformation != null) {
            sessionInformation.expireNow();
            sessionInformationMap.remove(sessionId);
        }
    }


    //we will use this after passing a command
    public void removeUserFromTable(String sessionId) {
        if (usersInformationMap.containsKey(sessionId)) {
            sessionInformationMap.remove(sessionId);
        }
        else System.out.println("user not sated in this table");
    }

    @Override
    public SessionInformation getSessionInformation(String sessionId) {
        return sessionInformationMap.get(sessionId);
    }

    @Override
    public List<Object> getAllPrincipals() {
        List<Object> principals = new ArrayList<>();
        for (SessionInformation sessionInformation : sessionInformationMap.values()) {
            if (sessionInformation != null && sessionInformation.isExpired()) {
                principals.add(sessionInformation.getPrincipal());
            }
        }
        return principals;
    }

    @Override
    public List<SessionInformation> getAllSessions(Object principal, boolean includeExpiredSessions) {
        List<SessionInformation> sessions = new ArrayList<>();
        for (SessionInformation sessionInformation : sessionInformationMap.values()) {
         //   i have remove this  down
            if (sessionInformation != null && ((UserDetailsImpl) sessionInformation.getPrincipal()).getUsername().equals(principal) ) {
                if (includeExpiredSessions || !sessionInformation.isExpired()) {
                    sessions.add(sessionInformation);
                }
            }
        }
        return sessions;
    }

    public List<String> getAllUsersSessionsIdSatedInThisTable(String tableId){
        List<String> sessionsInThisTable = new ArrayList<>();
        for (String sessionId : usersInformationMap.keySet()){
            if (sessionId != null && usersInformationMap.get(sessionId).equals(tableId)){
                sessionsInThisTable.add(sessionId);
            }
        }
        return sessionsInThisTable;
    }

    @Override
    public void refreshLastRequest(String sessionId) {
        SessionInformation sessionInformation = sessionInformationMap.get(sessionId);
        if (sessionInformation != null) {
            sessionInformation.refreshLastRequest();
        }
    }
}
