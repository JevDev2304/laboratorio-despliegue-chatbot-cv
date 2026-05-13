package com.chatbot.aplicacion.servicio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.chatbot.dominio.modelo.PreguntaChat;
import com.chatbot.dominio.modelo.PromptConfig;
import com.chatbot.dominio.modelo.RespuestaChat;
import com.chatbot.dominio.puerto.entrada.IServicioChat;
import com.chatbot.dominio.puerto.salida.IRepositorioVectorial;

import lombok.RequiredArgsConstructor;

import com.chatbot.dominio.puerto.salida.IClienteLlm;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ServicioChat implements IServicioChat {
    private static final Logger log = LoggerFactory.getLogger(ServicioChat.class);
    private final IClienteLlm clienteLlm;
    private final IRepositorioVectorial  repositorioVectorial;
    @Override
    public Mono<RespuestaChat> preguntar(PreguntaChat pregunta){
        return repositorioVectorial.buscarContexto(pregunta.texto())
        .flatMap(fragmentos -> {
            String contexto = String.join("\n---\n", fragmentos);
            String prompt = PromptConfig.construirPrompt(contexto, pregunta.texto());
            log.info("PROMPT ENVIADO AL LLM: {}", prompt);
            return clienteLlm.chatearLlm(prompt);
        })
        .map(RespuestaChat::new);
    }

}