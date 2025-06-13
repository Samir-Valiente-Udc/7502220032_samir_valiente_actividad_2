package main.java.controllers;

import main.java.models.entities.Contrato;
import main.java.models.entities.Usuario;
import main.java.models.services.ContratoService;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

/**
 * Servlet principal para la gestión de Contratos.
 * Mapea las URL /contratos y /contratos/* para manejar las operaciones CRUD.
 * Incluye una verificación de autenticación básica.
 * Ademas de ser el encargado de  manejar las peticiones HTTP GET y POST
 */

@WebServlet(urlPatterns = {"/contratos", "/contratos/*"})
public class ContratoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L; // Identificador de versión para serialización
    private ContratoService contratoService; // Instancia del servicio de contrato

    /**
     * Constructor del servlet. Inicializa el servicio de contrato.
     */
    public ContratoServlet() {
        super();
        this.contratoService = new ContratoService();
    }

    /**
     * Método de "filtro" de seguridad rudimentario.
     * En una aplicación real, esto se implementaría usando un {@link jakarta.servlet.Filter}
     * para interceptar todas las peticiones a rutas protegidas antes de que lleguen al servlet.
     *
     * @param request  Petición HTTP.
     * @param response Respuesta HTTP.
     * @return {@code true} si el usuario está autenticado y puede continuar, {@code false} si no y se redirige al login.
     * @throws IOException      Si ocurre un error de E/S durante la redirección.
     * @throws ServletException Si ocurre un error específico del servlet.
     */
    private boolean checkAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false); // No crear una sesión si no existe
        Usuario usuarioLogueado = (session != null) ? (Usuario) session.getAttribute("usuarioLogueado") : null;

        if (usuarioLogueado == null) {
            // Si no hay usuario logueado, redirigir a la página de login
            response.sendRedirect(request.getContextPath() + "/usuarios/login");
            return false; // Indicar que la petición no debe continuar
        }
        return true; // El usuario está autenticado, la petición puede continuar
    }

    /**
     * Maneja las peticiones HTTP GET.
     * Realiza una verificación de autenticación antes de procesar la petición.
     *
     * @param request  Objeto HttpServletRequest que contiene la petición del cliente.
     * @param response Objeto HttpServletResponse que contiene la respuesta del servlet.
     * @throws ServletException Si ocurre un error específico del servlet.
     * @throws IOException      Si ocurre un error de E/S.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Verificar autenticación antes de procesar cualquier GET
        if (!checkAuthentication(request, response)) {
            return; // Si no está autenticado, ya se redirigió y se detiene la ejecución
        }

        String action = request.getPathInfo(); // Obtiene la parte de la URL después de /contratos

        if (action == null) { // Si la URL es solo /contratos
            listarContratos(request, response);
        } else {
            switch (action) {
                case "/new": // /contratos/new
                    mostrarFormularioCrear(request, response);
                    break;
                case "/edit": // /contratos/edit?id=xxx
                    mostrarFormularioEditar(request, response);
                    break;
                case "/delete": // /contratos/delete?id=xxx
                    eliminarContrato(request, response);
                    break;
                default: // Cualquier otra sub-ruta no reconocida
                    listarContratos(request, response); // Por defecto, redirige a la lista
                    break;
            }
        }
    }

    /**
     * Maneja las peticiones HTTP POST.
     * Realiza una verificación de autenticación antes de procesar la petición.
     *
     * @param request  Objeto HttpServletRequest que contiene la petición del cliente.
     * @param response Objeto HttpServletResponse que contiene la respuesta del servlet.
     * @throws ServletException Si ocurre un error específico del servlet.
     * @throws IOException      Si ocurre un error de E/S.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Verificar autenticación antes de procesar cualquier POST
        if (!checkAuthentication(request, response)) {
            return; // Si no está autenticado, ya se redirigió y se detiene la ejecución
        }

        String action = request.getPathInfo(); // Obtiene la parte de la URL después de /contratos

        if (action == null) { // Si la URL es solo /contratos (manejo de POST sin sub-ruta explícita)
            // Simplificación: si el formulario envía un 'actionType' de 'update', se asume actualización.
            if (request.getParameter("actionType") != null && request.getParameter("actionType").equals("update")) {
                actualizarContrato(request, response);
            } else {
                crearContrato(request, response);
            }
        } else {
            switch (action) {
                case "/create": // /contratos/create
                    crearContrato(request, response);
                    break;
                case "/update": // /contratos/update
                    actualizarContrato(request, response);
                    break;
                default: // Cualquier otra sub-ruta no reconocida para POST
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Acción POST no encontrada.");
                    break;
            }
        }
    }

    /**
     * Recupera los contratos (filtrados por el usuario logueado) y los envía a la JSP de listado.
     *
     * @param request  Petición HTTP.
     * @param response Respuesta HTTP.
     * @throws ServletException Si ocurre un error del servlet.
     * @throws IOException      Si ocurre un error de E/S.
     */
    private void listarContratos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

        List<Contrato> listaContratos;
        if (usuarioLogueado != null) {
            // Si hay un usuario logueado, mostrar solo los contratos asociados a ese usuario.
            listaContratos = contratoService.obtenerContratosPorUsuario(usuarioLogueado.getUsername());
        } else {
            // Esto no debería ocurrir si checkAuthentication funciona, pero como fallback, mostrar todos.
            listaContratos = contratoService.obtenerTodosLosContratos();
        }

        request.setAttribute("listaContratos", listaContratos); // Guarda la lista en el ámbito de la petición
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/forms/contratos/listarContratos.jsp");
        dispatcher.forward(request, response); // Redirige a la JSP
    }

    /**
     * Muestra el formulario para crear un nuevo contrato.
     *
     * @param request  Petición HTTP.
     * @param response Respuesta HTTP.
     * @throws ServletException Si ocurre un error del servlet.
     * @throws IOException      Si ocurre un error de E/S.
     */
    private void mostrarFormularioCrear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/forms/contratos/crearContrato.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Muestra el formulario para editar un contrato existente, precargando sus datos.
     *
     * @param request  Petición HTTP.
     * @param response Respuesta HTTP.
     * @throws ServletException Si ocurre un error del servlet.
     * @throws IOException      Si ocurre un error de E/S.
     */
    private void mostrarFormularioEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id")); // Obtiene el ID del contrato de los parámetros
            Contrato contratoExistente = contratoService.obtenerContratoPorId(id); // Busca el contrato

            if (contratoExistente != null) {
                request.setAttribute("contrato", contratoExistente); // Guarda el contrato en el ámbito de la petición
                RequestDispatcher dispatcher = request.getRequestDispatcher("/views/forms/contratos/editarContrato.jsp");
                dispatcher.forward(request, response);
            } else {
                // Si el contrato no se encuentra, redirige a la lista con un mensaje de error.
                response.sendRedirect(request.getContextPath() + "/contratos?status=error&message=ContratoNoEncontrado");
            }
        } catch (NumberFormatException e) {
            // Si el ID no es un número válido, redirige a la lista con un mensaje de error.
            response.sendRedirect(request.getContextPath() + "/contratos?status=error&message=IDContratoInvalido");
        }
    }

    /**
     * Procesa la creación de un nuevo contrato a partir de los datos del formulario.
     *
     * @param request  Petición HTTP.
     * @param response Respuesta HTTP.
     * @throws ServletException Si ocurre un error del servlet.
     * @throws IOException      Si ocurre un error de E/S.
     */
    private void crearContrato(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

        // Doble verificación de autenticación (aunque checkAuthentication ya lo hace)
        if (usuarioLogueado == null) {
            response.sendRedirect(request.getContextPath() + "/usuarios/login");
            return;
        }

        try {
            // Obtiene y convierte los parámetros del formulario a los tipos de datos correctos
            Date fecha_firma = Date.valueOf(request.getParameter("fecha_firma"));
            Date fecha_inicio = Date.valueOf(request.getParameter("fecha_inicio"));
            Date fecha_fin = Date.valueOf(request.getParameter("fecha_fin"));
            String empresa = request.getParameter("empresa");
            String empleado = request.getParameter("empleado");
            String funciones = request.getParameter("funciones");
            double monto = Double.parseDouble(request.getParameter("monto"));
            String frecuencia_de_pago = request.getParameter("frecuencia_de_pago");

            // Crea un nuevo objeto Contrato, asociándolo al usuario logueado
            Contrato nuevoContrato = new Contrato(fecha_firma, fecha_inicio, fecha_fin, empresa, empleado, funciones, monto, frecuencia_de_pago, usuarioLogueado.getUsername());
            boolean creado = contratoService.crearContrato(nuevoContrato); // Intenta crear el contrato

            if (creado) {
                // Si se creó exitosamente, redirige a la lista de contratos con un mensaje de éxito.
                response.sendRedirect(request.getContextPath() + "/contratos?status=success&message=ContratoCreado");
            } else {
                // Si hubo un error, vuelve al formulario de creación con un mensaje de error.
                request.setAttribute("error", "Error al crear contrato. Verifique los datos.");
                request.setAttribute("contrato", nuevoContrato); // Para mantener los datos en el formulario
                RequestDispatcher dispatcher = request.getRequestDispatcher("/views/forms/contratos/crearContrato.jsp");
                dispatcher.forward(request, response);
            }
        } catch (IllegalArgumentException e) {
            // Captura errores de formato de fecha o número
            request.setAttribute("error", "Error en el formato de la fecha o monto. Asegúrese de que los datos sean correctos. " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/forms/contratos/crearContrato.jsp");
            dispatcher.forward(request, response);
        }
    }

    /**
     * Procesa la actualización de un contrato existente a partir de los datos del formulario.
     *
     * @param request  Petición HTTP.
     * @param response Respuesta HTTP.
     * @throws ServletException Si ocurre un error del servlet.
     * @throws IOException      Si ocurre un error de E/S.
     */
    private void actualizarContrato(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioLogueado == null) {
            response.sendRedirect(request.getContextPath() + "/usuarios/login");
            return;
        }

        try {
            // Obtiene y convierte los parámetros del formulario
            int id = Integer.parseInt(request.getParameter("id"));
            Date fechaFirma = Date.valueOf(request.getParameter("fecha_firma"));
            Date fechaInicio = Date.valueOf(request.getParameter("fecha_inicio"));
            Date fechaFin = Date.valueOf(request.getParameter("fecha_fin"));
            String empresa = request.getParameter("empresa");
            String empleado = request.getParameter("empleado");
            String funciones = request.getParameter("funciones");
            double monto = Double.parseDouble(request.getParameter("monto"));
            String frecuenciaDePago = request.getParameter("frecuencia_de_pago");

            // Crea un objeto Contrato con los datos actualizados
            Contrato contratoAActualizar = new Contrato(id, fechaFirma, fechaInicio, fechaFin, empresa, empleado, funciones, monto, frecuenciaDePago, usuarioLogueado.getUsername());
            boolean actualizado = contratoService.actualizarContrato(contratoAActualizar); // Intenta actualizar el contrato

            if (actualizado) {
                // Si se actualizó exitosamente, redirige a la lista con un mensaje de éxito.
                response.sendRedirect(request.getContextPath() + "/contratos?status=success&message=ContratoActualizado");
            } else {
                // Si hubo un error, vuelve al formulario de edición con un mensaje de error.
                request.setAttribute("error", "Error al actualizar contrato. Verifique los datos.");
                request.setAttribute("contrato", contratoAActualizar); // Para mantener los datos en el formulario
                RequestDispatcher dispatcher = request.getRequestDispatcher("/views/forms/contratos/editarContrato.jsp");
                dispatcher.forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "ID o monto inválido. " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/forms/contratos/editarContrato.jsp");
            dispatcher.forward(request, response);
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", "Error en el formato de la fecha. " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/forms/contratos/editarContrato.jsp");
            dispatcher.forward(request, response);
        }
    }

    /**
     * Procesa la eliminación de un contrato.
     *
     * @param request  Petición HTTP.
     * @param response Respuesta HTTP.
     * @throws ServletException Si ocurre un error del servlet.
     * @throws IOException      Si ocurre un error de E/S.
     */
    private void eliminarContrato(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioLogueado == null) {
            response.sendRedirect(request.getContextPath() + "/usuarios/login");
            return;
        }

        try {
            int id = Integer.parseInt(request.getParameter("id")); // Obtiene el ID del contrato a eliminar
            boolean eliminado = contratoService.eliminarContrato(id); // Intenta eliminar el contrato

            if (eliminado) {
                // Si se eliminó exitosamente, redirige a la lista con un mensaje de éxito.
                response.sendRedirect(request.getContextPath() + "/contratos?status=success&message=ContratoEliminado");
            } else {
                // Si hubo un error, redirige a la lista con un mensaje de error.
                response.sendRedirect(request.getContextPath() + "/contratos?status=error&message=ErrorEliminarContrato");
            }
        } catch (NumberFormatException e) {
            // Si el ID no es un número válido, redirige a la lista con un mensaje de error.
            response.sendRedirect(request.getContextPath() + "/contratos?status=error&message=IDInvalido");
        }
    }
}

