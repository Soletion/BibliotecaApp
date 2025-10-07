# Sistema de Gestión de Biblioteca

Una aplicación de escritorio desarrollada en Java con interfaz gráfica Swing para gestionar el inventario de una biblioteca hecha para una actividad de clase de Desarrollo de Aplicaciones Web.

## 📋 Características

- **Gestión completa de libros**: Agregar, modificar, eliminar y buscar libros
- **Interfaz intuitiva**: Diseño claro y fácil de usar
- **Búsqueda flexible**: Buscar por título, autor o género
- **Validación de datos**: Control de entradas y manejo de errores
- **Base de datos integrada**: Persistencia de datos mediante DAO
- **Feedback visual**: Mensajes de confirmación y error con temporizador

## 🛠️ Funcionalidades

### Operaciones CRUD
- **Agregar libros**: Ingresar título, autor, género y cantidad
- **Modificar libros**: Actualizar información existente
- **Eliminar libros**: Remover con confirmación
- **Eliminación automática**: Cuando la cantidad llega a cero

### Búsqueda y Filtrado
- Búsqueda en tiempo real por diferentes criterios
- Filtrado por título, autor o género
- Lista actualizable dinámicamente

### Validaciones
- Campos obligatorios
- Validación de números en cantidad
- Prevención de duplicados y errores
- Confirmación para operaciones críticas

## 🖥️ Tecnologías Utilizadas

- **Java** - Lenguaje de programación
- **Swing** - Interfaz gráfica de usuario
- **DAO Pattern** - Acceso a datos
- **ObjectDB** - Base de datos 

## 📦 Estructura del Proyecto

```
src/
├── com/
│   └── biblioteca/
│       ├── dao/
│       │   └── LibroDAO.java
│       ├── model/
│       │   └── Libro.java
│       └── gui/
│           └── BibliotecaGUI.java
```

### Clases Principales

- **BibliotecaGUI**: Interfaz principal de usuario
- **LibroDAO**: Acceso y operaciones con la base de datos
- **Libro**: Modelo de datos para libros

## 🚀 Instalación y Uso

### Prerrequisitos
- Java JDK 8 o superior
- Base de datos ObjectDB configurada
- Dependencias del proyecto

### Ejecución
1. Compilar el proyecto:
```bash
javac -cp . com/biblioteca/gui/BibliotecaGUI.java
```

2. Ejecutar la aplicación:
```bash
java -cp . com.biblioteca.gui.BibliotecaGUI
```

## 🎯 Características de la Interfaz

### Panel de Formulario
- Campos para título, autor, género y cantidad
- Botón de agregar centrado
- Validación en tiempo real

### Panel de Lista
- Vista tabular alineada de libros
- Cabecera con formato monospace
- Selección individual con highlight
- Información completa: ID, título, autor, género, cantidad

### Panel de Búsqueda
- Selector de criterio (título/autor/género)
- Campo de búsqueda con botón
- Resultados filtrados en tiempo real

### Panel de Acciones
- Botones modificar y eliminar
- Habilitación condicional según selección
- Confirmaciones para operaciones destructivas

## 🔧 Configuración

Asegúrate de configurar correctamente:
1. La conexión a la base de datos en `LibroDAO`
2. Las credenciales de acceso a la BD
3. El esquema de la base de datos según el modelo Libro

## 📝 Autor

Desarrollado por **Natasha Solange Marcos Curbalán**.
