package main.java.models.repositories;

import main.java.models.entities.Usuario;
import main.java.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que maneja las operaciones de acceso a datos (CRUD) para la entidad {@link Usuario}.
 * Se comunica directamente con la base de datos.
 */
public class UsuarioRepository {

    /**
     * Inserta un nuevo usuario en la base de datos.
     *
     * @param usuario El objeto {@link Usuario} a insertar.
     * @return {@code true} si la inserción fue exitosa, {@code false} en caso contrario.
     */
    public boolean createUsuario(Usuario usuario) {
        String sql = "INSERT INTO Usuario (username, password, nombre, email) VALUES (?, ?, ?, ?)";
        // Uso de try-with-resources para asegurar que Connection y PreparedStatement se cierren automáticamente
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, usuario.getUsername());
            pstmt.setString(2, usuario.getPassword()); // NOTA: En un sistema real, la contraseña debería ser hasheada.
            pstmt.setString(3, usuario.getNombre());
            pstmt.setString(4, usuario.getEmail());

            int rowsAffected = pstmt.executeUpdate(); // Ejecuta la consulta de inserción
            return rowsAffected > 0; // Retorna true si se insertó al menos una fila
        } catch (SQLException e) {
            System.err.println("Error al crear usuario: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Busca y recupera un usuario de la base de datos por su nombre de usuario.
     *
     * @param username El nombre de usuario del usuario a buscar.
     * @return El objeto {@link Usuario} si se encuentra, o {@code null} si no existe.
     */
    public Usuario getUsuarioByUsername(String username) {
        String sql = "SELECT username, password, nombre, email FROM Usuario WHERE username = ?";
        Usuario usuario = null;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) { // Ejecuta la consulta de selección
                if (rs.next()) { // Si se encuentra un resultado
                    usuario = new Usuario();
                    usuario.setUsername(rs.getString("username"));
                    usuario.setPassword(rs.getString("password"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setEmail(rs.getString("email"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener usuario por username: " + e.getMessage());
            e.printStackTrace();
        }
        return usuario;
    }

    /**
     * Recupera una lista de todos los usuarios de la base de datos.
     *
     * @return Una {@link List} de objetos {@link Usuario}. Retorna una lista vacía si no hay usuarios.
     */
    public List<Usuario> getAllUsuarios() {
        String sql = "SELECT username, password, nombre, email FROM Usuario";
        List<Usuario> usuarios = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) { // Ejecuta la consulta de selección

            while (rs.next()) { // Itera sobre cada fila del resultado
                Usuario usuario = new Usuario();
                usuario.setUsername(rs.getString("username"));
                usuario.setPassword(rs.getString("password"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setEmail(rs.getString("email"));
                usuarios.add(usuario); // Añade el usuario a la lista
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los usuarios: " + e.getMessage());
            e.printStackTrace();
        }
        return usuarios;
    }

    /**
     * Actualiza la información de un usuario existente en la base de datos.
     *
     * @param usuario El objeto {@link Usuario} con la información actualizada.
     * @return {@code true} si la actualización fue exitosa, {@code false} en caso contrario.
     */
    public boolean updateUsuario(Usuario usuario) {
        String sql = "UPDATE Usuario SET password = ?, nombre = ?, email = ? WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, usuario.getPassword()); // NOTA: En un sistema real, la contraseña debería ser hasheada.
            pstmt.setString(2, usuario.getNombre());
            pstmt.setString(3, usuario.getEmail());
            pstmt.setString(4, usuario.getUsername());

            int rowsAffected = pstmt.executeUpdate(); // Ejecuta la consulta de actualización
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina un usuario de la base de datos por su nombre de usuario.
     *
     * @param username El nombre de usuario del usuario a eliminar.
     * @return {@code true} si la eliminación fue exitosa, {@code false} en caso contrario.
     */
    public boolean deleteUsuario(String username) {
        String sql = "DELETE FROM Usuario WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);

            int rowsAffected = pstmt.executeUpdate(); // Ejecuta la consulta de eliminación
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
