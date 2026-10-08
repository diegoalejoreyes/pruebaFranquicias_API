package com.ejemplo.franquicias.exception;

/**
 * Se lanza cuando no existe la franquicia, sucursal o producto buscado.
 */
public class RecursoNoEncontradoException extends RuntimeException {

    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
