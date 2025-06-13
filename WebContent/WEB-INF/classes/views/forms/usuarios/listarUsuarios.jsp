<%@include file="../../resources/header.jsp" %>
<% request.setAttribute("pageTitle", "Listar Usuarios"); %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="d-flex justify-content-between align-items-center mb-3">
    <h2>Lista de Usuarios</h2>
    <a href="${pageContext.request.contextPath}/usuarios/new" class="btn btn-success">Crear Nuevo Usuario</a>
</div>

<div class="table-responsive">
    <table class="table table-striped table-hover">
        <thead class="thead-dark">
        <tr>
            <th>Username</th>
            <th>Nombre</th>
            <th>Email</th>
            <th>Acciones</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="usuario" items="${listaUsuarios}">
            <tr>
                <td><c:out value="${usuario.username}"/></td>
                <td><c:out value="${usuario.nombre}"/></td>
                <td><c:out value="${usuario.email}"/></td>
                <td>
                    <a href="${pageContext.request.contextPath}/usuarios/edit?username=<c:out value="${usuario.username}"/>" class="btn btn-warning btn-sm mb-1">Editar</a>
                    <a href="${pageContext.request.contextPath}/usuarios/delete?username=<c:out value="${usuario.username}"/>" class="btn btn-danger btn-sm mb-1" onclick="return confirm('¿Está seguro de que desea eliminar este usuario? Esta acción es irreversible.');">Eliminar</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<c:if test="${empty listaUsuarios}">
    <div class="alert alert-info text-center">No hay usuarios registrados en el sistema.</div>
</c:if>

<%@include file="../../resources/footer.jsp" %>
