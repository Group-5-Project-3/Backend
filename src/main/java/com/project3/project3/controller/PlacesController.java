package com.project3.project3.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @GetMapping("/places/city-state")
    public ResponseEntity<?> getPlacesByCityAndState(
            @RequestParam String city,
            @RequestParam String state,
            @RequestParam(defaultValue = "5000") int radius,
            @RequestParam(defaultValue = "park") String type,
            @RequestParam(defaultValue = "trail") String keyword) {
        try {
            double[] coordinates = getLatLongFromCityAndState(city, state);
            return getNearbyPlaces(coordinates[0], coordinates[1], radius, type, keyword);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            System.err.println("Error fetching data: " + e.getMessage());
            return ResponseEntity.status(500).body("Error fetching data");
        }
    }

    private double[] getLatLongFromCityAndState(String city, String state) {
        String geocodeUrl = String.format("https://maps.googleapis.com/maps/api/geocode/json?address=%s,%s&key=%s", city, state, apiKey);

        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(geocodeUrl, String.class);
            JsonNode geocodeJson = new ObjectMapper().readTree(response.getBody());

            if (!geocodeJson.has("results") || geocodeJson.get("results").isEmpty()) {
                throw new IllegalArgumentException("City and state not found");
            }

            JsonNode location = geocodeJson.get("results").get(0).get("geometry").get("location");
            double latitude = location.get("lat").asDouble();
            double longitude = location.get("lng").asDouble();

            return new double[]{latitude, longitude};
        } catch (Exception e) {
            throw new RuntimeException("Error fetching coordinates: " + e.getMessage());
        }
    }
}
