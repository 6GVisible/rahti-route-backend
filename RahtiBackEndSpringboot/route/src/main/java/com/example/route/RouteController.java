package com.example.route;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalTime;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import org.json.JSONObject;

@RestController
public class RouteController {
    // Define the fields for the route information

    private double decisionX = 70.0;
    private double decisionY = 18.89;

    public static class RequestDTO {
        public int sessionID;
        public int tripID;
        public LocalDate date;
        public LocalTime time;
        public double startX;
        public double startY;
        public String preferences;
        public double locationX;
        public double locationY;
        public String startAddress;
        public String endAddress;
    }

    //define methods
    @GetMapping("/route")
    public String getRoute() {
        return "Route information July 21";
    }

    @PostMapping("/route/request")
    public Map<String, Object> getRouteRequest(@RequestBody RequestDTO request) {
        Map<String, Object> recommended = new HashMap<>();
        recommended.put("clientId", request.sessionID);
        recommended.put("sessionId", request.sessionID);
        recommended.put("tripID", request.tripID);
        recommended.put("time", request.time.toString());
        recommended.put("date", request.date.toString());
        try {
            
        if (isWithinDistance(request.locationX, request.locationY, request.startX, request.startY, 10)) {
            recommended.put("routesarray", initialdecision());
        } else if (isWithinDistance(request.locationX, request.locationY, decisionX, decisionY, 10)) {
            recommended.put("routesarray", seconddecision());
        } else {
            recommended.put("error", "No routes found");
        }

        logSuggestedRoutes(new JSONObject(recommended), request.time);

        return recommended;
        } catch (Exception e) {
            e.printStackTrace();
            return Map.of("Error", "This URL supports POST requests only. Please use a POST request with the appropriate body.");
        }
    }


    @GetMapping("/route/end")
    public String endRoute() {
        return "Confirm: Route ended";
    }

    @PostMapping("/preferences")
    public Map<String, String> setPreferences(@RequestBody Map<String, String> prefs) {
        try{
        return prefs;            
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return Map.of("Error", "This URL supports POST requests only. Please use a POST request with the appropriate body.");
        }
    }

    // Utility method to compute distance between two points
    private boolean isWithinDistance(double x1, double y1, double x2, double y2, double maxDistanceMeters) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance <= maxDistanceMeters;
    }

    //define methods
    public void logSuggestedRoutes(JSONObject logjson, LocalTime time ){
        // Step 1: generate the suggested routes JSON
        JSONObject logsuggestedRoutes = logjson;

        // Step 2: define the directory where you want to store files
        //String directoryPath = "C:\\Users\\vbasnaya18\\OneDrive - Oulun yliopisto\\Desktop\\RahtiBackEndApp\\route"; // relative or absolute path
        String filename = String.format("suggestedRoutes.json");

        // Step 3: write JSON to file
        if (logjson == null) return;
        try (FileWriter fileWriter = new FileWriter(filename)) {
            fileWriter.write(logsuggestedRoutes.toString(4)); // pretty print with 4 spaces
            System.out.println("Suggested routes logged to: " + filename);
        } catch (IOException e) {
            System.err.println("Error writing suggested routes to file: " + e.getMessage());
            e.printStackTrace();
        }

    }
//define methods
    public Map<String, Object> initialdecision( ){
        try {
        String jsonText = new String(Files.readAllBytes(Paths.get("decision1.json")));
        JSONObject jsonObject = new JSONObject(jsonText);
        Map<String, Object> map = jsonObject.toMap();

        return map;
         } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return Map.of("Error", e.getMessage());
        }
    }
    
//define methods
    public Map<String, Object> seconddecision( ){

        Map<String, Object> routesArray = new HashMap<>();

        // Create route objects
        //Map<String, String> route1 = new HashMap<>();
        routesArray.put("1", "red");


        //Map<String, String> route2 = new HashMap<>();
        routesArray.put("4", "X");

        //Map<String, String> route3 = new HashMap<>();
        //route3.put("3", "blue");
        routesArray.put("5", "Y");

        return routesArray;
    }


}
