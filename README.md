# 📑 Sistema de Gestión de Contratos (SGC)

Bienvenido al **Sistema de Gestión de Contratos (SGC)**, una aplicación web desarrollada en Java que permite administrar contratos y usuarios de manera eficiente, segura y moderna.

---

## 🚀 Características principales

- 📝 **Gestión de Contratos:** Crear, listar, editar y eliminar contratos.
- 👤 **Gestión de Usuarios:** Registro, edición y eliminación de usuarios.
- 🔒 **Autenticación y Seguridad:** Acceso restringido a usuarios autorizados.
- 📊 **Interfaz Intuitiva:** Vistas limpias y responsivas gracias a Bootstrap.
- 🗄️ **Persistencia de Datos:** Integración con base de datos MySQL.
- 🛠️ **Arquitectura MVC:** Separación clara entre lógica, presentación y datos.

---

## 🛠️ Tecnologías Utilizadas

- **Java 11** ☕
- **Jakarta Servlet API 6.0.0** 🌐
- **JSP (JavaServer Pages)** 🖥️
- **JSTL (JavaServer Pages Standard Tag Library) 2.0.0** 📚
- **MySQL** 🐬 (conector: `mysql-connector-java-jdk11-8.0.19`)
- **Bootstrap 4.5.2** 🎨
- **HTML5, CSS3, JavaScript** 💻
- **JDBC** 🔗
- **IntelliJ IDEA** 🧠 (IDE principal)
- **Patrón MVC** 🏗️
- **Tomcat 10**

---

## 📁 Estructura del Proyecto

```
7502220032_samir_valiente_actividad_2/
│
├── src/main/java/
│   ├── controllers/         # Servlets (controladores)
│   ├── models/
│   │   ├── entities/        # Entidades (POJOs)
│   │   ├── repositories/    # Acceso a datos (CRUD)
│   │   └── services/        # Lógica de negocio
│   └── utils/               # Utilidades (conexión BD)
│
├── WebContent/
│   ├── views/
│   │   ├── forms/           # Formularios JSP
│   │   ├── resources/       # Fragmentos JSP (header, footer)
│   │   ├── css/             # Estilos personalizados
│   │   └── js/              # Scripts JS
│   ├── index.html           # Página de inicio
│   └── error.jsp            # Página de error
│
├── lib/                     # Librerías externas
├── script.sql               # Script de creación de BD
└── .gitignore               # Exclusiones de Git
```

---

## ⚙️ Configuración y Ejecución

1. **Clona el repositorio:**
   ```bash
   git clone https://github.com/Samir-Valiente-Udc/7502220032_samir_valiente_actividad_2.git
   ```

2. **Configura la base de datos:**
   - Asegúrate de tener MySQL instalado y en ejecución.
   - Ejecuta el archivo `script.sql` para crear la base de datos y tablas necesarias.

3. **Configura la conexión a la base de datos:**
   - Edita `src/main/java/utils/DatabaseConnection.java` con tus credenciales de MySQL.

4. **Importa el proyecto en IntelliJ IDEA:**
   - Selecciona "Import Project" y elige la carpeta raíz del proyecto.

5. **Despliega en un servidor compatible (Tomcat recomendado):**
   - Compila y ejecuta el proyecto.
   - Accede a [http://localhost:8080/](http://localhost:8080/) en tu navegador.

---

## 🧩 Descripción de Carpetas y Archivos

- **controllers/**: Servlets que gestionan las peticiones HTTP.
- **models/entities/**: Clases que representan las tablas de la base de datos.
- **models/repositories/**: Métodos CRUD para interactuar con la base de datos.
- **models/services/**: Lógica de negocio y validaciones.
- **utils/DatabaseConnection.java**: Clase para la gestión de la conexión MySQL.
- **WebContent/views/forms/**: Formularios JSP para contratos y usuarios.
- **WebContent/views/resources/**: Fragmentos reutilizables (header, footer).
- **WebContent/views/css/**: Estilos personalizados.
- **WebContent/views/js/**: Scripts JavaScript.
- **script.sql**: Script para crear la base de datos y tablas.

---

## 📝 Notas

- Los archivos `.class` y la carpeta `out/` no están documentados ya que son generados automáticamente.
- El proyecto sigue el patrón MVC para facilitar el mantenimiento y la escalabilidad.

---

## 👨‍💻 Autor

Desarrollado por **Samir Valiente**  
