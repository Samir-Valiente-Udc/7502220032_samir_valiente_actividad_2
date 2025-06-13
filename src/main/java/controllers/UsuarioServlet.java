package main.java.controllers;

import main.java.models.entities.Usuario;
import main.java.models.services.UsuarioService;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Controlador para manejar las operaciones relacionadas con los usuarios.
 * Permite crear, editar, eliminar y listar usuarios, así como autenticación.
 * También maneja la sesión del usuario y verifica la autenticación para las operaciones protegidas.
 *
 * Rutas principales:
 * - /usuarios - Listado de usuarios (requiere autenticación)
 * - /usuarios/new - Formulario de creación (requiere autenticación)
 * - /usuarios/edit - Formulario de edición (requiere autenticación)
 * - /usuarios/delete - Eliminar usuario (requiere autenticación)
 * - /usuarios/login - Formulario de login
 * - /usuarios/logout - Cerrar sesión
 * - /usuarios/authenticate - Procesar autenticación (POST)
 */
@WebServlet(urlPatterns = {"/usuarios", "/usuarios/*"})
public class UsuarioServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UsuarioService usuarioService; // Servicio para manejar la lógica de negocio de usuarios

    /**
     * Constructor que inicializa el servicio de usuarios
     */
    public UsuarioServlet() {
        super();
        this.usuarioService = new UsuarioService();
    }

    /**
     * Verifica si el usuario está autenticado, excepto para rutas de login y authenticate
     * @param request Objeto HttpServletRequest con la solicitud del cliente
     * @param response Objeto HttpServletResponse para la respuesta al cliente
     * @return boolean true si está autenticado o es ruta pública, false si no está autenticado
     * @throws IOException Si ocurre un error de entrada/salida
     * @throws ServletException Si ocurre un error en el servlet
     */
    private boolean checkAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Excluir las rutas de login y authenticate de la verificación
        String path = request.getPathInfo();
        if (path != null && (path.equals("/login") || path.equals("/authenticate"))) {
            return true;
        }

        // Obtener la sesión actual (sin crear una nueva si no existe)
        HttpSession session = request.getSession(false);
        // Obtener usuario de la sesión si existe
        Usuario usuarioLogueado = (session != null) ? (Usuario) session.getAttribute("usuarioLogueado") : null;

        // Si no hay usuario logueado, redirigir al login
        if (usuarioLogueado == null) {
            response.sendRedirect(request.getContextPath() + "/usuarios/login");
            return false;
        }
        return true;
    }

    /**
     * Maneja las solicitudes GET para diferentes acciones
     * @param request Objeto HttpServletRequest con la solicitud del cliente
     * @param response Objeto HttpServletResponse para la respuesta al cliente
     * @throws ServletException Si ocurre un error en el servlet
     * @throws IOException Si ocurre un error de entrada/salida
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();

        // Solo verificar autenticación para rutas que no sean login o logout
        if (action == null || (!action.equals("/login") && !action.equals("/logout"))) {
            if (!checkAuthentication(request, response)) {
                return;
            }
        }

        // Determinar la acción a realizar basada en la ruta
        if (action == null) {
            listarUsuarios(request, response);
        } else {
            switch (action) {
                case "/new":
                    mostrarFormularioCrear(request, response);
                    break;
                case "/edit":
                    mostrarFormularioEditar(request, response);
                    break;
                case "/delete":
                    eliminarUsuario(request, response);
                    break;
                case "/login":
                    mostrarFormularioLogin(request, response);
                    break;
                case "/logout":
                    cerrarSesion(request, response);
                    break;
                default:
                    listarUsuarios(request, response);
                    break;
            }
        }
    }

    /**
     * Maneja las solicitudes POST para diferentes acciones
     * @param request Objeto HttpServletRequest con la solicitud del cliente
     * @param response Objeto HttpServletResponse para la respuesta al cliente
     * @throws ServletException Si ocurre un error en el servlet
     * @throws IOException Si ocurre un error de entrada/salida
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();

        // Solo verificar autenticación para rutas que no sean authenticate
        if (action == null || !action.equals("/authenticate")) {
            if (!checkAuthentication(request, response)) {
                return;
            }
        }

        // Determinar la acción a realizar basada en la ruta y parámetros
        if (action == null) {
            if (request.getParameter("actionType") != null && request.getParameter("actionType").equals("update")) {
                actualizarUsuario(request, response);
            } else {
                crearUsuario(request, response);
            }
        } else {
            switch (action) {
                case "/create":
                    crearUsuario(request, response);
                    break;
                case "/update":
                    actualizarUsuario(request, response);
                    break;
                case "/authenticate":
                    autenticarUsuario(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Acción POST no encontrada.");
                    break;
            }
        }
    }

    /**
     * Lista todos los usuarios en el sistema
     * @param request Objeto HttpServletRequest con la solicitud del cliente
     * @param response Objeto HttpServletResponse para la respuesta al cliente
     * @throws ServletException Si ocurre un error en el servlet
     * @throws IOException Si ocurre un error de entrada/salida
     */
    private void listarUsuarios(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Usuario> listaUsuarios = usuarioService.obtenerTodosLosUsuarios();
        request.setAttribute("listaUsuarios", listaUsuarios);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/forms/usuarios/listarUsuarios.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Muestra el formulario para crear un nuevo usuario
     * @param request Objeto HttpServletRequest con la solicitud del cliente
     * @param response Objeto HttpServletResponse para la respuesta al cliente
     * @throws ServletException Si ocurre un error en el servlet
     * @throws IOException Si ocurre un error de entrada/salida
     */
    private void mostrarFormularioCrear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/forms/usuarios/crearUsuario.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Muestra el formulario para editar un usuario existente
     * @param request Objeto HttpServletRequest con la solicitud del cliente
     * @param response Objeto HttpServletResponse para la respuesta al cliente
     * @throws ServletException Si ocurre un error en el servlet
     * @throws IOException Si ocurre un error de entrada/salida
     */
    private void mostrarFormularioEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username"); // Obtener username del parámetro
        Usuario usuarioExistente = usuarioService.obtenerUsuarioPorUsername(username);

        if (usuarioExistente != null) {
            request.setAttribute("usuario", usuarioExistente);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/forms/usuarios/editarUsuario.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/usuarios?status=error&message=UsuarioNoEncontrado");
        }
    }

    /**
     * Crea un nuevo usuario con los datos del formulario
     * @param request Objeto HttpServletRequest con la solicitud del cliente
     * @param response Objeto HttpServletResponse para la respuesta al cliente
     * @throws ServletException Si ocurre un error en el servlet
     * @throws IOException Si ocurre un error de entrada/salida
     */
    private void crearUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener parámetros del formulario
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");

        // Crear nuevo objeto Usuario
        Usuario nuevoUsuario = new Usuario(username, password, nombre, email);
        // Intentar crear el usuario en el sistema
        boolean creado = usuarioService.crearUsuario(nuevoUsuario);

        if (creado) {
            // Redirigir con mensaje de éxito
            response.sendRedirect(request.getContextPath() + "/usuarios?status=success&message=UsuarioCreado");
        } else {
            // Mostrar error y volver al formulario con los datos ingresados
            request.setAttribute("error", "Error al crear usuario. El nombre de usuario ya podría existir o hubo un problema.");
            request.setAttribute("usuario", nuevoUsuario);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/forms/usuarios/crearUsuario.jsp");
            dispatcher.forward(request, response);
        }
    }

    /**
     * Actualiza un usuario existente con los datos del formulario
     * @param request Objeto HttpServletRequest con la solicitud del cliente
     * @param response Objeto HttpServletResponse para la respuesta al cliente
     * @throws ServletException Si ocurre un error en el servlet
     * @throws IOException Si ocurre un error de entrada/salida
     */
    private void actualizarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener parámetros del formulario
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");

        // Crear objeto Usuario con los datos actualizados
        Usuario usuarioAActualizar = new Usuario(username, password, nombre, email);
        // Intentar actualizar el usuario en el sistema
        boolean actualizado = usuarioService.actualizarUsuario(usuarioAActualizar);

        if (actualizado) {
            // Redirigir con mensaje de éxito
            response.sendRedirect(request.getContextPath() + "/usuarios?status=success&message=UsuarioActualizado");
        } else {
            // Mostrar error y volver al formulario con los datos ingresados
            request.setAttribute("error", "Error al actualizar usuario.");
            request.setAttribute("usuario", usuarioAActualizar);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/forms/usuarios/editarUsuario.jsp");
            dispatcher.forward(request, response);
        }
    }

    /**
     * Elimina un usuario del sistema
     * @param request Objeto HttpServletRequest con la solicitud del cliente
     * @param response Objeto HttpServletResponse para la respuesta al cliente
     * @throws ServletException Si ocurre un error en el servlet
     * @throws IOException Si ocurre un error de entrada/salida
     */
    private void eliminarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        // Intentar eliminar el usuario del sistema
        boolean eliminado = usuarioService.eliminarUsuario(username);

        if (eliminado) {
            // Redirigir con mensaje de éxito
            response.sendRedirect(request.getContextPath() + "/usuarios?status=success&message=UsuarioEliminado");
        } else {
            // Redirigir con mensaje de error
            response.sendRedirect(request.getContextPath() + "/usuarios?status=error&message=ErrorEliminarUsuario");
        }
    }

    /**
     * Muestra el formulario de login
     * @param request Objeto HttpServletRequest con la solicitud del cliente
     * @param response Objeto HttpServletResponse para la respuesta al cliente
     * @throws ServletException Si ocurre un error en el servlet
     * @throws IOException Si ocurre un error de entrada/salida
     */
    private void mostrarFormularioLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/forms/usuarios/login.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Autentica a un usuario con las credenciales proporcionadas
     * @param request Objeto HttpServletRequest con la solicitud del cliente
     * @param response Objeto HttpServletResponse para la respuesta al cliente
     * @throws ServletException Si ocurre un error en el servlet
     * @throws IOException Si ocurre un error de entrada/salida
     */
    private void autenticarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener credenciales del formulario
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Validar credenciales con el servicio
        Usuario usuarioAutenticado = usuarioService.validarCredenciales(username, password);

        if (usuarioAutenticado != null) {
            // Crear sesión y almacenar usuario autenticado
            HttpSession session = request.getSession();
            session.setAttribute("usuarioLogueado", usuarioAutenticado);
            // Redirigir al listado de usuarios
            response.sendRedirect(request.getContextPath() + "/usuarios");
        } else {
            // Mostrar error de credenciales inválidas
            request.setAttribute("error", "Credenciales inválidas. Por favor, inténtelo de nuevo.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/forms/usuarios/login.jsp");
            dispatcher.forward(request, response);
        }
    }

    /**
     * Cierra la sesión del usuario actual
     * @param request Objeto HttpServletRequest con la solicitud del cliente
     * @param response Objeto HttpServletResponse para la respuesta al cliente
     * @throws ServletException Si ocurre un error en el servlet
     * @throws IOException Si ocurre un error de entrada/salida
     */
    private void cerrarSesion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener sesión actual (sin crear una nueva si no existe)
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // Invalidar la sesión
        }
        // Redirigir al login con mensaje de éxito
        response.sendRedirect(request.getContextPath() + "/usuarios/login?status=success&message=SesionCerrada");
    }
}