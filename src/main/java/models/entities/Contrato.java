package main.java.models.entities;

import java.sql.Date; // Importa java.sql.Date para trabajar con fechas de la base de datos

/**
 * Entidad que representa un contrato en el Sistema de Gestión de Contratos (SGC).
 * Corresponde a la tabla 'Contrato' en la base de datos.
 */
public class Contrato {
    private int id;
    private Date fechaFirma;
    private Date fechaInicio;
    private Date fechaFin;
    private String empresa;
    private String empleado;
    private String funciones;
    private double monto;
    private String frecuenciaDePago;
    private String usuarioUsername; // Clave foránea que referencia al usuario asociado

    /**
     * Constructor vacío por defecto.
     */
    public Contrato() {
    }

    /**
     * Constructor para crear un objeto Contrato sin ID (útil para inserciones, donde el ID es auto-generado).
     *
     * @param fechaFirma       Fecha de firma del contrato.
     * @param fechaInicio      Fecha de inicio de la vigencia del contrato.
     * @param fechaFin         Fecha de finalización de la vigencia del contrato.
     * @param empresa          Nombre de la empresa involucrada.
     * @param empleado         Nombre del empleado asociado.
     * @param funciones        Descripción de las funciones.
     * @param monto            Monto económico del contrato.
     * @param frecuenciaDePago Frecuencia de pago (ej: "Mensual", "Anual").
     * @param usuarioUsername  Nombre de usuario del usuario que gestiona el contrato.
     */
    public Contrato(Date fechaFirma, Date fechaInicio, Date fechaFin, String empresa, String empleado, String funciones, double monto, String frecuenciaDePago, String usuarioUsername) {
        this.fechaFirma = fechaFirma;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.empresa = empresa;
        this.empleado = empleado;
        this.funciones = funciones;
        this.monto = monto;
        this.frecuenciaDePago = frecuenciaDePago;
        this.usuarioUsername = usuarioUsername;
    }

    /**
     * Constructor completo para crear un objeto Contrato con todos sus atributos, incluyendo el ID.
     * Útil para recuperar datos de la base de datos.
     *
     * @param id               Identificador único del contrato.
     * @param fechaFirma       Fecha de firma del contrato.
     * @param fechaInicio      Fecha de inicio de la vigencia del contrato.
     * @param fechaFin         Fecha de finalización de la vigencia del contrato.
     * @param empresa          Nombre de la empresa involucrada.
     * @param empleado         Nombre del empleado asociado.
     * @param funciones        Descripción de las funciones.
     * @param monto            Monto económico del contrato.
     * @param frecuenciaDePago Frecuencia de pago (ej: "Mensual", "Anual").
     * @param usuarioUsername  Nombre de usuario del usuario que gestiona el contrato.
     */
    public Contrato(int id, Date fechaFirma, Date fechaInicio, Date fechaFin, String empresa, String empleado, String funciones, double monto, String frecuenciaDePago, String usuarioUsername) {
        this.id = id;
        this.fechaFirma = fechaFirma;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.empresa = empresa;
        this.empleado = empleado;
        this.funciones = funciones;
        this.monto = monto;
        this.frecuenciaDePago = frecuenciaDePago;
        this.usuarioUsername = usuarioUsername;
    }

    // --- Métodos Getters y Setters para acceder y modificar los atributos ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFechaFirma() {
        return fechaFirma;
    }

    public void setFechaFirma(Date fechaFirma) {
        this.fechaFirma = fechaFirma;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public String getFunciones() {
        return funciones;
    }

    public void setFunciones(String funciones) {
        this.funciones = funciones;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getFrecuenciaDePago() {
        return frecuenciaDePago;
    }

    public void setFrecuenciaDePago(String frecuenciaDePago) {
        this.frecuenciaDePago = frecuenciaDePago;
    }

    public String getUsuarioUsername() {
        return usuarioUsername;
    }

    public void setUsuarioUsername(String usuarioUsername) {
        this.usuarioUsername = usuarioUsername;
    }

    /**
     * Sobreescribe el método toString() para proporcionar una representación de cadena del objeto Contrato.
     * Útil para depuración.
     *
     * @return Una cadena que representa el objeto Contrato.
     */
    @Override
    public String toString() {
        return "Contrato{" +
                "id=" + id +
                ", fechaFirma=" + fechaFirma +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", empresa='" + empresa + '\'' +
                ", empleado='" + empleado + '\'' +
                ", monto=" + monto +
                ", frecuenciaDePago='" + frecuenciaDePago + '\'' +
                ", usuarioUsername='" + usuarioUsername + '\'' +
                '}';
    }
}

