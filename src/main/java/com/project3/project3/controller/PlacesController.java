package com.project3.project3.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
public class PlacesController {

    private final String apiKey = System.getenv("GOOGLE_MAPS_API_KEY");

    @GetMapping("/places")
    public ResponseEntity<?> getNearbyPlaces(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam(defaultValue = "5000") int radius,
            @RequestParam(defaultValue = "park") String type,
            @RequestParam(defaultValue = "trail") String keyword) {

        String url = String.format(
                "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=%f,%f&radius=%d&type=%s&keyword=%s&key=%s",
                latitude, longitude, radius, type, keyword, apiKey);

        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            System.err.println("Error fetching data: " + e.getMessage());
            return ResponseEntity.status(500).body("Error fetching data");
        }
    }
}

