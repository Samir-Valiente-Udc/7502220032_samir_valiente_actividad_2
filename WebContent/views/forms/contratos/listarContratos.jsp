<%@include file="../../resources/header.jsp" %>
<% request.setAttribute("pageTitle", "Listar Contratos"); %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="d-flex justify-content-between align-items-center mb-3">
    <h2>Lista de Contratos</h2>
    <a href="${pageContext.request.contextPath}/contratos/new" class="btn btn-success">Crear Nuevo Contrato</a>
</div>

<div class="table-responsive">
    <table class="table table-striped table-hover">
        <thead class="thead-dark">
        <tr>
            <th>ID</th>
            <th>Fecha Firma</th>
            <th>Inicio</th>
            <th>Fin</th>
            <th>Empresa</th>
            <th>Empleado</th>
            <th>Funciones</th>
            <th>Monto</th>
            <th>Frecuencia</th>
            <th>Usuario</th>
            <th>Acciones</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="contrato" items="${listaContratos}">
            <tr>
                <td><c:out value="${contrato.id}"/></td>
                <td><c:out value="${contrato.fecha_firma}"/></td>
                <td><c:out value="${contrato.fecha_inicio}"/></td>
                <td><c:out value="${contrato.fecha_fin}"/></td>
                <td><c:out value="${contrato.empresa}"/></td>
                <td><c:out value="${contrato.empleado}"/></td>
                <td><c:out value="${contrato.funciones}"/></td>
                <td><c:out value="${contrato.monto}"/></td>
                <td><c:out value="${contrato.frecuencia_de_pago}"/></td>
                <td><c:out value="${contrato.usuarioUsername}"/></td>
                <td>
                    <a href="${pageContext.request.contextPath}/contratos/edit?id=<c:out value="${contrato.id}"/>" class="btn btn-warning btn-sm mb-1">Editar</a>
                    <a href="${pageContext.request.contextPath}/contratos/delete?id=<c:out value="${contrato.id}"/>" class="btn btn-danger btn-sm mb-1" onclick="return confirm('¿Está seguro de que desea eliminar este contrato? Esta acción es irreversible.');">Eliminar</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<c:if test="${empty listaContratos}">
    <div class="alert alert-info text-center">No hay contratos registrados para este usuario.</div>
</c:if>

<%@include file="../../resources/footer.jsp" %>
