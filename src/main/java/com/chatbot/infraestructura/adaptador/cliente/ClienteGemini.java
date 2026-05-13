package com.chatbot.infraestructura.adaptador.cliente;

import java.util.Map;
import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.chatbot.dominio.modelo.PromptConfig;
import com.chatbot.dominio.puerto.salida.IClienteLlm;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Primary
@Component
@RequiredArgsConstructor
public class ClienteGemini implements IClienteLlm {
    private final WebClient webClientGemini;

    @Override
    public Mono<String> chatearLlm(String prompt) {
        Map<String, Object> body = Map.of(
                "systemInstruction", Map.of("parts", List.of(Map.of("text", PromptConfig.SISTEMA))),
                "contents", List.of(Map.of("parts", List.of(Map.of("text", prompt))))
        );

        return webClientGemini.post()
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Map.class)
                .map(respuesta -> extraerRespuesta(respuesta));
    }

    private String extraerRespuesta(Map<?, ?> respuesta) {
        var candidates = (List<Map<String, Object>>) respuesta.get("candidates");
        var content = (Map<String, Object>) candidates.get(0).get("content");
        var parts = (List<Map<String, Object>>) content.get("parts");
        var text = (Map<String, Object>) parts.get(0);
        return (String) text.get("text");
    }
}
