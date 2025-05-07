package es.prog2425.ejerciciosBD9_1.data.dao

import es.prog2425.ejerciciosBD9_1.data.db.DatabaseTienda
import java.sql.*

class UsuarioDAOH2 : IUsuarioDAO {
    override fun insertarCampo(nombre: String, email: String) {
        val connection = DatabaseTienda.getConnection()
        var stmt: Statement? = null

        try{
            val sql = "INSERT INTO Usuario (nombre, email) VALUES (?, ?)"
            stmt = connection.prepareStatement(sql)
            stmt.setString(1, nombre)
            stmt.setString(2, email)
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