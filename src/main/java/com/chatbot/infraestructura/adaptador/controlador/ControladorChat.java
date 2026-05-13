package com.chatbot.infraestructura.adaptador.controlador;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatbot.infraestructura.adaptador.controlador.dto.PreguntaRequest;
import com.chatbot.dominio.modelo.PreguntaChat;
import com.chatbot.dominio.modelo.RespuestaChat;
import com.chatbot.aplicacion.servicio.ServicioChat;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ControladorChat {

    private final ServicioChat servicioChat;

    @PostMapping("/preguntar")
    public Mono<RespuestaChat> preguntar(@RequestBody PreguntaRequest request) {
        return servicioChat.preguntar(new PreguntaChat(request.pregunta()));
    }
}