package com.ejemplo.franquicias.service;

import com.ejemplo.franquicias.dto.ProductoMayorStockResponse;
import com.ejemplo.franquicias.exception.RecursoNoEncontradoException;
import com.ejemplo.franquicias.model.Franquicia;
import com.ejemplo.franquicias.model.Producto;
import com.ejemplo.franquicias.model.Sucursal;
import com.ejemplo.franquicias.repository.FranquiciaRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

/**
 * Aqui va la LOGICA DE NEGOCIO. El controlador solo recibe la peticion y llama a estos metodos.
 */
@Service
public class FranquiciaService {

    private final FranquiciaRepository repository;

    public FranquiciaService(FranquiciaRepository repository) {
        this.repository = repository;
    }

    public List<Franquicia> listarFranquicias() {
        return repository.findAll();
    }

    public Franquicia obtenerFranquicia(String franquiciaId) {
        return repository.findById(franquiciaId)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe la franquicia con id " + franquiciaId));
    }

    // agregar una nueva franquicia
    public Franquicia crearFranquicia(String nombre) {
        return repository.save(new Franquicia(nombre));
    }

    // agregar una nueva sucursal a una franquicia
    public Franquicia agregarSucursal(String franquiciaId, String nombre) {
        Franquicia franquicia = obtenerFranquicia(franquiciaId);
        franquicia.getSucursales().add(new Sucursal(nombre));
        return repository.save(franquicia);
    }

    //agregar un nuevo producto a una sucursal
    public Franquicia agregarProducto(String franquiciaId, String sucursalId, String nombre, int stock) {
        Franquicia franquicia = obtenerFranquicia(franquiciaId);
        Sucursal sucursal = buscarSucursal(franquicia, sucursalId);
        sucursal.getProductos().add(new Producto(nombre, stock));
        return repository.save(franquicia);
    }

    // eliminar un producto de una sucursal
    public Franquicia eliminarProducto(String franquiciaId, String sucursalId, String productoId) {
        Franquicia franquicia = obtenerFranquicia(franquiciaId);
        Sucursal sucursal = buscarSucursal(franquicia, sucursalId);
        boolean eliminado = sucursal.getProductos().removeIf(p -> p.getId().equals(productoId));
        if (!eliminado) {
            throw new RecursoNoEncontradoException("No existe el producto con id " + productoId);
        }
        return repository.save(franquicia);
    }

    // modificar el stock de un producto
    public Franquicia modificarStock(String franquiciaId, String sucursalId, String productoId, int nuevoStock) {
        Franquicia franquicia = obtenerFranquicia(franquiciaId);
        Sucursal sucursal = buscarSucursal(franquicia, sucursalId);
        Producto producto = buscarProducto(sucursal, productoId);
        producto.setStock(nuevoStock);
        return repository.save(franquicia);
    }

    // producto con mas stock en cada sucursal de una franquicia
    public List<ProductoMayorStockResponse> productosConMasStock(String franquiciaId) {
        Franquicia franquicia = obtenerFranquicia(franquiciaId);

        return franquicia.getSucursales().stream()
                .filter(sucursal -> !sucursal.getProductos().isEmpty()) // ignoramos sucursales sin productos
                .map(sucursal -> {
                    Producto mayor = sucursal.getProductos().stream()
                            .max(Comparator.comparingInt(Producto::getStock))
                            .get();
                    return new ProductoMayorStockResponse(
                            sucursal.getId(),
                            sucursal.getNombre(),
                            mayor.getId(),
                            mayor.getNombre(),
                            mayor.getStock());
                })
                .toList();
    }

    // actualizar el nombre de una sucursal
    public Franquicia actualizarNombreSucursal(String franquiciaId, String sucursalId, String nuevoNombre) {
        Franquicia franquicia = obtenerFranquicia(franquiciaId);
        Sucursal sucursal = buscarSucursal(franquicia, sucursalId);
        sucursal.setNombre(nuevoNombre);
        return repository.save(franquicia);
    }

    // actualizar el nombre de un producto
    public Franquicia actualizarNombreProducto(String franquiciaId, String sucursalId,
                                               String productoId, String nuevoNombre) {
        Franquicia franquicia = obtenerFranquicia(franquiciaId);
        Sucursal sucursal = buscarSucursal(franquicia, sucursalId);
        Producto producto = buscarProducto(sucursal, productoId);
        producto.setNombre(nuevoNombre);
        return repository.save(franquicia);
    }

    // ---------- metodos de ayuda ----------

    private Sucursal buscarSucursal(Franquicia franquicia, String sucursalId) {
        return franquicia.getSucursales().stream()
                .filter(s -> s.getId().equals(sucursalId))
                .findFirst()
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe la sucursal con id " + sucursalId));
    }

    private Producto buscarProducto(Sucursal sucursal, String productoId) {
        return sucursal.getProductos().stream()
                .filter(p -> p.getId().equals(productoId))
                .findFirst()
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe el producto con id " + productoId));
    }
}
