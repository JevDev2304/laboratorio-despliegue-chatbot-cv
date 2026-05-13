package com.chatbot.infraestructura.adaptador.controlador;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ControladorPrueba {

    @GetMapping("/hola")
    public String hola() {
        return "¡Funciona!";
    }
}
