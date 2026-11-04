package co.sumapets.inventario.modelo;

import java.math.BigDecimal;

/** Representa un producto que la tienda SumaPets ofrece o mantiene en inventario. */
public class Producto {

    private int id;
    private String nombre;
    private String categoria;
    private String marca;
    private BigDecimal precio;
    private int stock;

    public Producto(String nombre, String categoria, String marca, BigDecimal precio, int stock) {
        setNombre(nombre);
        setCategoria(categoria);
        setMarca(marca);
        setPrecio(precio);
        setStock(stock);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("El identificador no puede ser negativo.");
        }
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = validarTexto(nombre, "nombre");
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = validarTexto(categoria, "categoría");
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = validarTexto(marca, "marca");
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        if (precio == null || precio.signum() < 0) {
            throw new IllegalArgumentException("El precio debe ser un valor igual o mayor que cero.");
        }
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("Las unidades disponibles no pueden ser negativas.");
        }
        this.stock = stock;
    }

    private String validarTexto(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("El campo " + campo + " es obligatorio.");
        }
        return valor.trim();
    }

    @Override
    public String toString() {
        return "ID: " + id
                + " | Producto: " + nombre
                + " | Categoría: " + categoria
                + " | Marca: " + marca
                + " | Precio: $" + precio.toPlainString()
                + " | Existencias: " + stock;
    }
}
