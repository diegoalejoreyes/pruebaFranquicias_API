package com.ejemplo.franquicias.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Una sucursal vive dentro de una franquicia (no tiene coleccion propia).
 * Como MongoDB no le genera id a los objetos internos, lo generamos nosotros con UUID.
 */
public class Sucursal {

    private String id;
    private String nombre;
    private List<Producto> productos = new ArrayList<>();

    public Sucursal() {
    }

    public Sucursal(String nombre) {
        this.id = UUID.randomUUID().toString();
        this.nombre = nombre;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public List<Producto> getProductos() { return productos; }
    public void setProductos(List<Producto> productos) { this.productos = productos; }
}
