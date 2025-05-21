package es.prog2425.ejerciciosBD9_1.app

import es.prog2425.ejerciciosBD9_1.data.db.DatabaseTienda
import java.sql.*

object InicializadorTablas {
    /**
     * Posee una consulta SQL que crea determinadas tablas con sus respectivos campos.
     * Las tablas incluidas son: Usuario, Producto, Pedido y LineaPedido.
     *
     * - Cada sentencia usa `CREATE TABLE IF NOT EXISTS` para evitar errores si la tabla ya existe.
     * - Las tablas incluyen claves primarias (`PRIMARY KEY`) y relaciones mediante claves foráneas (`FOREIGN KEY`).
     *
     * @return Lista de sentencias SQL como cadenas de texto.
     */
    private fun obtenerTablas(): List<String> {
        return listOf(
            "CREATE TABLE IF NOT EXISTS Usuario (id INT AUTO_INCREMENT PRIMARY KEY, nombre VARCHAR(255) NOT NULL, email VARCHAR(255) UNIQUE)",
            "CREATE TABLE IF NOT EXISTS Producto (id INT AUTO_INCREMENT PRIMARY KEY, nombre VARCHAR(255) NOT NULL, precio DECIMAL NOT NULL, stock INT NOT NULL)",
            "CREATE TABLE IF NOT EXISTS Pedido (id INT AUTO_INCREMENT PRIMARY KEY, precioTotal DECIMAL NOT NULL, idUsuario INT, FOREIGN KEY (idUsuario) REFERENCES Usuario(id))",
            "CREATE TABLE IF NOT EXISTS LineaPedido (id INT AUTO_INCREMENT PRIMARY KEY, cantidad INT NOT NULL, precio DECIMAL NOT NULL, idPedido INT, idProducto INT, FOREIGN KEY (idPedido) REFERENCES Pedido(id), FOREIGN KEY (idProducto) REFERENCES Producto(id))"
        )
    }

    /**
     * Crea las tablas de la base de datos ejecutando las consultas SQL por `obtenerTablas()`.
     *
     * - Establece una conexión mediante `DatabaseTienda.getConnection()` a la BD.
     * - Ejecuta cada sentencia SQL con un `Statement`.
     * - Maneja excepciones SQL y generales.
     * - Cierra el `Statement` y la conexión al final.
     *
     * Esta función debería llamarse una vez al inicializar la aplicación para asegurar que las tablas existen.
     */
    fun crearTablas() {
        val connection = try {
            DatabaseTienda.getConnection()
        } catch (e: SQLException) {
            throw SQLException("Error al abrir la conexión: ${e.message}")
            null
        } catch (e: Exception) {
            throw Exception("Error inesperado: ${e.message}")
            null
        }
        println("Conexión establecida exisotamente.")

        if (connection != null){
            var stmt: Statement? = null
            try {
                stmt = connection.createStatement()
                for (sql in obtenerTablas()) {
                    stmt.executeUpdate(sql)
                }
                println("Tablas creadas correctamente.")
            } catch (e: SQLException) {
                throw IllegalArgumentException("Error al crear tablas: ${e.message}")
            } catch (e: Exception) {
                throw IllegalArgumentException("Error: ${e.message}")
            } finally {
                stmt?.close()
                try {
                    DatabaseTienda.closeConnection(connection)
                    println("Conexión cerrada exitosamente.")
                } catch (e: SQLException) {
                    throw SQLException("Error al cerra la conexión: ${e.message}")
                } catch (e: Exception) {
                    throw Exception("Error inesperado: ${e.message}")
                }
            }
        }
    }
}