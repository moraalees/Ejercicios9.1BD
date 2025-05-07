package es.prog2425.ejerciciosBD9_1.app

import es.prog2425.ejerciciosBD9_1.data.db.DatabaseTienda
import java.sql.*

object InicializadorTablas {
    private fun obtenerTablas(): List<String> {
        return listOf(
            "CREATE TABLE IF NOT EXISTS Usuario (id INT AUTO_INCREMENT PRIMARY KEY, nombre VARCHAR(255) NOT NULL, email VARCHAR(255) UNIQUE)",
            "CREATE TABLE IF NOT EXISTS Producto (id INT AUTO_INCREMENT PRIMARY KEY, nombre VARCHAR(255) NOT NULL, precio DECIMAL NOT NULL, stock INT NOT NULL)",
            "CREATE TABLE IF NOT EXISTS Pedido (id INT AUTO_INCREMENT PRIMARY KEY, precioTotal DECIMAL NOT NULL, idUsuario INT, FOREIGN KEY (idUsuario) REFERENCES Usuario(id))",
            "CREATE TABLE IF NOT EXISTS LineaPedido (id INT AUTO_INCREMENT PRIMARY KEY, cantidad INT NOT NULL, precio DECIMAL NOT NULL, idPedido INT, idProducto INT, FOREIGN KEY (idPedido) REFERENCES Pedido(id), FOREIGN KEY (idProducto) REFERENCES Producto(id))"
        )
    }

    fun crearTablas() {
        val connection = DatabaseTienda.getConnection()
        var stmt: Statement? = null
        try {
            stmt = connection.createStatement()
            for (sql in obtenerTablas()) {
                stmt.executeUpdate(sql)
            }
            println("Tablas creadas correctamente.")
        } catch (e: SQLException) {
            throw SQLException("Error al crear tablas: ${e.message}")
        } catch (e: Exception) {
            throw Exception("Error: ${e.message}")
        }finally {
            stmt?.close()
            DatabaseTienda.closeConnection(connection)
        }
    }
}