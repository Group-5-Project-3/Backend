package com.project3.project3.utility;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.tinylog.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatGPTUtil {

    private ChatGPTUtil() {
        // Private constructor to prevent instantiation
    }

    public static String getChatGPTTrailReviewSentiments(String allTrailReviews) {
        String apiKey = System.getenv("CHAT_GPT_API_KEY");
        String url = "https://api.openai.com/v1/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        // Request body setup
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-4o-mini");
        requestBody.put("messages", List.of(
                Map.of("role", "system", "content", "You are a helpful assistant for writing sentiments based on the reviews a trail has."),
                Map.of("role", "system", "content", "The reviews will come as a single string with a number a period a and the review"),
                Map.of("role", "system", "content", "Higlight the key sentiments from all of the reviews in 3 sentences."),
                Map.of("role", "user", "content", allTrailReviews)
        ));

        Logger.info("Sending prompt to ChatGPT: {}", allTrailReviews);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Map.class);

            // Validate response
            if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
                Logger.error("Unexpected response from ChatGPT API: Status {}, Body {}", response.getStatusCode(), response.getBody());
                throw new IllegalStateException("Unexpected response from ChatGPT API");
            }

            // Parsing response
            Map<String, Object> responseBody = response.getBody();
            Logger.debug("ChatGPT API response body: {}", responseBody);

            List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");

            if (choices == null || choices.isEmpty()) {
                Logger.error("No choices found in ChatGPT response");
                throw new IllegalStateException("No choices found in ChatGPT response");
            }

            Map<String, Object> firstChoice = choices.get(0);
            Map<String, Object> message = (Map<String, Object>) firstChoice.get("message");
            String content = (String) message.get("content");

            if (content == null || content.isBlank()) {
                Logger.error("ChatGPT returned an empty response");
                throw new IllegalStateException("ChatGPT returned an empty response");
            }

            Logger.info("Successfully retrieved response from ChatGPT");
            return content.trim();

        } catch (Exception e) {
            Logger.error(e, "Failed to get response from ChatGPT API: {}", e.getMessage());
            throw new RuntimeException("Failed to get response from ChatGPT API: " + e.getMessage(), e);
        }
    }

    public static String getChatGPTResponse(String prompt) {
        String apiKey = System.getenv("CHAT_GPT_API_KEY");
        String url = "https://api.openai.com/v1/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        // Request body setup
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-4o-mini");
        requestBody.put("messages", List.of(
                Map.of("role", "system", "content", "You are a helpful assistant needed for adding descriptions for local trails."),
                Map.of("role", "system", "content", "Respond in 2-3 meaningful sentences"),
                Map.of("role", "user", "content", prompt)
        ));

        Logger.info("Sending prompt to ChatGPT: {}", prompt);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Map.class);

            // Validate response
            if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
                Logger.error("Unexpected response from ChatGPT API: Status {}, Body {}", response.getStatusCode(), response.getBody());
                throw new IllegalStateException("Unexpected response from ChatGPT API");
            }

            // Parsing response
            Map<String, Object> responseBody = response.getBody();
            Logger.debug("ChatGPT API response body: {}", responseBody);

            List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");

            if (choices == null || choices.isEmpty()) {
                Logger.error("No choices found in ChatGPT response");
                throw new IllegalStateException("No choices found in ChatGPT response");
            }

            Map<String, Object> firstChoice = choices.get(0);
            Map<String, Object> message = (Map<String, Object>) firstChoice.get("message");
            String content = (String) message.get("content");

            if (content == null || content.isBlank()) {
                Logger.error("ChatGPT returned an empty response");
                throw new IllegalStateException("ChatGPT returned an empty response");
            }

            Logger.info("Successfully retrieved response from ChatGPT");
            return content.trim();

        } catch (Exception e) {
            Logger.error(e, "Failed to get response from ChatGPT API: {}", e.getMessage());
            throw new RuntimeException("Failed to get response from ChatGPT API: " + e.getMessage(), e);
        }
    }
}
