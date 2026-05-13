package com.chatbot.infraestructura.configuracion;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ConfiguracionWebClients {


@Value("${gemini.api.url}")
private String geminiUrl;

@Value("${gemini.api.key}")
private String geminiApiKey;

@Value("${pinecone.url}")
private String pineconeUrl;

@Value("${pinecone.api.key}")
private String pineconeApiKey;



@Bean
public WebClient webClientPinecone() {
    return WebClient.builder()
            .baseUrl(pineconeUrl)
            .defaultHeader("Api-Key", pineconeApiKey)
            .defaultHeader("Content-Type", "application/json")
            .build();
}

@Bean
public WebClient webClientGemini() {
    return WebClient.builder()
            .baseUrl(geminiUrl + "?key=" + geminiApiKey)
            .defaultHeader("Content-Type", "application/json")
            .build();
}

}