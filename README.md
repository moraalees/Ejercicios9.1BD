# BD + Kotlin

---

## Información relevante

Este proyecto ha ido avanzando rápidamente. Es por esto mismo, que existen 7 ramas diferentes en este repositorio. Cada rama va siendo una mejora de la anterior. 
> Nota: No todas las ramas contienen un `README.md`, ya que el programa principal (rama `AppDatasource`) contiene el desarrollo final. Todo está explicado en el [`README.md`](https://github.com/moraalees/Ejercicios9.1BD/blob/AppDataSource/README.md) de dicha rama.

---

## Explicación todas las Ramas

Todos los programas están controlados por errores. He aquí la distribución del proyecto:

- [`Main`](https://github.com/moraalees/Ejercicios9.1BD/tree/main): En esta rama se establece una conexión y se cierra de igual forma. Todo esto gracias a la única clase que hay en el programa, `DatabaseTienda`.
- [`Rama ej2`](https://github.com/moraalees/Ejercicios9.1BD/tree/ej2): En esta rama se crean las tablas de la Base de Datos y se generan los primeros datos en todas las tablas. También, se crea la estructura que emplearé de ahí en adelante.
- [`Rama ej3`](https://github.com/moraalees/Ejercicios9.1BD/tree/ej3): En esta rama se crean las funciones en las clases DAOH2 que muestran datos concretos de las tablas.
- [`Rama ej4`](https://github.com/moraalees/Ejercicios9.1BD/tree/ej4): En esta rama se eliminan datos concretos de las tablas, todo esto por que se pedía en el ejercicio a completar.
- [`Rama ej5`](https://github.com/moraalees/Ejercicios9.1BD/tree/ej5): En esta rama se modifican ciertos datos de algunos campos de las tablas.
- [`Rama ej6`](https://github.com/moraalees/Ejercicios9.1BD/tree/ej6): En esta rama se cambia el uso de `DriverManager` por una `DataSource` para establecer la conexión con la BD. Aquí se implementa un usuario con `DataSource` además de mostrar ciertos datos.
- [`Rama AppDataSource`](https://github.com/moraalees/Ejercicios9.1BD/tree/AppDataSource): Esta última rama contiene la versión final del proyecto. Este, está explicado con detalle en su [`README.md`](https://github.com/moraalees/Ejercicios9.1BD/blob/AppDataSource/README.md). Este emplea el uso de menús para que el usuario interactúe con la consola para así poder crear, eliminar y manipular datos a su antojo de la BD.
