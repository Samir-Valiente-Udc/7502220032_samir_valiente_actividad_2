<%@include file="../../resources/header.jsp" %>
<% request.setAttribute("pageTitle", "Crear Usuario"); %>

<div class="card col-md-8 mx-auto">
    <div class="card-header">
        <h3 class="text-center">Crear Nuevo Usuario</h3>
    </div>
    <div class="card-body">
        <form action="${pageContext.request.contextPath}/usuarios/create" method="post">
            <div class="form-group">
                <label for="username">Nombre de Usuario:</label>
                <input type="text" class="form-control" id="username" name="username" value="${usuario.username}" required autofocus autocomplete="username">
            </div>
            <div class="form-group">
                <label for="password">Contraseña:</label>
                <input type="password" class="form-control" id="password" name="password" required autocomplete="new-password">
            </div>
            <div class="form-group">
                <label for="nombre">Nombre Completo:</label>
                <input type="text" class="form-control" id="nombre" name="nombre" value="${usuario.nombre}" required autocomplete="name">
            </div>
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" class="form-control" id="email" name="email" value="${usuario.email}" required autocomplete="email">
            </div>
            <div class="d-flex justify-content-between mt-4">
                <button type="submit" class="btn btn-primary flex-fill mr-2">Guardar Usuario</button>
                <a href="${pageContext.request.contextPath}/usuarios" class="btn btn-secondary flex-fill ml-2">Cancelar</a>
            </div>
        </form>
    </div>
</div>

<%@include file="../../resources/footer.jsp" %>
