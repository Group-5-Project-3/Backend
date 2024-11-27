package com.project3.project3.utility;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatGPTUtil {

    private ChatGPTUtil() {
        // Private constructor to prevent instantiation
    }

    public static String getChatGPTResponse(String prompt) {
        String apiKey = System.getenv("CHAT_GPT_API_KEY");
        String url = "https://api.openai.com/v1/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        // Request body setup
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("messages", List.of(
                Map.of("role", "system", "content", "You are a helpful assistant needed for adding descriptions for local trails."),
                Map.of("role", "user", "content", prompt)
        ));

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Map.class);

            // Validate response
            if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
                throw new IllegalStateException("Unexpected response from ChatGPT API");
            }

            // Parsing response
            Map<String, Object> responseBody = response.getBody();
            List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");

            if (choices == null || choices.isEmpty()) {
                throw new IllegalStateException("No choices found in ChatGPT response");
            }

            Map<String, Object> firstChoice = choices.get(0);
            Map<String, Object> message = (Map<String, Object>) firstChoice.get("message");
            String content = (String) message.get("content");

            if (content == null || content.isBlank()) {
                throw new IllegalStateException("ChatGPT returned an empty response");
            }

            return content.trim();

        } catch (Exception e) {
            throw new RuntimeException("Failed to get response from ChatGPT API: " + e.getMessage(), e);
        }
    }
}

