package com.chatbot.dominio.puerto.salida;
import reactor.core.publisher.Mono;

public interface IClienteLlm {
    Mono<String> chatearLlm(String prompt);
}