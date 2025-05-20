# App Gestión Pedidos en BD

---

## Descripción

Este proyecto, escrito en Kotlin, se basa en la manipulación de datos a través de una BD (Base de Datos) enlazada a código. Para gestionar dicha conexión entre código y la BD, se usa un DataSource.
En esta app, se permite al usuario gestionar diferentes datos y columnas o filas de la BD, ya sean `Usuarios`, `Productos`, `Pedidos` o `Líneas de Pedido`.

---

## Estructura

Dicha app, sigue una estructura de capetas y clases cumpliendo correctamente con los patrones DAO y/o los principios SOLID:
- [`Directorio App`](https://github.com/moraalees/Ejercicios9.1BD/tree/AppDataSource/src/main/kotlin/app): Este directorio es el encargado de mostrarle al usuario la información que se va manipulando a través de la consola. Contiene por así decirlo el flujo del programa, es decir, los diferentes menús.
Este directorio contiene las clases `ProgramaManager`, `UsuariosManager`, `PedidosManager`, `ProductosManager` y `LineaPedidosManager`. En estas clases se encuentran principalmente los menús, además de cómo se hace
a través de consola la manipulación de datos en la BD. Estas clases **NO** inyectan datos en la BD, simplemente llaman al `Service`.
- [`Directorio Data`](https://github.com/moraalees/Ejercicios9.1BD/tree/AppDataSource/src/main/kotlin/data): Este directorio contiene toda la lógica y acceso a la BD. Este se divide en otros dos directorios:
    - [`Directorio Dao`](https://github.com/moraalees/Ejercicios9.1BD/tree/AppDataSource/src/main/kotlin/data/dao): Este subdirectorio contiene las clases que se encargan de realizar operaciones sobre la base de datos. Estas clases siguen el patrón DAO y están implementadas específicamente para la base
      de datos H2. Contiene algunas clases como `UsuarioDAOH2`, `PedidoDAOH2` o `IUsuarioDAO`. Cada clase se conecta a la BD gracias al uso de un Data Source.
    - [`Directorio Db`](https://github.com/moraalees/Ejercicios9.1BD/tree/AppDataSource/src/main/kotlin/data/db): Este otro subdirectorio posee la inicialización de la conexión a la BD gracias a un object `DataSourceFactory`. Este se encarga de proporcionar el Data Source a las demás clases. También hay una
      enum class `Mode` que dictamina tipos de bases de datos soportadas por la aplicación.
- [`Directorio Model`](https://github.com/moraalees/Ejercicios9.1BD/tree/AppDataSource/src/main/kotlin/model): Contiene la lógica de negocio, es decir, las clases sobre las que se van a manipular datos. Estas son, como mencioné anteriormente, `Usuarios`, `Productos`, `Pedidos` y `Líneas de Pedido`. Todas
las clases son de tipo data class ya que solo son datos y no tienen una lógica compleja.
- [`Directorio Service`](https://github.com/moraalees/Ejercicios9.1BD/tree/AppDataSource/src/main/kotlin/service): Este directorio contiene las clases de servicio. Estas, actúan como intermediarias entre los directorios `App` y `Dao`, ya que las clases de servicio implementan las funciones de las clases DAOH2
y la app llama a este directorio. Aquí, existen varias clases, como pueden ser `PedidoService` o `IUsuarioService`, que, como dije anteriormente, emplean funciones DAO, además de validar los datos pasados.
- [`Directorio Ui`](https://github.com/moraalees/Ejercicios9.1BD/tree/AppDataSource/src/main/kotlin/ui): Este último directorio contiene la clase responsable de la interacción entre consola y usuario, `UI`. Dicha clase es usada en el programa principal (Directorio App) para manejar diferentes eventos,
como pueden ser proporcionar una pausa al programa o limpiar la terminal.
- [`Main`](https://github.com/moraalees/Ejercicios9.1BD/blob/AppDataSource/src/main/kotlin/Main.kt): Es el archivo principal que llama a la App para que funcione el programa. En esta clase, se crean instancias de todos los DAO y todos los Servicios para luego inyectarlos al programa principal, y una vez esto,
empieza el programa.

---

## Por qué Data Source

Tras trabajar tanto con DriverManager y con DataSource en las ramas de los ejercicios simples de la BD, me decanté finalmente por realizar el proyecto usando Data Source. Esto fue así por varios motivos:
- Simplicidad: Tras hacer algunas funciones con ambos 'formatos', ya sea cualquiera dentro de las clases DAOH2, me percaté de la diferencia abismal de código entre estos. Esto fue gracias al uso de `.use` en Data Source, ya que se cierran las conexiones instantáneamente, cosa que en Driver Manager no pasa. El factor de que se podría haber dejado abierta la conexión o el Result Set me agobiaba, y fue por esto que preferí Data Source.
- Control de errores: Me percaté de que al usar Data Source, me era más sencillo la validación de errores a la hora de aplicar la conexión.
- Separación de responsabilidades: DataSource me permitió aislar la configuración de la conexión, es decir, las constantes de URL, USUARIO y PASSWORD, facilitando cualquier cambio sin necesidad de modificar el código directamente.

