package co.sumapets.inventario.app;

import co.sumapets.inventario.modelo.Producto;
import co.sumapets.inventario.repositorio.ProductoDAO;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/** Menú del proyecto del SENA de Magaly andrea y Elias Collantes SumaPets 
GA7-220501096-AA2-EV0 */
public class SumapetsProyectoSena {

    private final Scanner teclado = new Scanner(System.in);
    private final ProductoDAO productoDAO = new ProductoDAO();

    public static void main(String[] args) {
        new SumapetsProyectoSena().iniciar();
    }

    private void iniciar() {
        String opcion;
        do {
            mostrarMenu();
            opcion = leerTexto("Selecciona una opción: ");

            try {
                switch (opcion) {
                    case "1" -> listarProductos();
                    case "2" -> crearProducto();
                    case "3" -> consultarProducto();
                    case "4" -> actualizarProducto();
                    case "5" -> eliminarProducto();
                    case "0" -> System.out.println("Fin del módulo de inventario SumaPets.");
                    default -> System.out.println("Opción no válida. Escribe un número del menú.");
                }
            } catch (SQLException e) {
                System.out.println("No fue posible completar la operación en la base de datos.");
                System.out.println("Detalle para revisar la conexión o la consulta: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Revisa los datos ingresados: " + e.getMessage());
            }
        } while (!"0".equals(opcion));
    }

    private void mostrarMenu() {
        System.out.println("\n===== SUMAPETS ACTUALIZACIÓN DE INVENTARIO ADMINISTRADOR =====");
        System.out.println("1. Consulta de mercancia disponible actualmente en inventario");
        System.out.println("2. Registrar un producto nuevo en inventario");
        System.out.println("3. Consultar un producto por ID del mismo");
        System.out.println("4. Actualizar un producto o su cantidad disponible en stock");
        System.out.println("5. Eliminar un producto descontinuado del inventario");
        System.out.println("0. Salir");
    }

    private void listarProductos() throws SQLException {
        List<Producto> productos = productoDAO.listar();
        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados.");
            return;
        }
        productos.forEach(System.out::println);
    }

    private void crearProducto() throws SQLException {
        Producto producto = leerDatosProducto();
        productoDAO.crear(producto);
        System.out.println("Producto registrado con el ID " + producto.getId() + ".");
    }

    private void consultarProducto() throws SQLException {
        int id = leerEntero("ID del producto: ");
        Optional<Producto> producto = productoDAO.buscarPorId(id);
        System.out.println(producto.map(Object::toString).orElse("No existe un producto con ese ID."));
    }

    private void actualizarProducto() throws SQLException {
        int id = leerEntero("ID del producto que deseas actualizar: ");
        Optional<Producto> actual = productoDAO.buscarPorId(id);
        if (actual.isEmpty()) {
            System.out.println("No existe un producto con ese ID.");
            return;
        }

        Producto producto = leerDatosProducto();
        producto.setId(id);
        if (productoDAO.actualizar(producto)) {
            System.out.println("Producto actualizado correctamente.");
        } else {
            System.out.println("No se encontró el producto para actualizar.");
        }
    }

    private void eliminarProducto() throws SQLException {
        int id = leerEntero("ID del producto que deseas eliminar: ");
        String confirmacion = leerTexto("Confirma la eliminación escribiendo S: ");
        if (!"S".equalsIgnoreCase(confirmacion)) {
            System.out.println("Eliminación cancelada.");
            return;
        }

        if (productoDAO.eliminar(id)) {
            System.out.println("Producto eliminado correctamente.");
        } else {
            System.out.println("No existe un producto con ese ID de producto");
        }
    }

    private Producto leerDatosProducto() {
        String nombre = leerTexto("Nombre: ");
        String categoria = leerTexto("Categoría (Alimento, Higiene, Accesorios, etc.): ");
        String marca = leerTexto("Marca: ");
        BigDecimal precio = leerDecimal("Precio en pesos: ");
        int stock = leerEntero("Unidades disponibles: ");
        return new Producto(nombre, categoria, marca, precio, stock);
    }

    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return teclado.nextLine().trim();
    }

    private int leerEntero(String mensaje) {
        while (true) {
            String valor = leerTexto(mensaje);
            try {
                return Integer.parseInt(valor);
            } catch (NumberFormatException e) {
                System.out.println("Escribe un número entero,porfa ");
            }
        }
    }

    private BigDecimal leerDecimal(String mensaje) {
        while (true) {
            String valor = leerTexto(mensaje).replace(',', '.');
            try {
                return new BigDecimal(valor);
            } catch (NumberFormatException e) {
                System.out.println("El precio debe ser un numero si no es entero puedes poner un puntito");
            }
        }
    }
}

//**respositorio actualizado