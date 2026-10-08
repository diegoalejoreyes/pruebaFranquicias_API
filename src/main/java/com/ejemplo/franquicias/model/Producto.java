package com.ejemplo.franquicias.model;

import java.util.UUID;

/**
 * Un producto vive dentro de una sucursal. Tiene nombre y cantidad de stock.
 */
public class Producto {

    private String id;
    private String nombre;
    private int stock;

    public Producto() {
    }

    public Producto(String nombre, int stock) {
        this.id = UUID.randomUUID().toString();
        this.nombre = nombre;
        this.stock = stock;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}
