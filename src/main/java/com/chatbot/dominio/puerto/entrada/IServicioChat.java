package com.chatbot.dominio.puerto.entrada;

import com.chatbot.dominio.modelo.PreguntaChat;
import com.chatbot.dominio.modelo.RespuestaChat;
import reactor.core.publisher.Mono;

public interface IServicioChat {
    Mono<RespuestaChat> preguntar(PreguntaChat pregunta);
}
