package es.prog2425.ejerciciosBD9_1.data.dao

import es.prog2425.ejerciciosBD9_1.data.db.DatabaseTienda
import java.sql.SQLException
import java.sql.Statement

class LineaPedidoDAOH2 : ILineaPedidoDAO{
    override fun insertarCampo(idPedido: Int, idProducto: Int, cantidad: Int, precio: Double) {
        val connection = DatabaseTienda.getConnection()
        var stmt: Statement? = null

        try{
            val sql = "INSERT INTO Lineapedido (idpedido, idproducto, cantidad, precio) VALUES (?, ?, ?, ?)"
            stmt = connection.prepareStatement(sql)
            stmt.setInt(1, idPedido)
            stmt.setInt(2, idProducto)
            stmt.setInt(3, cantidad)
            stmt.setDouble(4, precio)
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