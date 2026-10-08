package com.ejemplo.franquicias.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Una franquicia se guarda como UN documento en la coleccion "franquicias" de MongoDB.
 * Sus sucursales (y los productos de cada sucursal) van guardados DENTRO del mismo documento.
 */
@Document(collection = "franquicias")
public class Franquicia {

    @Id
    private String id; // MongoDB lo genera automaticamente

    private String nombre;

    private List<Sucursal> sucursales = new ArrayList<>();

    public Franquicia() {
    }

    public Franquicia(String nombre) {
        this.nombre = nombre;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public List<Sucursal> getSucursales() { return sucursales; }
    public void setSucursales(List<Sucursal> sucursales) { this.sucursales = sucursales; }
}
