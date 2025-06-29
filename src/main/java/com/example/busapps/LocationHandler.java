package com.example.busapps;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashSet;
import java.util.Set;

@Component
public class LocationHandler extends TextWebSocketHandler {

    private final Set<WebSocketSession> passengers = new HashSet<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        passengers.add(session);
        System.out.println("Passenger connected: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            LocationMessage location = objectMapper.readValue(message.getPayload(), LocationMessage.class);

            for (WebSocketSession passenger : passengers) {
                if (passenger.isOpen()) {
                    passenger.sendMessage(new TextMessage(objectMapper.writeValueAsString(location)));
                }
            }

            System.out.println("Location received: " + location.getLatitude() + ", " + location.getLongitude());
        } catch (Exception e) {
            System.err.println("Invalid location format: " + e.getMessage());
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        passengers.remove(session);
        System.out.println("Disconnected: " + session.getId());
    }
}