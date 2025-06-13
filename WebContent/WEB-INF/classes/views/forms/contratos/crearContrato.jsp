<%@include file="../../resources/header.jsp" %>
<% request.setAttribute("pageTitle", "Crear Nuevo Contrato"); %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="card col-md-8 mx-auto">
    <div class="card-header">
        <h3 class="text-center">Crear Nuevo Contrato</h3>
    </div>
    <div class="card-body">
        <form action="${pageContext.request.contextPath}/contratos/guardar" method="post">
            <div class="form-group">
                <label for="fecha_firma">Fecha de Firma:</label>
                <input type="date" class="form-control" id="fecha_firma" name="fecha_firma" required>
            </div>
            <div class="form-group">
                <label for="fecha_inicio">Fecha de Inicio:</label>
                <input type="date" class="form-control" id="fecha_inicio" name="fecha_inicio" required>
            </div>
            <div class="form-group">
                <label for="fecha_fin">Fecha de Fin:</label>
                <input type="date" class="form-control" id="fecha_fin" name="fecha_fin" required>
            </div>
            <div class="form-group">
                <label for="empresa">Empresa:</label>
                <input type="text" class="form-control" id="empresa" name="empresa" required>
            </div>
            <div class="form-group">
                <label for="empleado">Empleado:</label>
                <input type="text" class="form-control" id="empleado" name="empleado" required>
            </div>
            <div class="form-group">
                <label for="funciones">Funciones:</label>
                <textarea class="form-control" id="funciones" name="funciones" rows="3" required></textarea>
            </div>
            <div class="form-group">
                <label for="monto">Monto:</label>
                <input type="number" step="0.01" class="form-control" id="monto" name="monto" required>
            </div>
            <div class="form-group">
                <label for="frecuencia_de_pago">Frecuencia de Pago:</label>
                <select class="form-control" id="frecuencia_de_pago" name="frecuencia_de_pago" required>
                    <option value="">Seleccione una opción</option>
                    <option value="Mensual">Mensual</option>
                    <option value="Trimestral">Trimestral</option>
                    <option value="Semestral">Semestral</option>
                    <option value="Anual">Anual</option>
                    <option value="Unico">Único</option>
                </select>
            </div>
            <input type="hidden" name="actionType" value="create">
            <div class="d-flex justify-content-between mt-4">
                <button type="submit" class="btn btn-primary flex-fill mr-2">Guardar Contrato</button>
                <a href="${pageContext.request.contextPath}/contratos" class="btn btn-secondary flex-fill ml-2">Cancelar</a>
            </div>
        </form>
    </div>
</div>

<%@include file="../../resources/footer.jsp" %>