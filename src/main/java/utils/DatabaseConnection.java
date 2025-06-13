package main.java.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase de utilidad para gestionar la conexión a la base de datos MySQL.
 * Proporciona métodos estáticos para obtener y cerrar conexiones.
 */
public class DatabaseConnection {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/sgc_db?useSSL=false&serverTimezone=UTC";

    // Nombre de usuario de la base de datos.
    private static final String JDBC_USER = "samir_valiente";

    // Contraseña del usuario de la base de datos.
    private static final String JDBC_PASSWORD = "AbcdeUdeC";

    /**
     * Obtiene una nueva conexión a la base de datos.
     *
     * @return Un objeto {@link Connection} si la conexión es exitosa, o {@code null} en caso de error.
     */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Cargar el driver JDBC de MySQL.
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establecer la conexión utilizando la URL, usuario y contraseña definidos.
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            System.out.println("Conexión a la base de datos establecida correctamente.");
        } catch (ClassNotFoundException e) {
            // Se lanza si el driver JDBC no se encuentra en el classpath.
            System.err.println("Error: Driver JDBC de MySQL no encontrado. Asegúrate de que el JAR esté en WEB-INF/lib.");
            e.printStackTrace();
        } catch (SQLException e) {
            // Se lanza si hay un error al intentar conectar a la base de datos.
            System.err.println("Error al conectar a la base de datos. Verifica la URL, usuario, contraseña y que MySQL esté corriendo.");
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Cierra una conexión a la base de datos de forma segura.
     *
     * @param connection El objeto {@link Connection} a cerrar. Si es {@code null}, no hace nada.
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Conexión a la base de datos cerrada.");
            } catch (SQLException e) {
                // Se lanza si hay un error al intentar cerrar la conexión.
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
