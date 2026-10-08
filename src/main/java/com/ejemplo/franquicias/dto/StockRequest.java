package com.ejemplo.franquicias.dto;

import jakarta.validation.constraints.Min;

/**
 * Cuerpo de la peticion para modificar el stock.
 * Ejemplo JSON: { "stock": 120 }
 */
public record StockRequest(
        @Min(value = 0, message = "El stock no puede ser negativo") int stock
) {
}
