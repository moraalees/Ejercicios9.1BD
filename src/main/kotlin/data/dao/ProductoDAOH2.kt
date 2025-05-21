package es.prog2425.ejerciciosBD9_1.data.dao

import es.prog2425.ejerciciosBD9_1.data.db.DatabaseTienda
import es.prog2425.ejerciciosBD9_1.model.Producto
import java.sql.SQLException
import java.sql.Statement

class ProductoDAOH2 : IProductoDAO {
    /**
     * Crea un nuevo producto en la base de datos con los valores individuales proporcionados.
     *
     * @param nombre Nombre del producto.
     * @param precio Precio unitario del producto.
     * @param stock Cantidad disponible en inventario.
     *
     * Establece una conexión a la base de datos, prepara una consulta SQL con parámetros,
     * ejecuta la consulta y seguidamente maneja cualquier error que pueda surgir.
     */
    override fun insertarCampo(nombre: String, precio: Double, stock: Int) {
        val connection = try {
            DatabaseTienda.getConnection()
        } catch (e: SQLException) {
            throw SQLException("Error al obtener la conexión: ${e.message}")
            null
        } catch (e: Exception) {
            throw Exception("Error inesperado: ${e.message}")
            null
        }
        println("Conexión exitosa.")
        if (connection != null){
            var stmt: Statement? = null
            try {
                val sql = "INSERT INTO Producto (nombre, precio, stock) VALUES (?, ?, ?)"
                stmt = connection.prepareStatement(sql)
                stmt.setString(1, nombre)
                stmt.setDouble(2, precio)
                stmt.setInt(3, stock)
                stmt.executeUpdate()
            } catch (e: SQLException) {
                throw SQLException("Error al insertar los campos en las tablas: ${e.message}")
            } catch (e: Exception) {
                throw Exception("Error inesperado: ${e.message}")
            } finally {
                stmt?.close()
                try{
                    DatabaseTienda.closeConnection(connection)
                    println("Se cerró la conexión exitosamente.")
                } catch (e: SQLException) {
                    throw SQLException("Error al cerrar la conexión: ${e.message}")
                } catch (e: Exception) {
                    throw Exception("Error inesperado: ${e.message}")
                }
            }
        }
    }

    /**
     * Crea un nuevo producto en la base de datos usando una clase `Producto`.
     *
     * @param producto Clase que contiene toda la información necesaria para insertar en la tabla.
     *
     * Funciona igual que el metodo anterior, pero más limpio porque se tiene una clase con los datos agrupados.
     */
    override fun insertarCampo(producto: Producto) {
        val connection = try {
            DatabaseTienda.getConnection()
        } catch (e: SQLException) {
            throw SQLException("Error al obtener la conexión: ${e.message}")
            null
        } catch (e: Exception) {
            throw Exception("Error inesperado: ${e.message}")
            null
        }
        println("Conexión exitosa.")
        if (connection != null){
            var stmt: Statement? = null
            try {
                val sql = "INSERT INTO Producto (nombre, precio, stock) VALUES (?, ?, ?)"
                stmt = connection.prepareStatement(sql)
                stmt.setString(1, producto.nombre)
                stmt.setDouble(2, producto.precio)
                stmt.setInt(3, producto.stock)
                stmt.executeUpdate()
            } catch (e: SQLException) {
                throw SQLException("Error al insertar los campos en las tablas")
            } catch (e: Exception) {
                throw Exception("Error al insertar los campos en las tablas")
            } finally {
                stmt?.close()
                try{
                    DatabaseTienda.closeConnection(connection)
                    println("Se cerró la conexión exitosamente.")
                } catch (e: SQLException) {
                    throw SQLException("Error al cerrar la conexión: ${e.message}")
                } catch (e: Exception) {
                    throw Exception("Error inesperado: ${e.message}")
                }
            }
        }
    }
}