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

@WebServlet(urlPatterns = {"/usuarios", "/usuarios/*"})
public class UsuarioServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UsuarioService usuarioService;

    public UsuarioServlet() {
        super();
        this.usuarioService = new UsuarioService();
    }

    private boolean checkAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Excluir las rutas de login y authenticate de la verificación
        String path = request.getPathInfo();
        if (path != null && (path.equals("/login") || path.equals("/authenticate"))) {
            return true;
        }

        HttpSession session = request.getSession(false);
        Usuario usuarioLogueado = (session != null) ? (Usuario) session.getAttribute("usuarioLogueado") : null;

        if (usuarioLogueado == null) {
            response.sendRedirect(request.getContextPath() + "/usuarios/login");
            return false;
        }
        return true;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();

        // Solo verificar autenticación para rutas que no sean login o logout
        if (action == null || (!action.equals("/login") && !action.equals("/logout"))) {
            if (!checkAuthentication(request, response)) {
                return;
            }
        }

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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();

        // Solo verificar autenticación para rutas que no sean authenticate
        if (action == null || !action.equals("/authenticate")) {
            if (!checkAuthentication(request, response)) {
                return;
            }
        }

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

    // Resto de los métodos permanecen igual...
    private void listarUsuarios(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Usuario> listaUsuarios = usuarioService.obtenerTodosLosUsuarios();
        request.setAttribute("listaUsuarios", listaUsuarios);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/forms/usuarios/listarUsuarios.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioCrear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/forms/usuarios/crearUsuario.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        Usuario usuarioExistente = usuarioService.obtenerUsuarioPorUsername(username);

        if (usuarioExistente != null) {
            request.setAttribute("usuario", usuarioExistente);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/forms/usuarios/editarUsuario.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/usuarios?status=error&message=UsuarioNoEncontrado");
        }
    }

    private void crearUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");

        Usuario nuevoUsuario = new Usuario(username, password, nombre, email);
        boolean creado = usuarioService.crearUsuario(nuevoUsuario);

        if (creado) {
            response.sendRedirect(request.getContextPath() + "/usuarios?status=success&message=UsuarioCreado");
        } else {
            request.setAttribute("error", "Error al crear usuario. El nombre de usuario ya podría existir o hubo un problema.");
            request.setAttribute("usuario", nuevoUsuario);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/forms/usuarios/crearUsuario.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void actualizarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");

        Usuario usuarioAActualizar = new Usuario(username, password, nombre, email);
        boolean actualizado = usuarioService.actualizarUsuario(usuarioAActualizar);

        if (actualizado) {
            response.sendRedirect(request.getContextPath() + "/usuarios?status=success&message=UsuarioActualizado");
        } else {
            request.setAttribute("error", "Error al actualizar usuario.");
            request.setAttribute("usuario", usuarioAActualizar);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/forms/usuarios/editarUsuario.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void eliminarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        boolean eliminado = usuarioService.eliminarUsuario(username);

        if (eliminado) {
            response.sendRedirect(request.getContextPath() + "/usuarios?status=success&message=UsuarioEliminado");
        } else {
            response.sendRedirect(request.getContextPath() + "/usuarios?status=error&message=ErrorEliminarUsuario");
        }
    }

    private void mostrarFormularioLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/forms/usuarios/login.jsp");
        dispatcher.forward(request, response);
    }

    private void autenticarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Usuario usuarioAutenticado = usuarioService.validarCredenciales(username, password);

        if (usuarioAutenticado != null) {
            HttpSession session = request.getSession();
            session.setAttribute("usuarioLogueado", usuarioAutenticado);
            response.sendRedirect(request.getContextPath() + "/usuarios");
        } else {
            request.setAttribute("error", "Credenciales inválidas. Por favor, inténtelo de nuevo.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/forms/usuarios/login.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void cerrarSesion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect(request.getContextPath() + "/usuarios/login?status=success&message=SesionCerrada");
    }
}