<%@include file="../../resources/header.jsp" %>
<% request.setAttribute("pageTitle", "Iniciar Sesión"); %>

<div class="card col-md-6 mx-auto">
    <div class="card-header">
        <h3 class="text-center">Iniciar Sesión</h3>
    </div>
    <div class="card-body">
        <form action="${pageContext.request.contextPath}/usuarios/authenticate" method="post">
            <div class="form-group">
                <label for="username">Nombre de Usuario:</label>
                <input type="text" class="form-control" id="username" name="username" required autocomplete="username">
            </div>
            <div class="form-group">
                <label for="password">Contraseña:</label>
                <input type="password" class="form-control" id="password" name="password" required autocomplete="current-password">
            </div>
            <button type="submit" class="btn btn-primary btn-block">Ingresar</button>
        </form>
    </div>
    <div class="card-footer text-center">
        ¿No tienes una cuenta? <a href="${pageContext.request.contextPath}/usuarios/new">Regístrate aquí</a>
    </div>
</div>

<%@include file="../../resources/footer.jsp" %>
