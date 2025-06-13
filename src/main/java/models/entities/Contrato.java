package main.java.models.entities;

import java.sql.Date; // Importa java.sql.Date para trabajar con fechas de la base de datos

/**
 * Entidad que representa un contrato en el Sistema de Gestión de Contratos (SGC).
 * Corresponde a la tabla 'Contrato' en la base de datos.
 */
public class Contrato {
    private int id;
    private Date fecha_firma;
    private Date fecha_inicio;
    private Date fecha_fin;
    private String empresa;
    private String empleado;
    private String funciones;
    private double monto;
    private String frecuencia_de_pago;
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
     * @param fecha_inicio      Fecha de inicio de la vigencia del contrato.
     * @param fecha_fin         Fecha de finalización de la vigencia del contrato.
     * @param empresa          Nombre de la empresa involucrada.
     * @param empleado         Nombre del empleado asociado.
     * @param funciones        Descripción de las funciones.
     * @param monto            Monto económico del contrato.
     * @param frecuencia_de_pago Frecuencia de pago (ej: "Mensual", "Anual").
     * @param usuarioUsername  Nombre de usuario del usuario que gestiona el contrato.
     */
    public Contrato(Date fechaFirma, Date fecha_inicio, Date fecha_fin, String empresa, String empleado, String funciones, double monto, String frecuencia_de_pago, String usuarioUsername) {
        this.fecha_firma = fechaFirma;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.empresa = empresa;
        this.empleado = empleado;
        this.funciones = funciones;
        this.monto = monto;
        this.frecuencia_de_pago = frecuencia_de_pago;
        this.usuarioUsername = usuarioUsername;
    }

    /**
     * Constructor completo para crear un objeto Contrato con todos sus atributos, incluyendo el ID.
     * Útil para recuperar datos de la base de datos.
     *
     * @param id               Identificador único del contrato.
     * @param fecha_firma       Fecha de firma del contrato.
     * @param fecha_inicio      Fecha de inicio de la vigencia del contrato.
     * @param fecha_fin         Fecha de finalización de la vigencia del contrato.
     * @param empresa          Nombre de la empresa involucrada.
     * @param empleado         Nombre del empleado asociado.
     * @param funciones        Descripción de las funciones.
     * @param monto            Monto económico del contrato.
     * @param frecuencia_de_pago Frecuencia de pago (ej: "Mensual", "Anual").
     * @param usuarioUsername  Nombre de usuario del usuario que gestiona el contrato.
     */
    public Contrato(int id, Date fecha_firma, Date fecha_inicio, Date fecha_fin, String empresa, String empleado, String funciones, double monto, String frecuencia_de_pago, String usuarioUsername) {
        this.id = id;
        this.fecha_firma = fecha_firma;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.empresa = empresa;
        this.empleado = empleado;
        this.funciones = funciones;
        this.monto = monto;
        this.frecuencia_de_pago = frecuencia_de_pago;
        this.usuarioUsername = usuarioUsername;
    }

    // --- Métodos Getters y Setters para acceder y modificar los atributos ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha_firma() {
        return fecha_firma;
    }

    public void setFecha_firma(Date fecha_firma) {
        this.fecha_firma = fecha_firma;
    }

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public Date getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
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

    public String getFrecuencia_de_pago() {
        return frecuencia_de_pago;
    }

    public void setFrecuencia_de_pago(String frecuencia_de_pago) {
        this.frecuencia_de_pago = frecuencia_de_pago;
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
                ", fecha_firma=" + fecha_firma +
                ", fecha_inicio=" + fecha_inicio +
                ", fecha_fin=" + fecha_fin +
                ", empresa='" + empresa + '\'' +
                ", empleado='" + empleado + '\'' +
                ", monto=" + monto +
                ", frecuencia_de_pago='" + frecuencia_de_pago + '\'' +
                ", usuarioUsername='" + usuarioUsername + '\'' +
                '}';
    }
}

