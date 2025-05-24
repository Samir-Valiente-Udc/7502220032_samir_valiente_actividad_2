package main.java.models.services;

import main.java.models.entities.Usuario;
import main.java.models.repositories.UsuarioRepository;

import java.util.List;

/**
 * Clase de servicio que encapsula la lógica de negocio para la entidad {@link Usuario}.
 * Actúa como intermediario entre los controladores y el repositorio, aplicando reglas de negocio.
 */
public class UsuarioService {
    private UsuarioRepository usuarioRepository;

    /**
     * Constructor del servicio de usuario. Inicializa el repositorio de usuario.
     */
    public UsuarioService() {
        this.usuarioRepository = new UsuarioRepository();
    }

    /**
     * Crea un nuevo usuario en el sistema.
     * Realiza una validación para asegurar que el nombre de usuario no exista ya.
     *
     * @param usuario El objeto {@link Usuario} a crear.
     * @return {@code true} si el usuario fue creado exitosamente, {@code false} en caso contrario (ej. usuario ya existe).
     */
    public boolean crearUsuario(Usuario usuario) {
        // Validación de negocio: Verificar si el nombre de usuario ya está en uso.
        if (usuarioRepository.getUsuarioByUsername(usuario.getUsername()) != null) {
            System.out.println("Error en UsuarioService: El nombre de usuario '" + usuario.getUsername() + "' ya existe.");
            return false; // El usuario ya existe, no se puede crear.
        }
        // Si no existe, procede a crear el usuario en la base de datos.
        return usuarioRepository.createUsuario(usuario);
    }

    /**
     * Obtiene un usuario por su nombre de usuario.
     *
     * @param username El nombre de usuario del usuario a buscar.
     * @return El objeto {@link Usuario} encontrado, o {@code null} si no existe.
     */
    public Usuario obtenerUsuarioPorUsername(String username) {
        return usuarioRepository.getUsuarioByUsername(username);
    }

    /**
     * Obtiene una lista de todos los usuarios registrados en el sistema.
     *
     * @return Una {@link List} de objetos {@link Usuario}. Retorna una lista vacía si no hay usuarios.
     */
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.getAllUsuarios();
    }

    /**
     * Actualiza la información de un usuario existente.
     *
     * @param usuario El objeto {@link Usuario} con la información actualizada.
     * @return {@code true} si el usuario fue actualizado exitosamente, {@code false} en caso contrario.
     */
    public boolean actualizarUsuario(Usuario usuario) {
        // Aquí se podrían añadir más validaciones de negocio antes de actualizar,
        // por ejemplo, verificar que el email no sea duplicado si se permitiera cambiar el username.
        return usuarioRepository.updateUsuario(usuario);
    }

    /**
     * Elimina un usuario del sistema por su nombre de usuario.
     *
     * @param username El nombre de usuario del usuario a eliminar.
     * @return {@code true} si el usuario fue eliminado exitosamente, {@code false} en caso contrario.
     */
    public boolean eliminarUsuario(String username) {
        return usuarioRepository.deleteUsuario(username);
    }

    /**
     * Valida las credenciales de un usuario para el inicio de sesión.
     *
     * @param username El nombre de usuario proporcionado.
     * @param password La contraseña proporcionada.
     * @return El objeto {@link Usuario} si las credenciales son válidas, o {@code null} si no lo son.
     */
    public Usuario validarCredenciales(String username, String password) {
        Usuario usuario = usuarioRepository.getUsuarioByUsername(username);
        // En un sistema real, se compararía el hash de la contraseña almacenada con el hash de la contraseña proporcionada.
        // Aquí, por simplicidad, se compara directamente la cadena de texto.
        if (usuario != null && usuario.getPassword().equals(password)) {
            return usuario;
        }
        return null; // Credenciales inválidas
    }
}
