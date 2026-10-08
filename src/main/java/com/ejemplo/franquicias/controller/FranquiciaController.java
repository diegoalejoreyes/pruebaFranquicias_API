package com.ejemplo.franquicias.controller;

import com.ejemplo.franquicias.dto.NombreRequest;
import com.ejemplo.franquicias.dto.ProductoMayorStockResponse;
import com.ejemplo.franquicias.dto.ProductoRequest;
import com.ejemplo.franquicias.dto.StockRequest;
import com.ejemplo.franquicias.model.Franquicia;
import com.ejemplo.franquicias.service.FranquiciaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Aqui se definen los ENDPOINTS (las URLs de la API).
 * Todas empiezan por /api/franquicias.
 */
@RestController
@RequestMapping("/api/franquicias")
public class FranquiciaController {

    private final FranquiciaService service;

    public FranquiciaController(FranquiciaService service) {
        this.service = service;
    }

    // Consultar todas las franquicias
    @GetMapping
    public List<Franquicia> listar() {
        return service.listarFranquicias();
    }

    // Consultar una franquicia por id
    @GetMapping("/{franquiciaId}")
    public Franquicia obtener(@PathVariable String franquiciaId) {
        return service.obtenerFranquicia(franquiciaId);
    }

    // 2. Agregar nueva franquicia
    @PostMapping
    public ResponseEntity<Franquicia> crearFranquicia(@Valid @RequestBody NombreRequest request) {
        Franquicia creada = service.crearFranquicia(request.nombre());
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    // 3. Agregar nueva sucursal a una franquicia
    @PostMapping("/{franquiciaId}/sucursales")
    public ResponseEntity<Franquicia> agregarSucursal(@PathVariable String franquiciaId,
                                                      @Valid @RequestBody NombreRequest request) {
        Franquicia actualizada = service.agregarSucursal(franquiciaId, request.nombre());
        return ResponseEntity.status(HttpStatus.CREATED).body(actualizada);
    }

    // 4. Agregar nuevo producto a una sucursal
    @PostMapping("/{franquiciaId}/sucursales/{sucursalId}/productos")
    public ResponseEntity<Franquicia> agregarProducto(@PathVariable String franquiciaId,
                                                      @PathVariable String sucursalId,
                                                      @Valid @RequestBody ProductoRequest request) {
        Franquicia actualizada = service.agregarProducto(franquiciaId, sucursalId,
                request.nombre(), request.stock());
        return ResponseEntity.status(HttpStatus.CREATED).body(actualizada);
    }

    // 5. Eliminar un producto de una sucursal
    @DeleteMapping("/{franquiciaId}/sucursales/{sucursalId}/productos/{productoId}")
    public Franquicia eliminarProducto(@PathVariable String franquiciaId,
                                       @PathVariable String sucursalId,
                                       @PathVariable String productoId) {
        return service.eliminarProducto(franquiciaId, sucursalId, productoId);
    }

    // 6. Modificar el stock de un producto
    @PatchMapping("/{franquiciaId}/sucursales/{sucursalId}/productos/{productoId}/stock")
    public Franquicia modificarStock(@PathVariable String franquiciaId,
                                     @PathVariable String sucursalId,
                                     @PathVariable String productoId,
                                     @Valid @RequestBody StockRequest request) {
        return service.modificarStock(franquiciaId, sucursalId, productoId, request.stock());
    }

    // 7. Producto con mas stock por sucursal de una franquicia
    @GetMapping("/{franquiciaId}/productos/mayor-stock")
    public List<ProductoMayorStockResponse> productosConMasStock(@PathVariable String franquiciaId) {
        return service.productosConMasStock(franquiciaId);
    }

    // actualizar el nombre de una sucursal
    @PatchMapping("/{franquiciaId}/sucursales/{sucursalId}/nombre")
    public Franquicia actualizarNombreSucursal(@PathVariable String franquiciaId,
                                               @PathVariable String sucursalId,
                                               @Valid @RequestBody NombreRequest request) {
        return service.actualizarNombreSucursal(franquiciaId, sucursalId, request.nombre());
    }

    // actualizar el nombre de un producto
    @PatchMapping("/{franquiciaId}/sucursales/{sucursalId}/productos/{productoId}/nombre")
    public Franquicia actualizarNombreProducto(@PathVariable String franquiciaId,
                                               @PathVariable String sucursalId,
                                               @PathVariable String productoId,
                                               @Valid @RequestBody NombreRequest request) {
        return service.actualizarNombreProducto(franquiciaId, sucursalId, productoId, request.nombre());
    }
}
