package com.chatbot.dominio.puerto.salida;
import java.util.List;
import reactor.core.publisher.Mono;

public interface IRepositorioVectorial {
    Mono<List<String>> buscarContexto(String pregunta);
} 