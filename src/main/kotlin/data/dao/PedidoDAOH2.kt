package es.prog2425.ejerciciosBD9_1.data.dao

import es.prog2425.ejerciciosBD9_1.data.db.DatabaseTienda
import es.prog2425.ejerciciosBD9_1.model.Pedido
import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement

class PedidoDAOH2 : IPedidoDAO {
    /**
     * Crea un nuevo pedido en la base de datos con los valores individuales proporcionados.
     *
     * @param idUsuario ID del usuario que realiza el pedido.
     * @param precio Precio total del pedido.
     *
     * Establece una conexión a la base de datos, prepara una consulta SQL con parámetros,
     * ejecuta la consulta y seguidamente maneja cualquier error que pueda surgir.
     */
    override fun insertarCampo(idUsuario: Int, precio: Double) {
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
     * Crea un nuevo pedido en la base de datos usando una clase `Pedido`.
     *
     * @param pedido Clase que contiene toda la información necesaria para insertar en la tabla.
     *
     * Funcionalmente igual que el metodo anterior, pero más limpio porque se tiene una clase con los datos agrupados.
     */
    override fun insertarCampo(pedido: Pedido) {
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

            try{
                val sql = "INSERT INTO Pedido (idusuario, preciototal) VALUES (?, ?)"
                stmt = connection.prepareStatement(sql)
                stmt.setInt(1, pedido.idUsuario)
                stmt.setDouble(2, pedido.precioTotal)
                stmt.executeUpdate()
            } catch (e: SQLException) {
                throw SQLException("Error al insertar los campos en las tablas", e)
            } catch (e: Exception) {
                throw Exception("Error: ${e.message}")
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


    override fun insertarCampo(conn: Connection, idUsuario: Int, precio: Double) {
        var stmt: Statement? = null
        try{
            val sql = "INSERT INTO Pedido (idusuario, preciototal) VALUES (?, ?)"
            stmt = conn.prepareStatement(sql)
            stmt.setInt(1, idUsuario)
            stmt.setDouble(2, precio)
            stmt.executeUpdate()
        } catch (e: SQLException) {
            throw SQLException("Error al insertar los campos en las tablas", e)
        } catch (e: Exception) {
            throw Exception("Error: ${e.message}")
        } finally {
            stmt?.close()
        }
    }

    override fun insertarCampo(conn: Connection, pedido: Pedido) {
        var stmt: Statement? = null
        try{
            val sql = "INSERT INTO Pedido (idusuario, preciototal) VALUES (?, ?)"
            stmt = conn.prepareStatement(sql)
            stmt.setInt(1, pedido.idUsuario)
            stmt.setDouble(2, pedido.precioTotal)
            stmt.executeUpdate()
        } catch (e: SQLException) {
            throw SQLException("Error al insertar los campos en las tablas", e)
        } catch (e: Exception) {
            throw Exception("Error: ${e.message}")
        } finally {
            stmt?.close()
        }
    }
}