package com.ejemplo.franquicias.repository;

import com.ejemplo.franquicias.model.Franquicia;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Solo con extender MongoRepository, Spring nos da gratis los metodos:
 * save(), findById(), findAll(), deleteById(), etc. No hay que escribir queries.
 */
public interface FranquiciaRepository extends MongoRepository<Franquicia, String> {
}
