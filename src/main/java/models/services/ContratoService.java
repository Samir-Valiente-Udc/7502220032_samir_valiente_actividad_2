package main.java.models.services;

import main.java.models.entities.Contrato;
import main.java.models.repositories.ContratoRepository;

import java.util.List;

/**
 * Clase de servicio que encapsula la lógica de negocio para la entidad {@link Contrato}.
 * Actúa como intermediario entre los controladores y el repositorio.
 */
public class ContratoService {
    private ContratoRepository contratoRepository;

    /**
     * Constructor del servicio de contrato. Inicializa el repositorio de contrato.
     */
    public ContratoService() {
        this.contratoRepository = new ContratoRepository();
    }

    /**
     * Crea un nuevo contrato en el sistema.
     * Aquí se podrían añadir validaciones de negocio adicionales, por ejemplo,
     * que la fecha de inicio no sea posterior a la fecha de fin.
     *
     * @param contrato El objeto {@link Contrato} a crear.
     * @return {@code true} si el contrato fue creado exitosamente, {@code false} en caso contrario.
     */
    public boolean crearContrato(Contrato contrato) {
        // Ejemplo de validación de negocio: asegurar que las fechas son coherentes
        if (contrato.getFecha_inicio().after(contrato.getFecha_fin())) {
            System.err.println("Error en ContratoService: La fecha de inicio no puede ser posterior a la fecha de fin.");
            return false;
        }
        return contratoRepository.createContrato(contrato);
    }

    /**
     * Obtiene un contrato por su ID único.
     *
     * @param id El ID del contrato a buscar.
     * @return El objeto {@link Contrato} encontrado, o {@code null} si no existe.
     */
    public Contrato obtenerContratoPorId(int id) {
        return contratoRepository.getContratoById(id);
    }

    /**
     * Obtiene una lista de todos los contratos registrados en el sistema.
     *
     * @return Una {@link List} de objetos {@link Contrato}. Retorna una lista vacía si no hay contratos.
     */
    public List<Contrato> obtenerTodosLosContratos() {
        return contratoRepository.getAllContratos();
    }

    /**
     * Obtiene una lista de contratos asociados a un nombre de usuario específico.
     * Esto permite filtrar los contratos por el usuario que los gestiona.
     *
     * @param username El nombre de usuario del cual se quieren obtener los contratos.
     * @return Una {@link List} de objetos {@link Contrato} asociados al usuario.
     */
    public List<Contrato> obtenerContratosPorUsuario(String username) {
        return contratoRepository.getContratosByUsuario(username);
    }

    /**
     * Actualiza la información de un contrato existente.
     *
     * @param contrato El objeto {@link Contrato} con la información actualizada.
     * @return {@code true} si el contrato fue actualizado exitosamente, {@code false} en caso contrario.
     */
    public boolean actualizarContrato(Contrato contrato) {
        // Ejemplo de validación de negocio antes de actualizar
        if (contrato.getFecha_inicio().after(contrato.getFecha_fin())) {
            System.err.println("Error en ContratoService: La fecha de inicio no puede ser posterior a la fecha de fin durante la actualización.");
            return false;
        }
        return contratoRepository.updateContrato(contrato);
    }

    /**
     * Elimina un contrato del sistema por su ID.
     *
     * @param id El ID del contrato a eliminar.
     * @return {@code true} si el contrato fue eliminado exitosamente, {@code false} en caso contrario.
     */
    public boolean eliminarContrato(int id) {
        return contratoRepository.deleteContrato(id);
    }
}
