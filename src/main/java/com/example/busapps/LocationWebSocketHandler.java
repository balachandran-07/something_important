package com.example.busapps;

import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocationWebSocketHandler extends TextWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(LocationWebSocketHandler.class);

    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        logger.info("🟢 New WebSocket session connected: {}", session.getId());
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        logger.info("📨 Message received: {}", message.getPayload());
        for (WebSocketSession s : sessions) {
            if (s.isOpen()) {
                s.sendMessage(message);
                logger.info("📤 Sent message to session: {}", s.getId());
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        logger.warn("WebSocket session closed: {} | Reason: {}", session.getId(), status);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        logger.error("Transport error in session: " + session.getId(), exception);
    }
}
