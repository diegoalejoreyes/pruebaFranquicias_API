package com.ejemplo.franquicias.dto;

/**
 * Respuesta del endpoint "producto con mas stock por sucursal".
 * Indica el producto y a que sucursal pertenece.
 */
public record ProductoMayorStockResponse(
        String sucursalId,
        String sucursalNombre,
        String productoId,
        String productoNombre,
        int stock
) {
}
