package com.ejemplo.franquicias.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

/**
 * Cuerpo de la peticion para crear un producto.
 * Ejemplo JSON: { "nombre": "Hamburguesa", "stock": 50 }
 */
public record ProductoRequest(
        @NotBlank(message = "El nombre es obligatorio") String nombre,
        @Min(value = 0, message = "El stock no puede ser negativo") int stock
) {
}
