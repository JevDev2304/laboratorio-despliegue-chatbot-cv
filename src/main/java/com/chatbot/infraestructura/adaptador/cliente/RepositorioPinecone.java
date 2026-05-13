package com.chatbot.infraestructura.adaptador.cliente;
import com.chatbot.dominio.puerto.salida.IRepositorioVectorial;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class RepositorioPinecone implements IRepositorioVectorial {
    private final WebClient webClientPinecone;

    @Override
    public Mono<List<String>> buscarContexto(String pregunta) {
        Map<String, Object> body = Map.of(
                "query", Map.of(
                        "inputs", Map.of("text", pregunta),
                        "top_k", 5
                ),
                "fields", List.of("text")
        );
        return webClientPinecone.post()
                .uri("/records/namespaces/cvs/search")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Map.class)
                .map(respuesta -> {
                    var result = (Map<String, Object>) respuesta.get("result");
                    var hits = (List<Map<String, Object>>) result.get("hits");
                    List<String> textos = new ArrayList<>();
                    if (hits != null && !hits.isEmpty()) {
                        for (var hit : hits) {
                            var fields = (Map<String, Object>) hit.get("fields");
                            textos.add((String) fields.get("text"));
                        }
                    }
                    return textos;
                });
    }

    


}
