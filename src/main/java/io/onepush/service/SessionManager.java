package io.onepush.service;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yaoyasong on 2016/4/29.
 */
@Component
public class SessionManager {
    private ConcurrentHashMap<String,WebSocketSession> sessions = new ConcurrentHashMap<>();

    public ConcurrentHashMap<String, WebSocketSession> getSessions() {
        return sessions;
    }
}
