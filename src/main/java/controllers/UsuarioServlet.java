package main.java.controllers;

import main.java.models.entities.Usuario;
import main.java.models.services.UsuarioService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Servlet principal para la gestión de Usuarios.
 * Mapea las URL /usuarios y /usuarios/* para manejar las operaciones CRUD y autenticación.
 */
@WebServlet(urlPatterns = {"/usuarios", "/usuarios/*"})
public class UsuarioServlet extends HttpServlet {
    private static final long serialVersionUID = 1L; // Identificador de versión para serialización
    private UsuarioService usuarioService; // Instancia del servicio de usuario

    /**
     * Constructor del servlet. Inicializa el servicio de usuario.
     */
    public UsuarioServlet() {
        super();
        this.usuarioService = new UsuarioService();
    }

    /**
     * Maneja las peticiones HTTP GET.
     * Dependiendo de la ruta de la URL, realiza diferentes acciones:
     * - /usuarios: Lista todos los usuarios.
     * - /usuarios/new: Muestra el formulario para crear un nuevo usuario.
     * - /usuarios/edit?username=xxx: Muestra el formulario para editar un usuario existente.
     * - /usuarios/delete?username=xxx: Elimina un usuario.
     * - /usuarios/login: Muestra el formulario de inicio de sesión.
     * - /usuarios/logout: Cierra la sesión del usuario.
     *
     * @param request  Objeto HttpServletRequest que contiene la petición del cliente.
     * @param response Objeto HttpServletResponse que contiene la respuesta del servlet.
     * @throws ServletException Si ocurre un error específico del servlet.
     * @throws IOException      Si ocurre un error de E/S.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo(); // Obtiene la parte de la URL después de /usuarios

        if (action == null) { // Si la URL es solo /usuarios
            listarUsuarios(request, response);
        } else {
            switch (action) {
                case "/new": // /usuarios/new
                    mostrarFormularioCrear(request, response);
                    break;
                case "/edit": // /usuarios/edit?username=xxx
                    mostrarFormularioEditar(request, response);
                    break;
                case "/delete": // /usuarios/delete?username=xxx
                    eliminarUsuario(request, response);
                    break;
                case "/login": // /usuarios/login
                    mostrarFormularioLogin(request, response);
                    break;
                case "/logout": // /usuarios/logout
                    cerrarSesion(request, response);
                    break;
                default: // Cualquier otra sub-ruta no reconocida
                    listarUsuarios(request, response); // Por defecto, redirige a la lista
                    break;
            }
        }
    }

    /**
     * Maneja las peticiones HTTP POST.
     * Dependiendo de la ruta de la URL o de un parámetro oculto, realiza diferentes acciones:
     * - /usuarios/create: Crea un nuevo usuario.
     * - /usuarios/update: Actualiza un usuario existente.
     * - /usuarios/authenticate: Procesa el inicio de sesión.
     *
     * @param request  Objeto HttpServletRequest que contiene la petición del cliente.
     * @param response Objeto HttpServletResponse que contiene la respuesta del servlet.
     * @throws ServletException Si ocurre un error específico del servlet.
     * @throws IOException      Si ocurre un error de E/S.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo(); // Obtiene la parte de la URL después de /usuarios

        if (action == null) { // Si la URL es solo /usuarios (manejo de POST sin sub-ruta explícita)
            // Esto es una simplificación; en un caso real se distinguiría mejor si es creación o actualización.
            // Para este ejemplo, si el formulario envía un 'actionType' de 'update', se asume actualización.
            if (request.getParameter("actionType") != null && request.getParameter("actionType").equals("update")) {
                actualizarUsuario(request, response);
            } else {
                crearUsuario(request, response);
            }
        } else {
            switch (action) {
                case "/create": // /usuarios/create
                    crearUsuario(request, response);
                    break;
                case "/update": // /usuarios/update
                    actualizarUsuario(request, response);
                    break;
                case "/authenticate": // /usuarios/authenticate
                    autenticarUsuario(request, response);
                    break;
                default: // Cualquier otra sub-ruta no reconocida para POST
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Acción POST no encontrada.");
                    break;
            }
        }
    }

    /**
     * Recupera todos los usuarios y los envía a la JSP de listado.
     *
     * @param request  Petición HTTP.
     * @param response Respuesta HTTP.
     * @throws ServletException Si ocurre un error del servlet.
     * @throws IOException      Si ocurre un error de E/S.
     */
    private void listarUsuarios(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Usuario> listaUsuarios = usuarioService.obtenerTodosLosUsuarios();
        request.setAttribute("listaUsuarios", listaUsuarios); // Guarda la lista en el ámbito de la petición
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/forms/usuarios/listarUsuarios.jsp");
        dispatcher.forward(request, response); // Redirige a la JSP
    }

    /**
     * Muestra el formulario para crear un nuevo usuario.
     *
     * @param request  Petición HTTP.
     * @param response Respuesta HTTP.
     * @throws ServletException Si ocurre un error del servlet.
     * @throws IOException      Si ocurre un error de E/S.
     */
    private void mostrarFormularioCrear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/forms/usuarios/crearUsuario.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Muestra el formulario para editar un usuario existente, precargando sus datos.
     *
     * @param request  Petición HTTP.
     * @param response Respuesta HTTP.
     * @throws ServletException Si ocurre un error del servlet.
     * @throws IOException      Si ocurre un error de E/S.
     */
    private void mostrarFormularioEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username"); // Obtiene el username de los parámetros de la URL
        Usuario usuarioExistente = usuarioService.obtenerUsuarioPorUsername(username); // Busca el usuario

        if (usuarioExistente != null) {
            request.setAttribute("usuario", usuarioExistente); // Guarda el usuario en el ámbito de la petición
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/forms/usuarios/editarUsuario.jsp");
            dispatcher.forward(request, response);
        } else {
            // Si el usuario no se encuentra, redirige a la lista de usuarios con un mensaje de error.
            response.sendRedirect(request.getContextPath() + "/usuarios?status=error&message=UsuarioNoEncontrado");
        }
    }

    /**
     * Procesa la creación de un nuevo usuario a partir de los datos del formulario.
     *
     * @param request  Petición HTTP.
     * @param response Respuesta HTTP.
     * @throws ServletException Si ocurre un error del servlet.
     * @throws IOException      Si ocurre un error de E/S.
     */
    private void crearUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtiene los parámetros del formulario
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");

        Usuario nuevoUsuario = new Usuario(username, password, nombre, email); // Crea un nuevo objeto Usuario
        boolean creado = usuarioService.crearUsuario(nuevoUsuario); // Intenta crear el usuario a través del servicio

        if (creado) {
            // Si se creó exitosamente, redirige a la lista de usuarios con un mensaje de éxito.
            response.sendRedirect(request.getContextPath() + "/usuarios?status=success&message=UsuarioCreado");
        } else {
            // Si hubo un error (ej. usuario ya existe), vuelve al formulario de creación con un mensaje de error.
            request.setAttribute("error", "Error al crear usuario. El nombre de usuario ya podría existir o hubo un problema.");
            request.setAttribute("usuario", nuevoUsuario); // Para mantener los datos en el formulario
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/forms/usuarios/crearUsuario.jsp");
            dispatcher.forward(request, response);
        }
    }

    /**
     * Procesa la actualización de un usuario existente a partir de los datos del formulario.
     *
     * @param request  Petición HTTP.
     * @param response Respuesta HTTP.
     * @throws ServletException Si ocurre un error del servlet.
     * @throws IOException      Si ocurre un error de E/S.
     */
    private void actualizarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtiene los parámetros del formulario
        String username = request.getParameter("username");
        String password = request.getParameter("password"); // Se asume que se envía la contraseña actual o nueva
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");

        Usuario usuarioAActualizar = new Usuario(username, password, nombre, email); // Crea un objeto Usuario con los datos actualizados
        boolean actualizado = usuarioService.actualizarUsuario(usuarioAActualizar); // Intenta actualizar el usuario

        if (actualizado) {
            // Si se actualizó exitosamente, redirige a la lista de usuarios con un mensaje de éxito.
            response.sendRedirect(request.getContextPath() + "/usuarios?status=success&message=UsuarioActualizado");
        } else {
            // Si hubo un error, vuelve al formulario de edición con un mensaje de error.
            request.setAttribute("error", "Error al actualizar usuario.");
            request.setAttribute("usuario", usuarioAActualizar); // Para mantener los datos en el formulario
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/forms/usuarios/editarUsuario.jsp");
            dispatcher.forward(request, response);
        }
    }

    /**
     * Procesa la eliminación de un usuario.
     *
     * @param request  Petición HTTP.
     * @param response Respuesta HTTP.
     * @throws ServletException Si ocurre un error del servlet.
     * @throws IOException      Si ocurre un error de E/S.
     */
    private void eliminarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username"); // Obtiene el username del usuario a eliminar
        boolean eliminado = usuarioService.eliminarUsuario(username); // Intenta eliminar el usuario

        if (eliminado) {
            // Si se eliminó exitosamente, redirige a la lista con un mensaje de éxito.
            response.sendRedirect(request.getContextPath() + "/usuarios?status=success&message=UsuarioEliminado");
        } else {
            // Si hubo un error, redirige a la lista con un mensaje de error.
            response.sendRedirect(request.getContextPath() + "/usuarios?status=error&message=ErrorEliminarUsuario");
        }
    }

    /**
     * Muestra el formulario de inicio de sesión.
     *
     * @param request  Petición HTTP.
     * @param response Respuesta HTTP.
     * @throws ServletException Si ocurre un error del servlet.
     * @throws IOException      Si ocurre un error de E/S.
     */
    private void mostrarFormularioLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/forms/usuarios/login.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Procesa las credenciales de inicio de sesión enviadas por el formulario.
     *
     * @param request  Petición HTTP.
     * @param response Respuesta HTTP.
     * @throws ServletException Si ocurre un error del servlet.
     * @throws IOException      Si ocurre un error de E/S.
     */
    private void autenticarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Usuario usuarioAutenticado = usuarioService.validarCredenciales(username, password); // Valida las credenciales

        if (usuarioAutenticado != null) {
            // Si las credenciales son válidas, crea o recupera la sesión HTTP.
            HttpSession session = request.getSession();
            session.setAttribute("usuarioLogueado", usuarioAutenticado); // Guarda el objeto Usuario en la sesión
            // Redirige a la página de inicio o al dashboard principal.
            response.sendRedirect(request.getContextPath() + "/");
        } else {
            // Si las credenciales son inválidas, vuelve al formulario de login con un mensaje de error.
            request.setAttribute("error", "Credenciales inválidas. Por favor, inténtelo de nuevo.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/forms/usuarios/login.jsp");
            dispatcher.forward(request, response);
        }
    }

    /**
     * Invalida la sesión actual del usuario, cerrando su sesión.
     *
     * @param request  Petición HTTP.
     * @param response Respuesta HTTP.
     * @throws ServletException Si ocurre un error del servlet.
     * @throws IOException      Si ocurre un error de E/S.
     */
    private void cerrarSesion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Obtiene la sesión existente, no crea una nueva si no hay.
        if (session != null) {
            session.invalidate(); // Invalida la sesión, eliminando todos sus atributos.
        }
        // Redirige a la página de login con un mensaje de éxito.
        response.sendRedirect(request.getContextPath() + "/usuarios/login?status=success&message=SesionCerrada");
    }
}
