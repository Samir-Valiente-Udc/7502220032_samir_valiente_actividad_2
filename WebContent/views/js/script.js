// views/js/script.js

document.addEventListener('DOMContentLoaded', function() {
    // Función para cerrar automáticamente las alertas de Bootstrap después de un tiempo
    const dismissAlerts = () => {
        const alerts = document.querySelectorAll('.alert');
        alerts.forEach(alert => {
            // Asegurarse de que Bootstrap JS está cargado para usar su método 'alert'
            if (typeof bootstrap !== 'undefined' && bootstrap.Alert) {
                setTimeout(() => {
                    new bootstrap.Alert(alert).close();
                }, 5000); // Cierra la alerta después de 5 segundos
            } else {
                // Si Bootstrap JS no está cargado, simplemente oculta la alerta
                setTimeout(() => {
                    alert.style.display = 'none';
                }, 5000);
            }
        });
    };

    // Llama a la función al cargar el DOM
    dismissAlerts();

    // Ejemplo de script para responsividad de tablas en móviles (complemento a style.css)
    const tables = document.querySelectorAll('.table-responsive .table');
    tables.forEach(table => {
        const headers = Array.from(table.querySelectorAll('thead th'));
        table.querySelectorAll('tbody tr').forEach(row => {
            Array.from(row.querySelectorAll('td')).forEach((cell, index) => {
                // Asigna el texto del encabezado como un atributo data-label a cada celda
                if (headers[index]) {
                    cell.setAttribute('data-label', headers[index].textContent + ':');
                }
            });
        });
    });
});
