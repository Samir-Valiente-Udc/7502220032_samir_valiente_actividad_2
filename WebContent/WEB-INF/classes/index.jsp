<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Redireccionando...</title>
    <script type="text/javascript">
        // Redireccionar inmediatamente a la página de login
        window.location.href = "<%= request.getContextPath() %>/views/forms/login.jsp";
    </script>
</head>
<body>
<p>Si no es redirigido automáticamente, haga clic aquí: <a href="<%= request.getContextPath() %>/views/forms/login.jsp">Iniciar Sesión</a></p>
</body>
</html>
