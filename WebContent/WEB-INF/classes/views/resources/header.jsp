<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SGC - <c:out value="${pageTitle}" default="Gestión de Contratos"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/views/css/style.css" rel="stylesheet">
    <style>
        /* Estilos generales para toda la aplicación */
        body {
            font-family: 'Inter', sans-serif; /* Fuente Inter */
            background-color: #f4f7f6;
            color: #333;
            padding-top: 70px; /* Espacio para el navbar fijo */
        }
        .navbar {
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }
        .navbar-brand {
            font-weight: bold;
            font-size: 1.5rem;
        }
        .container {
            background-color: #ffffff;
            padding: 30px;
            border-radius: 12px; /* Bordes redondeados */
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            margin-top: 20px;
            margin-bottom: 20px;
        }
        .card {
            border-radius: 12px; /* Bordes redondeados para tarjetas */
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.05);
        }
        .card-header {
            background-color: #3498db;
            color: white;
            border-top-left-radius: 12px;
            border-top-right-radius: 12px;
            font-weight: bold;
            padding: 15px;
        }
        .btn-primary {
            background-color: #3498db;
            border-color: #3498db;
            border-radius: 8px;
            transition: background-color 0.3s ease, transform 0.2s ease;
        }
        .btn-primary:hover {
            background-color: #2980b9;
            border-color: #2980b9;
            transform: translateY(-1px);
        }
        .btn-success {
            background-color: #2ecc71;
            border-color: #2ecc71;
            border-radius: 8px;
            transition: background-color 0.3s ease, transform 0.2s ease;
        }
        .btn-success:hover {
            background-color: #27ae60;
            border-color: #27ae60;
            transform: translateY(-1px);
        }
        .btn-warning {
            background-color: #f39c12;
            border-color: #f39c12;
            border-radius: 8px;
            transition: background-color 0.3s ease, transform 0.2s ease;
        }
        .btn-warning:hover {
            background-color: #e67e22;
            border-color: #e67e22;
            transform: translateY(-1px);
        }
        .btn-danger {
            background-color: #e74c3c;
            border-color: #e74c3c;
            border-radius: 8px;
            transition: background-color 0.3s ease, transform 0.2s ease;
        }
        .btn-danger:hover {
            background-color: #c0392b;
            border-color: #c0392b;
            transform: translateY(-1px);
        }
        .btn-secondary {
            background-color: #95a5a6;
            border-color: #95a5a6;
            border-radius: 8px;
            transition: background-color 0.3s ease, transform 0.2s ease;
        }
        .btn-secondary:hover {
            background-color: #7f8c8d;
            border-color: #7f8c8d;
            transform: translateY(-1px);
        }
        .form-control {
            border-radius: 8px;
        }
        .table {
            border-radius: 12px;
            overflow: hidden; /* Para que los bordes redondeados afecten a la tabla */
        }
        .thead-dark th {
            background-color: #34495e;
            color: white;
        }
        .alert {
            border-radius: 8px;
            margin-top: 15px;
        }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/">SGC</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/contratos">Contratos</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/usuarios">Usuarios</a>
            </li>
        </ul>
        <ul class="navbar-nav">
            <c:if test="${sessionScope.usuarioLogueado != null}">
                <li class="nav-item">
                    <span class="nav-link text-white">Hola, <c:out value="${sessionScope.usuarioLogueado.nombre}"/> (<c:out value="${sessionScope.usuarioLogueado.username}"/>)</span>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/usuarios/logout">Cerrar Sesión</a>
                </li>
            </c:if>
            <c:if test="${sessionScope.usuarioLogueado == null}">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/usuarios/login">Iniciar Sesión</a>
                </li>
            </c:if>
        </ul>
    </div>
</nav>
<div class="container mt-4">
    <c:if test="${param.status eq 'success'}">
    <div class="alert alert-success alert-dismissible fade show" role="alert">
        <c:choose>
            <c:when test="${param.message eq 'UsuarioCreado'}">Usuario creado exitosamente.</c:when>
            <c:when test="${param.message eq 'UsuarioActualizado'}">Usuario actualizado exitosamente.</c:when>
            <c:when test="${param.message eq 'UsuarioEliminado'}">Usuario eliminado exitosamente.</c:when>
            <c:when test="${param.message eq 'ContratoCreado'}">Contrato creado exitosamente.</c:when>
            <c:when test="${param.message eq 'ContratoActualizado'}">Contrato actualizado exitosamente.</c:when>
            <c:when test="${param.message eq 'ContratoEliminado'}">Contrato eliminado exitosamente.</c:when>
            <c:when test="${param.message eq 'SesionCerrada'}">Sesión cerrada exitosamente.</c:when>
            <c:otherwise>Operación exitosa.</c:otherwise>
        </c:choose>
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
    </c:if>
    <c:if test="${param.status eq 'error' || requestScope.error != null}">
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
        <c:choose>
            <c:when test="${requestScope.error != null}"><c:out value="${requestScope.error}"/></c:when>
            <c:when test="${param.message eq 'UsuarioNoEncontrado'}">Error: Usuario no encontrado.</c:when>
            <c:when test="${param.message eq 'IDContratoInvalido'}">Error: ID de contrato inválido.</c:when>
            <c:when test="${param.message eq 'ContratoNoEncontrado'}">Error: Contrato no encontrado.</c:when>
            <c:when test="${param.message eq 'ErrorCrearUsuario'}">Error al crear usuario.</c:when>
            <c:when test="${param.message eq 'ErrorActualizarUsuario'}">Error al actualizar usuario.</c:when>
            <c:when test="${param.message eq 'ErrorEliminarUsuario'}">Error al eliminar usuario.</c:when>
            <c:when test="${param.message eq 'ErrorCrearContrato'}">Error al crear contrato.</c:when>
            <c:when test="${param.message eq 'ErrorActualizarContrato'}">Error al actualizar contrato.</c:when>
            <c:when test="${param.message eq 'ErrorEliminarContrato'}">Error al eliminar contrato.</c:when>
            <c:when test="${param.message eq 'IDInvalido'}">ID inválido.</c:when>
            <c:otherwise>Ocurrió un error inesperado.</c:otherwise>
        </c:choose>
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
    </c:if>
