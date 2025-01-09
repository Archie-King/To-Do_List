package com.todo.util;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Component
public class SwaggerApiDocSaver implements ApplicationListener<ApplicationReadyEvent> {
    // Auto read project port
    @Value("${server.port}")
    private String serverPort;

    @Override
//    public void run(String... args) {
    public void onApplicationEvent(ApplicationReadyEvent event) {
        // Swagger document URL
        String jsonUrl = "http://127.0.0.1:" + serverPort + "/v3/api-docs";
        String yamlUrl = "http://127.0.0.1:" + serverPort + "/v3/api-docs.yaml";

        // Save Path
        String jsonFilePath = "docs/swagger-api-docs.json";
        String yamlFilePath = "docs/swagger-api-docs.yaml";

        // Save Swagger document
        saveSwaggerDoc(jsonUrl, jsonFilePath);
        saveSwaggerDoc(yamlUrl, yamlFilePath);
    }

    private void saveSwaggerDoc(String url, String filePath) {
        try {
            // Request Swagger document content
            RestTemplate restTemplate = new RestTemplate();
            String content = restTemplate.getForObject(url, String.class);

            // Write file
            File file = new File(filePath);
            file.getParentFile().mkdirs();  // Make sure the parent directory exists
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(content);
            }
            System.out.println("Saved Swagger document to " + file.getAbsolutePath());

        } catch (IOException e) {
            System.err.println("Failed to save Swagger document to " + filePath + ": " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error fetching Swagger document from URL: " + url + ": " + e.getMessage());
        }
    }
}
