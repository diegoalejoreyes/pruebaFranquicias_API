package com.ejemplo.franquicias;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada de la aplicacion.
 * @SpringBootApplication le dice a Spring que escanee este paquete (y sus subpaquetes)
 * buscando controladores, servicios, repositorios, etc. y los configure automaticamente.
 */
@SpringBootApplication
public class FranquiciasApplication {

    public static void main(String[] args) {
        SpringApplication.run(FranquiciasApplication.class, args);
    }
}
