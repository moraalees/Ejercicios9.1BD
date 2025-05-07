package es.prog2425.ejerciciosBD9_1.data.dao

import es.prog2425.ejerciciosBD9_1.data.db.DatabaseTienda
import es.prog2425.ejerciciosBD9_1.model.Producto
import java.sql.SQLException
import java.sql.Statement

class ProductoDAOH2 : IProductoDAO {
    override fun insertarCampo(nombre: String, precio: Double, stock: Int) {
        val connection = DatabaseTienda.getConnection()
        var stmt: Statement? = null

        try{
            val sql = "INSERT INTO Producto (nombre, precio, stock) VALUES (?, ?, ?)"
            stmt = connection.prepareStatement(sql)
            stmt.setString(1, nombre)
            stmt.setDouble(2, precio)
            stmt.setInt(3, stock)
            stmt.executeUpdate()
        } catch (e: SQLException) {
            throw SQLException("Error al insertar los campos en las tablas", e)
        } catch (e: Exception) {
            throw Exception("Error: ${e.message}")
        } finally {
            stmt?.close()
            DatabaseTienda.closeConnection(connection)
        }
    }

    override fun insertarCampo(producto: Producto) {
        val connection = DatabaseTienda.getConnection()
        var stmt: Statement? = null

        try{
            val sql = "INSERT INTO Producto (nombre, precio, stock) VALUES (?, ?, ?)"
            stmt = connection.prepareStatement(sql)
            stmt.setString(1, producto.nombre)
            stmt.setDouble(2, producto.precio)
            stmt.setInt(3, producto.stock)
            stmt.executeUpdate()
        } catch (e: SQLException) {
            throw SQLException("Error al insertar los campos en las tablas", e)
        } catch (e: Exception) {
            throw Exception("Error: ${e.message}")
        } finally {
            stmt?.close()
            DatabaseTienda.closeConnection(connection)
        }
    }
}