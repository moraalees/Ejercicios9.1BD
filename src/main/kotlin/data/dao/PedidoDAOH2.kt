package es.prog2425.ejerciciosBD9_1.data.dao

import es.prog2425.ejerciciosBD9_1.data.db.DatabaseTienda
import es.prog2425.ejerciciosBD9_1.model.Pedido
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
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

    /**
     * Obtiene todos los pedidos registrados en la base de datos.
     *
     * @return Una lista de objetos [Pedido] con todos los pedidos encontrados.
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     * @throws Exception Para cualquier otro tipo de error inesperado.
     */
    override fun getAll(): List<Pedido> {
        val conn = DatabaseTienda.getConnection()
        val listaPedidos = mutableListOf<Pedido>()
        var stmt: PreparedStatement? = null
        var rs: ResultSet? = null
        try {
            val sql = "SELECT * FROM Pedido"
            stmt = conn.prepareStatement(sql)
            rs = stmt.executeQuery()
            while (rs.next()) {
                val id = rs.getInt("id")
                val precio = rs.getDouble("precioTotal")
                val idUsuario = rs.getInt("idusuario")
                listaPedidos.add(Pedido(id, precio, idUsuario))
            }
        } catch (e: SQLException) {
            throw SQLException("Error al obtener los pedidos: ${e.message}")
        } catch(e: Exception) {
            throw Exception("Error: ${e.message}")
        }finally {
            rs?.close()
            stmt?.close()
            DatabaseTienda.closeConnection(conn)
        }
        return listaPedidos
    }

    /**
     * Calcula el importe total de los pedidos realizados por un usuario con un nombre específico.
     *
     * @param nombre El nombre del usuario por el cual se desea calcular el total de sus pedidos.
     * @return El total acumulado (suma) de los precios de los pedidos realizados por ese usuario.
     *         Si el usuario no tiene pedidos o no existe, se retorna 0.0.
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     * @throws Exception Para cualquier otro tipo de error inesperado.
     */
    override fun getTotalImporteByNombreUsuario(nombre: String): Double {
        val conn = DatabaseTienda.getConnection()
        var stmt: PreparedStatement? = null
        var rs: ResultSet? = null
        try {
            val sql = "SELECT SUM(P.precioTotal) AS total FROM Pedido P JOIN Usuario U ON P.idUsuario = U.id WHERE U.nombre = ?"
            stmt = conn.prepareStatement(sql)
            stmt.setString(1, nombre)
            rs = stmt.executeQuery()
            if (rs.next()) {
                return rs.getDouble("total")
            }
        } catch (e: SQLException) {
            throw SQLException("Error al calcular el total del usuario: ${e.message}")
        } catch (e: Exception) {
            throw Exception("Error: ${e.message}")
        } finally {
            rs?.close()
            stmt?.close()
            DatabaseTienda.closeConnection(conn)
        }
        return 0.0
    }
}