package co.sumapets.inventario.repositorio;

import co.sumapets.inventario.config.ConexionBD;
import co.sumapets.inventario.modelo.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** Ejecuta las operaciones CRUD de productos mediante JDBC y consultas preparadas. */
public class ProductoDAO {

    public Producto crear(Producto producto) throws SQLException {
        String sql = "INSERT INTO productos (nombre, categoria, marca, precio, stock) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            asignarDatos(sentencia, producto);
            sentencia.executeUpdate();

            try (ResultSet claves = sentencia.getGeneratedKeys()) {
                if (claves.next()) {
                    producto.setId(claves.getInt(1));
                }
            }
        }
        return producto;
    }

    public List<Producto> listar() throws SQLException {
        String sql = "SELECT id, nombre, categoria, marca, precio, stock FROM productos ORDER BY nombre";
        List<Producto> productos = new ArrayList<>();

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql);
             ResultSet resultados = sentencia.executeQuery()) {
            while (resultados.next()) {
                productos.add(mapearProducto(resultados));
            }
        }
        return productos;
    }

    public Optional<Producto> buscarPorId(int id) throws SQLException {
        String sql = "SELECT id, nombre, categoria, marca, precio, stock FROM productos WHERE id = ?";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setInt(1, id);
            try (ResultSet resultados = sentencia.executeQuery()) {
                if (resultados.next()) {
                    return Optional.of(mapearProducto(resultados));
                }
            }
        }
        return Optional.empty();
    }

    public boolean actualizar(Producto producto) throws SQLException {
        String sql = "UPDATE productos SET nombre = ?, categoria = ?, marca = ?, precio = ?, stock = ? WHERE id = ?";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            asignarDatos(sentencia, producto);
            sentencia.setInt(6, producto.getId());
            return sentencia.executeUpdate() == 1;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM productos WHERE id = ?";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setInt(1, id);
            return sentencia.executeUpdate() == 1;
        }
    }

    private void asignarDatos(PreparedStatement sentencia, Producto producto) throws SQLException {
        sentencia.setString(1, producto.getNombre());
        sentencia.setString(2, producto.getCategoria());
        sentencia.setString(3, producto.getMarca());
        sentencia.setBigDecimal(4, producto.getPrecio());
        sentencia.setInt(5, producto.getStock());
    }

    private Producto mapearProducto(ResultSet resultados) throws SQLException {
        Producto producto = new Producto(
                resultados.getString("nombre"),
                resultados.getString("categoria"),
                resultados.getString("marca"),
                resultados.getBigDecimal("precio"),
                resultados.getInt("stock"));
        producto.setId(resultados.getInt("id"));
        return producto;
    }
}
