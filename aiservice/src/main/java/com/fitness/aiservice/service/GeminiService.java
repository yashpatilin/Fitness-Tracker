package com.fitness.aiservice.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class GeminiService {

	private final WebClient webClient;

	@Value("${gemini.api.url}")
	private String geminiApiUrl;

	@Value("${gemini.api.key}")
	private String geminiApiKey;

	public GeminiService(WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder.build();
	}

	public String getRecommendation(String details) {

	    Map<String, Object> requestBody = Map.of(
	            "contents", new Object[]{
	                    Map.of(
	                            "parts", new Object[]{
	                                    Map.of("text", details)
	                            }
	                    )
	            }
	    );

	    try {

	        System.out.println("Gemini URL = " + geminiApiUrl);
	        System.out.println("Gemini Key Present = " + (geminiApiKey != null));

	        String response = webClient.post()
	                .uri(geminiApiUrl)
	                .header("Content-Type", "application/json")
	                .header("x-goog-api-key", geminiApiKey)
	                .bodyValue(requestBody)
	                .retrieve()
	                .bodyToMono(String.class)
	                .block();

	        System.out.println("Gemini Response:");
	        System.out.println(response);

	        return response;

	    } catch (Exception e) {

	        System.out.println("========== GEMINI ERROR ==========");
	        e.printStackTrace();

	        return "Unable to generate detailed recommendation at this time. Please try again later.";
	    }
	}
}