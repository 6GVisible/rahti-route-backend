package com.example.route;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class RouteController {

    @GetMapping("/route")
    public String getRoute() {
        return "Best route calculated";
    }

    @GetMapping("/route/check")
    public String checkRoute() {
        return "Route check OK";
    }

    @PostMapping("/route/end")
    public String endRoute() {
        return "Route ended";
    }

    @PostMapping("/preferences")
    public Map<String, String> setPreferences(@RequestBody Map<String, String> prefs) {
        return prefs;
    }
}
