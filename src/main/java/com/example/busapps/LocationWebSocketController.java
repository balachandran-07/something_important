package com.example.busapps;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LocationWebSocketController {

    @GetMapping("/status")
    public String checkStatus() {
        return "WebSocket backend is running!";
    }
}
