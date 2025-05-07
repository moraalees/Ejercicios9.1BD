package es.prog2425.ejerciciosBD9_1.data.db

import java.sql.*

object DatabaseTienda {
    private const val URL = "jdbc:h2:file:./data/tienda"
    private const val USER = "cristian"
    private const val PASSWORD = ""

    init{
        try{
            Class.forName("org.h2.Driver")
        } catch (e: ClassNotFoundException) {
            throw IllegalStateException("No se encontró el Driver H2.", e)
        }
    }

    /**
     * Establece una conexión a la base de datos utilizando las constantes configuradas.
     *
     * Esta función intenta conectarse a la base de datos con los valores especificados en la URL,
     * USER y PASSWORD. Si ocurre algún error durante la conexión, se lanza una excepción.
     *
     * @return Una conexión a la base de datos.
     * @throws SQLException Si hay un error al intentar conectar con la base de datos.
     * @throws Exception Cualquier error inesperado durante la conexión.
     */
    fun getConnection(): Connection {
        return try {
            DriverManager.getConnection(URL, USER, PASSWORD)
        } catch (e: SQLException) {
            throw SQLException("Error en la conexión: ${e.message}")
        } catch (e: Exception) {
            throw Exception("Error: ${e.message}")
        }
    }

    /**
     * Apaga la conexión a la base de datos proporcionada.
     *
     * Esta función se encarga de cerrar de manera segura la conexión a la base de datos.
     * Si ocurre algún error al cerrar la conexión, se lanza una excepción.
     *
     * @param conn La conexión a la base de datos que se desea cerrar.
     * @throws SQLException Si ocurre un error al intentar cerrar la conexión.
     * @throws Exception Cualquier error inesperado al cerrarla.
     */
    fun closeConnection(conn: Connection){
        try{
            conn.close()
        } catch (e: SQLException) {
            throw SQLException("Error al cerrar la conexión: ${e.message}")
        } catch (e: Exception) {
            throw Exception("Error: ${e.message}")
        }
    }
}