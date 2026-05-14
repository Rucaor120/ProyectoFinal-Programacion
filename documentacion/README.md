# Sistema de Gestión para Tienda de Pinturas

## Descripción general del proyecto
Para mi proyecto final he desarrollado una solución integral de escritorio orientada a la gestión de una **Tienda de Pinturas**. Mi objetivo principal ha sido crear una interfaz amigable e intuitiva (Dashboard) para que los usuarios (empleados y clientes) interactúen con el sistema, permitiendo el registro de usuarios, control de inventario de pinturas y la gestión de compras realizadas.

## Arquitectura y estructura del proyecto
He desarrollado el sistema en Java aplicando una arquitectura **MVC (Model-View-Controller)** en conjunto con el patrón **DAO (Data Access Object)**, asegurando que la interfaz gráfica (Swing) esté completamente desacoplada de la lógica de persistencia.

He estructurado los paquetes de la siguiente manera:
- `db/`: Contiene la clase `ConexionDB.java`, donde he configurado la conexión a MySQL usando `DriverManager`.
- `model/`: Contiene las clases POJO (Plain Old Java Object) que he creado para representar las entidades del dominio (`Usuario`, `Cliente`, `Pintura`, `Compra`).
- `dao/`: Define las interfaces y sus implementaciones (e.g., `UsuarioDAO`, `UsuarioDAOImpl`), donde he encapsulado todas las consultas SQL (usando *PreparedStatement* y control de transacciones con *try-with-resources*).
- `dto/`: Define el objeto `CompraDTO` que utilizo para transferir datos complejos provenientes de consultas con múltiples `JOIN` en la base de datos.
- `view/`: Contiene las interfaces gráficas que he diseñado en Swing (`Login`, `Registro`, `Principal`).
- `Main.java`: Punto de entrada a la aplicación donde configuro el tema visual Nimbus y lanzo la vista de Login.

## Modelo de base de datos
He diseñado el modelo relacional estructurándolo de la siguiente manera y aplicando **Joined Table Inheritance**:

- **usuarios**: Tabla base en la que almaceno `username`, `password`, `email`, `nombre`, `apellidos`, `dni` y `rol`.
- **clientes**: Tabla hija de *usuarios* (relacionada por `usuario_id`), en la que añado el campo `tipo_cliente` (minorista/mayorista). Aplica `ON DELETE CASCADE`.
- **empleados**: Tabla hija de *usuarios* (relacionada por `usuario_id`), en la que añado `turno` y `salario`. Aplica `ON DELETE CASCADE`.
- **pinturas**: Entidad principal del dominio, donde guardo detalles como `nombre`, `color`, `tipo`, `precio` y `stock`.
- **compras**: Tabla de relación (N:M) entre *clientes* y *pinturas*, registrando la fecha de la transacción, cantidad comprada y el precio total.

He incluido el script SQL completo `database.sql` en la raíz del proyecto con todas las tablas y datos de prueba precargados.

## Instrucciones de instalación y ejecución
Para probar mi proyecto, sigue estos pasos:
1. **Base de datos:**
   - Asegúrate de tener un servidor MySQL en ejecución en `localhost:3307`.
   - Ejecuta el script `database.sql` incluido en la raíz del proyecto para crear la base de datos `tiendapinturas` y todas sus tablas.
2. **Entorno Java:**
   - He configurado el proyecto para requerir el JDK instalado (versión 8 o superior).
   - He incluido el driver JDBC de MySQL `mysql-connector-java-8.0.30.jar` en la carpeta `lib/`.
3. **Compilación y ejecución:**
   - Abre la carpeta `ProyectoFinal-Programacion` en tu IDE preferido (Eclipse, IntelliJ IDEA, VS Code).
   - Asegúrate de agregar el `.jar` de la carpeta `lib/` al *Build Path* o *Classpath* del proyecto.
   - Ejecuta la clase `Main.java`.

## Repositorio y Control de Versiones
He versionado todo el proyecto utilizando Git.
`https://github.com/Rucaor120/ProyectoFinal-Programacion`

## Capturas de WakaTime
**

## Extensiones que he implementado
- **DTOs Personalizados:** Para evitar mezclar lógica de base de datos en las vistas al requerir datos de varias tablas (como ocurre en el panel de *Compras*), he implementado `CompraDTO` para estructurar la visualización de un JOIN entre `compras`, `usuarios`, `clientes` y `pinturas`.
- **Transacciones Atómicas:** En el registro de clientes/empleados he asegurado la integridad referencial gestionando manualmente las transacciones con `con.setAutoCommit(false)`, `con.commit()` y `con.rollback()`.

