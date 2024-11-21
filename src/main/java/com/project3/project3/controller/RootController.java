package com.project3.project3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.LinkedHashMap;
import java.util.List;

@Controller
public class RootController {

    private final RequestMappingHandlerMapping handlerMapping;

    private static final String BASE_URL = "https://cst438project3-6ec60cdacb89.herokuapp.com";

    @Autowired
    public RootController(RequestMappingHandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    @GetMapping("/")
    public ResponseEntity<String> listEndpoints() {
        // Group endpoints by controller name
        Map<String, List<EndpointInfo>> endpointsGroupedByController = handlerMapping.getHandlerMethods().entrySet().stream()
                .collect(Collectors.groupingBy(
                        entry -> entry.getValue().getMethod().getDeclaringClass().getSimpleName(),
                        LinkedHashMap::new,
                        Collectors.mapping(
                                entry -> new EndpointInfo(
                                        entry.getKey().toString(),
                                        BASE_URL + entry.getKey().toString()
                                ),
                                Collectors.toList()
                        )
                ));

        String htmlResponse = buildHtmlResponse(endpointsGroupedByController);
        return ResponseEntity.ok().header("Content-Type", "text/html").body(htmlResponse);
    }

    private String buildHtmlResponse(Map<String, List<EndpointInfo>> endpointsGroupedByController) {
        StringBuilder html = new StringBuilder();

        // Inline CSS styling
        html.append("<!DOCTYPE html>");
        html.append("<html lang='en'>");
        html.append("<head>");
        html.append("<meta charset='UTF-8'>");
        html.append("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        html.append("<title>API Endpoints</title>");
        html.append("<style>");
        html.append("body { font-family: Arial, sans-serif; margin: 20px; }");
        html.append("h1 { color: #333; }");
        html.append(".controller { margin-top: 20px; }");
        html.append(".endpoint-table { width: 100%; border-collapse: collapse; }");
        html.append(".endpoint-table th, .endpoint-table td { padding: 10px; text-align: left; border: 1px solid #ddd; }");
        html.append(".endpoint-table th { background-color: #f4f4f4; }");
        html.append("</style>");
        html.append("</head>");
        html.append("<body>");
        html.append("<h1>API Endpoints</h1>");

        // Loop over each controller and its endpoints
        for (Map.Entry<String, List<EndpointInfo>> entry : endpointsGroupedByController.entrySet()) {
            String controllerName = entry.getKey();
            List<EndpointInfo> endpoints = entry.getValue();

            html.append("<div class='controller'>");
            html.append("<h2>").append(controllerName).append("</h2>");
            html.append("<table class='endpoint-table'>");
            html.append("<tr><th>Path</th><th>Full URL</th></tr>");

            for (EndpointInfo endpoint : endpoints) {
                html.append("<tr>");
                html.append("<td>").append(endpoint.relativePath).append("</td>");
                html.append("<td><a href='").append(endpoint.fullUrl).append("' target='_blank'>").append(endpoint.fullUrl).append("</a></td>");
                html.append("</tr>");
            }

            html.append("</table>");
            html.append("</div>");
        }

        html.append("</body>");
        html.append("</html>");

        return html.toString();
    }

    // Helper class to store endpoint info
    private static class EndpointInfo {
        private final String relativePath;
        private final String fullUrl;

        public EndpointInfo(String relativePath, String fullUrl) {
            this.relativePath = relativePath;
            this.fullUrl = fullUrl;
        }
    }
}
