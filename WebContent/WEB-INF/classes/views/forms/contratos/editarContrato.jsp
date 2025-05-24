<%@include file="../../resources/header.jsp" %>
<% request.setAttribute("pageTitle", "Editar Contrato"); %>

<div class="card col-md-8 mx-auto">
    <div class="card-header">
        <h3 class="text-center">Editar Contrato</h3>
    </div>
    <div class="card-body">
        <form action="${pageContext.request.contextPath}/contratos/update" method="post">
            <input type="hidden" name="id" value="<c:out value="${contrato.id}"/>">
            <div class="form-group">
                <label for="fechaFirma">Fecha de Firma:</label>
                <input type="date" class="form-control" id="fechaFirma" name="fechaFirma" value="<c:out value="${contrato.fechaFirma}"/>" required>
            </div>
            <div class="form-group">
                <label for="fechaInicio">Fecha de Inicio:</label>
                <input type="date" class="form-control" id="fechaInicio" name="fechaInicio" value="<c:out value="${contrato.fechaInicio}"/>" required>
            </div>
            <div class="form-group">
                <label for="fechaFin">Fecha de Fin:</label>
                <input type="date" class="form-control" id="fechaFin" name="fechaFin" value="<c:out value="${contrato.fechaFin}"/>" required>
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
                <label for="frecuenciaDePago">Frecuencia de Pago:</label>
                <select class="form-control" id="frecuenciaDePago" name="frecuenciaDePago" required>
                    <option value="Mensual" <c:if test="${contrato.frecuenciaDePago eq 'Mensual'}">selected</c:if>>Mensual</option>
                    <option value="Trimestral" <c:if test="${contrato.frecuenciaDePago eq 'Trimestral'}">selected</c:if>>Trimestral</option>
                    <option value="Semestral" <c:if test="${contrato.frecuenciaDePago eq 'Semestral'}">selected</c:if>>Semestral</option>
                    <option value="Anual" <c:if test="${contrato.frecuenciaDePago eq 'Anual'}">selected</c:if>>Anual</option>
                    <option value="Unico" <c:if test="${contrato.frecuenciaDePago eq 'Unico'}">selected</c:if>>Único</option>
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
