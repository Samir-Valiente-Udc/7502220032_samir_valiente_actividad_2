<%@include file="../../resources/header.jsp" %>
<% request.setAttribute("pageTitle", "Editar Contrato"); %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="card col-md-8 mx-auto">
    <div class="card-header">
        <h3 class="text-center">Editar Contrato</h3>
    </div>
    <div class="card-body">
        <form action="${pageContext.request.contextPath}/contratos/update" method="post">
            <input type="hidden" name="id" value="<c:out value="${contrato.id}"/>">
            <div class="form-group">
                <label for="fecha_firma">Fecha de Firma:</label>
                <input type="date" class="form-control" id="fecha_firma" name="fecha_firma" value="<c:out value="${contrato.fecha_firma}"/>" required>
            </div>
            <div class="form-group">
                <label for="fecha_inicio">Fecha de Inicio:</label>
                <input type="date" class="form-control" id="fecha_inicio" name="fecha_inicio" value="<c:out value="${contrato.fecha_inicio}"/>" required>
            </div>
            <div class="form-group">
                <label for="fecha_fin">Fecha de Fin:</label>
                <input type="date" class="form-control" id="fecha_fin" name="fecha_fin" value="<c:out value="${contrato.fecha_fin}"/>" required>
            </div>
            <div class="form-group">
                <label for="empresa">Empresa:</label>
                <input type="text" class="form-control" id="empresa" name="empresa" value="<c:out value="${contrato.empresa}"/>" required>
            </div>
            <div class="form-group">
                <label for="empleado">Empleado:</label>
                <input type="text" class="form-control" id="empleado" name="empleado" value="<c:out value="${contrato.empleado}"/>" required>
            </div>
            <div class="form-group">
                <label for="funciones">Funciones:</label>
                <textarea class="form-control" id="funciones" name="funciones" rows="3"><c:out value="${contrato.funciones}"/></textarea>
            </div>
            <div class="form-group">
                <label for="monto">Monto:</label>
                <input type="number" step="0.01" class="form-control" id="monto" name="monto" value="<c:out value="${contrato.monto}"/>" required>
            </div>
            <div class="form-group">
                <label for="frecuencia_de_pago">Frecuencia de Pago:</label>
                <select class="form-control" id="frecuencia_de_pago" name="frecuencia_de_pago" required>
                    <option value="Mensual" <c:if test="${contrato.frecuencia_de_pago eq 'Mensual'}">selected</c:if>>Mensual</option>
                    <option value="Trimestral" <c:if test="${contrato.frecuencia_de_pago eq 'Trimestral'}">selected</c:if>>Trimestral</option>
                    <option value="Semestral" <c:if test="${contrato.frecuencia_de_pago eq 'Semestral'}">selected</c:if>>Semestral</option>
                    <option value="Anual" <c:if test="${contrato.frecuencia_de_pago eq 'Anual'}">selected</c:if>>Anual</option>
                    <option value="Unico" <c:if test="${contrato.frecuencia_de_pago eq 'Unico'}">selected</c:if>>Único</option>
                </select>
            </div>
            <input type="hidden" name="actionType" value="update">
            <div class="d-flex justify-content-between mt-4">
                <button type="submit" class="btn btn-primary flex-fill mr-2">Actualizar Contrato</button>
                <a href="${pageContext.request.contextPath}/contratos" class="btn btn-secondary flex-fill ml-2">Cancelar</a>
            </div>
        </form>
    </div>
</div>

<%@include file="../../resources/footer.jsp" %>
