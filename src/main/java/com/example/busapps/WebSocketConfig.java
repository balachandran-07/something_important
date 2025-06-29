package com.example.busapps;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final LocationHandler locationHandler;

    public WebSocketConfig(LocationHandler locationHandler) {
        this.locationHandler = locationHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(locationHandler, "/ws/location")
                .setAllowedOrigins("*"); // Allow all origins (for now)
    }
}