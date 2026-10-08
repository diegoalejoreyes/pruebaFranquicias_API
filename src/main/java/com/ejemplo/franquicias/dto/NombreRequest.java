package com.ejemplo.franquicias.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Cuerpo de la peticion cuando solo se envia un nombre.
 * Ejemplo JSON: { "nombre": "Sucursal Centro" }
 */
public record NombreRequest(
        @NotBlank(message = "El nombre es obligatorio") String nombre
) {
}
