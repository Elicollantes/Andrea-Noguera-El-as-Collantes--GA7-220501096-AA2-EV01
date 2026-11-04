package co.sumapets.inventario.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Provee conexiones JDBC a la base de datos local de SumaPets. */
public final class ConexionBD {

    private static final String URL = System.getenv().getOrDefault(
            "SUMAPETS_DB_URL",
            "jdbc:mysql://localhost:3306/sumapets?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8");
    private static final String USUARIO = System.getenv().getOrDefault("SUMAPETS_DB_USER", "root");
    private static final String CLAVE = System.getenv().getOrDefault("SUMAPETS_DB_PASSWORD", "");

    private ConexionBD() {
        // Evita crear objetos de una clase que solo ofrece un método estático.
    }

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CLAVE);
    }
}
