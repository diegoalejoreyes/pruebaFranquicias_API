package com.ejemplo.franquicias.service;

import com.ejemplo.franquicias.dto.ProductoMayorStockResponse;
import com.ejemplo.franquicias.exception.RecursoNoEncontradoException;
import com.ejemplo.franquicias.model.Franquicia;
import com.ejemplo.franquicias.model.Producto;
import com.ejemplo.franquicias.model.Sucursal;
import com.ejemplo.franquicias.repository.FranquiciaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias del servicio. Usamos Mockito para "simular" el repositorio,
 * asi no necesitamos una base de datos real para correr las pruebas.
 */
class FranquiciaServiceTest {

    private FranquiciaRepository repository;
    private FranquiciaService service;

    @BeforeEach
    void setUp() {
        repository = mock(FranquiciaRepository.class);
        service = new FranquiciaService(repository);
        // save() devuelve el mismo objeto que recibe
        when(repository.save(any(Franquicia.class))).thenAnswer(inv -> inv.getArgument(0));
    }

    @Test
    void debeRetornarElProductoConMasStockDeCadaSucursal() {
        Franquicia franquicia = new Franquicia("Mi Franquicia");
        Sucursal norte = new Sucursal("Norte");
        norte.getProductos().add(new Producto("Papas", 10));
        norte.getProductos().add(new Producto("Gaseosa", 40));
        Sucursal sur = new Sucursal("Sur");
        sur.getProductos().add(new Producto("Helado", 25));
        Sucursal vacia = new Sucursal("Sin productos");
        franquicia.getSucursales().addAll(List.of(norte, sur, vacia));
        when(repository.findById("f1")).thenReturn(Optional.of(franquicia));

        List<ProductoMayorStockResponse> resultado = service.productosConMasStock("f1");

        assertEquals(2, resultado.size());
        assertEquals("Gaseosa", resultado.get(0).productoNombre());
        assertEquals("Norte", resultado.get(0).sucursalNombre());
        assertEquals("Helado", resultado.get(1).productoNombre());
    }

    @Test
    void debeModificarElStockDeUnProducto() {
        Franquicia franquicia = new Franquicia("Mi Franquicia");
        Sucursal sucursal = new Sucursal("Centro");
        Producto producto = new Producto("Pizza", 5);
        sucursal.getProductos().add(producto);
        franquicia.getSucursales().add(sucursal);
        when(repository.findById("f1")).thenReturn(Optional.of(franquicia));

        service.modificarStock("f1", sucursal.getId(), producto.getId(), 99);

        assertEquals(99, producto.getStock());
        verify(repository).save(franquicia);
    }

    @Test
    void debeActualizarElNombreDeUnProducto() {
        Franquicia franquicia = new Franquicia("Mi Franquicia");
        Sucursal sucursal = new Sucursal("Centro");
        Producto producto = new Producto("Pizza", 5);
        sucursal.getProductos().add(producto);
        franquicia.getSucursales().add(sucursal);
        when(repository.findById("f1")).thenReturn(Optional.of(franquicia));

        service.actualizarNombreProducto("f1", sucursal.getId(), producto.getId(), "Pizza Hawaiana");

        assertEquals("Pizza Hawaiana", producto.getNombre());
        verify(repository).save(franquicia);
    }

    @Test
    void debeLanzarErrorSiLaFranquiciaNoExiste() {
        when(repository.findById("no-existe")).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class,
                () -> service.agregarSucursal("no-existe", "Sucursal X"));
    }
}
