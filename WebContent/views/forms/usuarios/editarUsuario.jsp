<%@include file="../../resources/header.jsp" %>
<% request.setAttribute("pageTitle", "Editar Usuario"); %>

<div class="card col-md-8 mx-auto">
    <div class="card-header">
        <h3 class="text-center">Editar Usuario</h3>
    </div>
    <div class="card-body">
        <form action="${pageContext.request.contextPath}/usuarios/update" method="post">
            <div class="form-group">
                <label for="username">Nombre de Usuario:</label>
                <input type="text" class="form-control" id="username" name="username" value="<c:out value="${usuario.username}"/>" readonly>
            </div>
            <div class="form-group">
                <label for="password">Contraseña (dejar en blanco para no cambiar):</label>
                <input type="password" class="form-control" id="password" name="password" placeholder="Dejar en blanco para no cambiar" autocomplete="new-password">
            </div>
            <div class="form-group">
                <label for="nombre">Nombre Completo:</label>
                <input type="text" class="form-control" id="nombre" name="nombre" value="<c:out value="${usuario.nombre}"/>" required autocomplete="name">
            </div>
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" class="form-control" id="email" name="email" value="<c:out value="${usuario.email}"/>" required autocomplete="email">
            </div>
            <input type="hidden" name="actionType" value="update">
            <div class="d-flex justify-content-between mt-4">
                <button type="submit" class="btn btn-primary flex-fill mr-2">Actualizar Usuario</button>
                <a href="${pageContext.request.contextPath}/usuarios" class="btn btn-secondary flex-fill ml-2">Cancelar</a>
            </div>
        </form>
    </div>
</div>

<%@include file="../../resources/footer.jsp" %>
