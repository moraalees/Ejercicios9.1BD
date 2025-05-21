package es.prog2425.ejerciciosBD9_1.data.dao

import es.prog2425.ejerciciosBD9_1.data.db.DatabaseTienda
import es.prog2425.ejerciciosBD9_1.model.Usuario
import java.sql.*

class UsuarioDAOH2 : IUsuarioDAO {
    /**
     * Crea un nuevo usuario en la base de datos con los valores individuales proporcionados.
     *
     * @param nombre Nombre del usuario.
     * @param email Dirección de correo electrónico del usuario.
     *
     * Establece una conexión a la base de datos, prepara una consulta SQL con parámetros,
     * ejecuta la consulta y seguidamente maneja cualquier error que pueda surgir.
     */
    override fun insertarCampo(nombre: String, email: String) {
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
                val sql = "INSERT INTO Usuario (nombre, email) VALUES (?, ?)"
                stmt = connection.prepareStatement(sql)
                stmt.setString(1, nombre)
                stmt.setString(2, email)
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
     * Crea un nuevo usuario en la base de datos usando una clase `Usuario`.
     *
     * @param usuario Clase que contiene toda la información necesaria para insertar en la tabla.
     *
     * Funciona igual que el metodo anterior, pero más limpio porque se tiene una clase con los datos agrupados.
     */
    override fun insertarCampo(usuario: Usuario) {
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
                val sql = "INSERT INTO Usuario (nombre, email) VALUES (?, ?)"
                stmt = connection.prepareStatement(sql)
                stmt.setString(1, usuario.nombre)
                stmt.setString(2, usuario.correo)
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
}