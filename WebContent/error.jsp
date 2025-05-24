<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error del Sistema</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            font-family: 'Inter', sans-serif;
            background-color: #f8f9fa;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            margin: 0;
        }
        .error-container {
            background-color: #ffffff;
            padding: 40px;
            border-radius: 12px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
            text-align: center;
            max-width: 600px;
            width: 90%;
        }
        h1 {
            color: #dc3545; /* Rojo de Bootstrap para errores */
            font-size: 3rem;
            margin-bottom: 20px;
        }
        p {
            color: #6c757d;
            font-size: 1.1rem;
            margin-bottom: 25px;
        }
        .btn-home {
            background-color: #007bff;
            color: white;
            padding: 12px 25px;
            border-radius: 8px;
            text-decoration: none;
            font-weight: bold;
            transition: background-color 0.3s ease, transform 0.2s ease;
            display: inline-block;
        }
        .btn-home:hover {
            background-color: #0056b3;
            transform: translateY(-2px);
        }
    </style>
</head>
<body>
<div class="error-container">
    <h1>¡Oops! Ha ocurrido un error.</h1>
    <p>Parece que algo salió mal en el servidor.</p>
    <p>Por favor, inténtelo de nuevo más tarde o contacte al administrador si el problema persiste.</p>

    <%-- Mostrar detalles del error solo en desarrollo (opcional) --%>
    <% if (exception != null) { %>
    <div class="alert alert-danger mt-4" role="alert" style="text-align: left;">
        <strong>Detalles del Error:</strong><br>
        Mensaje: <%= exception.getMessage() != null ? exception.getMessage() : "N/A" %><br>
        Tipo de Excepción: <%= exception.getClass().getName() %><br>
        <%-- Puedes descomentar la siguiente línea para mostrar el stack trace completo,
             pero no es recomendable en producción por seguridad. --%>
        <%-- <pre><%= exception.toString() %></pre> --%>
    </div>
    <% } %>

    <a href="<%= request.getContextPath() %>/" class="btn-home">Volver a la página de inicio</a>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.min.js"></script>
</body>
</html>
