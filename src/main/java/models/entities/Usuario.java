package main.java.models.entities;

/**
 * Entidad que representa a un usuario en el Sistema de Gestión de Contratos (SGC).
 * Corresponde a la tabla 'Usuario' en la base de datos.
 */
public class Usuario {
    private String username;
    private String password;
    private String nombre;
    private String email;

    /**
     * Constructor vacío por defecto. Necesario para algunos frameworks y para la creación de objetos.
     */
    public Usuario() {
    }

    /**
     * Constructor para crear un objeto Usuario con todos sus atributos.
     *
     * @param username El nombre de usuario único.
     * @param password La contraseña del usuario.
     * @param nombre   El nombre completo del usuario.
     * @param email    El correo electrónico del usuario.
     */
    public Usuario(String username, String password, String nombre, String email) {
        this.username = username;
        this.password = password;
        this.nombre = nombre;
        this.email = email;
    }

    // --- Métodos Getters y Setters para acceder y modificar los atributos ---

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sobreescribe el método toString() para proporcionar una representación de cadena del objeto Usuario.
     * Útil para depuración.
     *
     * @return Una cadena que representa el objeto Usuario.
     */
    @Override
    public String toString() {
        return "Usuario{" +
                "username='" + username + '\'' +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
