package main.java.models.repositories;

import main.java.models.entities.Contrato;
import main.java.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; // Necesario para obtener las claves generadas
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que maneja las operaciones de acceso a datos (CRUD) para la entidad {@link Contrato}.
 * Se comunica directamente con la base de datos.
 */
public class ContratoRepository {

    /**
     * Inserta un nuevo contrato en la base de datos.
     * El ID del contrato será auto-generado por la base de datos y asignado al objeto Contrato.
     *
     * @param contrato El objeto {@link Contrato} a insertar.
     * @return {@code true} si la inserción fue exitosa, {@code false} en caso contrario.
     */
    public boolean createContrato(Contrato contrato) {
        String sql = "INSERT INTO Contrato (fecha_firma, fecha_inicio, fecha_fin, empresa, empleado, funciones, monto, frecuencia_de_pago, usuario_username) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             // PreparedStatement con Statement.RETURN_GENERATED_KEYS para obtener el ID auto-generado
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setDate(1, contrato.getFecha_firma());
            pstmt.setDate(2, contrato.getFecha_inicio());
            pstmt.setDate(3, contrato.getFecha_fin());
            pstmt.setString(4, contrato.getEmpresa());
            pstmt.setString(5, contrato.getEmpleado());
            pstmt.setString(6, contrato.getFunciones());
            pstmt.setDouble(7, contrato.getMonto());
            pstmt.setString(8, contrato.getFrecuencia_de_pago());

            int rowsAffected = pstmt.executeUpdate(); // Ejecuta la consulta de inserción

            if (rowsAffected > 0) {
                // Si la inserción fue exitosa, obtener el ID generado
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        contrato.setId(generatedKeys.getInt(1)); // Asigna el ID generado al objeto Contrato
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error al crear contrato: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Busca y recupera un contrato de la base de datos por su ID.
     *
     * @param id El ID del contrato a buscar.
     * @return El objeto {@link Contrato} si se encuentra, o {@code null} si no existe.
     */
    public Contrato getContratoById(int id) {
        String sql = "SELECT id, fecha_firma, fecha_inicio, fecha_fin, empresa, empleado, funciones, monto, frecuencia_de_pago, usuario_username FROM Contrato WHERE id = ?";
        Contrato contrato = null;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) { // Ejecuta la consulta de selección
                if (rs.next()) { // Si se encuentra un resultado
                    contrato = new Contrato();
                    contrato.setId(rs.getInt("id"));
                    contrato.setFecha_firma(rs.getDate("fecha_firma"));
                    contrato.setFecha_inicio(rs.getDate("fecha_inicio"));
                    contrato.setFecha_fin(rs.getDate("fecha_fin"));
                    contrato.setEmpresa(rs.getString("empresa"));
                    contrato.setEmpleado(rs.getString("empleado"));
                    contrato.setFunciones(rs.getString("funciones"));
                    contrato.setMonto(rs.getDouble("monto"));
                    contrato.setFrecuencia_de_pago(rs.getString("frecuencia_de_pago"));
                    contrato.setUsuarioUsername(rs.getString("usuario_username"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener contrato por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return contrato;
    }

    /**
     * Recupera una lista de todos los contratos de la base de datos.
     *
     * @return Una {@link List} de objetos {@link Contrato}. Retorna una lista vacía si no hay contratos.
     */
    public List<Contrato> getAllContratos() {
        String sql = "SELECT id, fecha_firma, fecha_inicio, fecha_fin, empresa, empleado, funciones, monto, frecuencia_de_pago, usuario_username FROM Contrato";
        List<Contrato> contratos = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) { // Ejecuta la consulta de selección

            while (rs.next()) { // Itera sobre cada fila del resultado
                Contrato contrato = new Contrato();
                contrato.setId(rs.getInt("id"));
                contrato.setFecha_firma(rs.getDate("fecha_firma"));
                contrato.setFecha_inicio(rs.getDate("fecha_inicio"));
                contrato.setFecha_fin(rs.getDate("fechaf_fin"));
                contrato.setEmpresa(rs.getString("empresa"));
                contrato.setEmpleado(rs.getString("empleado"));
                contrato.setFunciones(rs.getString("funciones"));
                contrato.setMonto(rs.getDouble("monto"));
                contrato.setFrecuencia_de_pago(rs.getString("frecuencia_de_pago"));
                contrato.setUsuarioUsername(rs.getString("usuario_username"));
                contratos.add(contrato); // Añade el contrato a la lista
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los contratos: " + e.getMessage());
            e.printStackTrace();
        }
        return contratos;
    }

    /**
     * Recupera una lista de contratos asociados a un nombre de usuario específico.
     *
     * @param username El nombre de usuario del cual se quieren obtener los contratos.
     * @return Una {@link List} de objetos {@link Contrato} asociados al usuario.
     */
    public List<Contrato> getContratosByUsuario(String username) {
        String sql = "SELECT id, fecha_firma, fecha_inicio, fecha_fin, empresa, empleado, funciones, monto, frecuencia_de_pago, usuario_username FROM Contrato WHERE usuario_username = ?";
        List<Contrato> contratos = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Contrato contrato = new Contrato();
                    contrato.setId(rs.getInt("id"));
                    contrato.setFecha_firma(rs.getDate("fecha_firma"));
                    contrato.setFecha_inicio(rs.getDate("fecha_inicio"));
                    contrato.setFecha_fin(rs.getDate("fecha_fin"));
                    contrato.setEmpresa(rs.getString("empresa"));
                    contrato.setEmpleado(rs.getString("empleado"));
                    contrato.setFunciones(rs.getString("funciones"));
                    contrato.setMonto(rs.getDouble("monto"));
                    contrato.setFrecuencia_de_pago(rs.getString("frecuencia_de_pago"));
                    contrato.setUsuarioUsername(rs.getString("usuario_username"));
                    contratos.add(contrato);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener contratos por usuario: " + e.getMessage());
            e.printStackTrace();
        }
        return contratos;
    }

    /**
     * Actualiza la información de un contrato existente en la base de datos.
     *
     * @param contrato El objeto {@link Contrato} con la información actualizada.
     * @return {@code true} si la actualización fue exitosa, {@code false} en caso contrario.
     */
    public boolean updateContrato(Contrato contrato) {
        String sql = "UPDATE Contrato SET fecha_firma = ?, fecha_inicio = ?, fecha_fin = ?, empresa = ?, empleado = ?, funciones = ?, monto = ?, frecuencia_de_pago = ?, usuario_username = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDate(1, contrato.getFecha_firma());
            pstmt.setDate(2, contrato.getFecha_inicio());
            pstmt.setDate(3, contrato.getFecha_fin());
            pstmt.setString(4, contrato.getEmpresa());
            pstmt.setString(5, contrato.getEmpleado());
            pstmt.setString(6, contrato.getFunciones());
            pstmt.setDouble(7, contrato.getMonto());
            pstmt.setString(8, contrato.getFrecuencia_de_pago());
            pstmt.setString(9, contrato.getUsuarioUsername());
            pstmt.setInt(10, contrato.getId()); // El ID es usado en la cláusula WHERE para identificar el contrato a actualizar

            int rowsAffected = pstmt.executeUpdate(); // Ejecuta la consulta de actualización
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar contrato: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina un contrato de la base de datos por su ID.
     *
     * @param id El ID del contrato a eliminar.
     * @return {@code true} si la eliminación fue exitosa, {@code false} en caso contrario.
     */
    public boolean deleteContrato(int id) {
        String sql = "DELETE FROM Contrato WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            int rowsAffected = pstmt.executeUpdate(); // Ejecuta la consulta de eliminación
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar contrato: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}

