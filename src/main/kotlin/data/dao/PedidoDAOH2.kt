package es.prog2425.ejerciciosBD9_1.data.dao

import es.prog2425.ejerciciosBD9_1.data.db.DatabaseTienda
import java.sql.SQLException
import java.sql.Statement

class PedidoDAOH2 : IPedidoDAO {
    override fun insertarCampo(idUsuario: Int, precio: Double) {
        val connection = DatabaseTienda.getConnection()
        var stmt: Statement? = null

        try{
            val sql = "INSERT INTO Pedido (idusuario, preciototal) VALUES (?, ?)"
            stmt = connection.prepareStatement(sql)
            stmt.setInt(1, idUsuario)
            stmt.setDouble(2, precio)
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