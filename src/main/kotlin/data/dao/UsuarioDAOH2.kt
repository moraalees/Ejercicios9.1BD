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

    /**
     * Crea un nuevo usuario en la base de datos usando una clase `Usuario`.
     *
     * @param usuario Clase que contiene toda la información necesaria para insertar en la tabla.
     *
     * Funciona igual que el metodo anterior, pero más limpio porque se tiene una clase con los datos agrupados.
     */
    override fun insertarCampo(usuario: Usuario) {
        val connection = DatabaseTienda.getConnection()
        var stmt: Statement? = null

        try{
            val sql = "INSERT INTO Usuario (nombre, email) VALUES (?, ?)"
            stmt = connection.prepareStatement(sql)
            stmt.setString(1, usuario.nombre)
            stmt.setString(2, usuario.correo)
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

    override fun getAll(): List<Usuario> {
        val conn = DatabaseTienda.getConnection()
        val listaUsuarios = mutableListOf<Usuario>()
        var stmt: PreparedStatement? = null
        var rs: ResultSet? = null
        try {
            val sql = "SELECT * FROM Usuario"
            stmt = conn.prepareStatement(sql)
            rs = stmt.executeQuery()
            while (rs.next()) {
                val id = rs.getInt("id")
                val nombre = rs.getString("nombre")
                val email = rs.getString("email")
                listaUsuarios.add(Usuario(id, nombre, email))
            }
        } catch (e: SQLException) {
            throw SQLException("Error al obtener los usuarios: ${e.message}")
        } catch(e: Exception) {
            throw Exception("Error: ${e.message}")
        }finally {
            rs?.close()
            stmt?.close()
            DatabaseTienda.closeConnection(conn)
        }
        return listaUsuarios
    }

    override fun getUsuariosByProductoComprado(nombreProducto: String): List<Usuario> {
        val conn = DatabaseTienda.getConnection()
        val usuarios = mutableListOf<Usuario>()
        var stmt: PreparedStatement? = null
        var rs: ResultSet? = null
        try {
            val sql = "SELECT DISTINCT U.id, U.nombre, U.email FROM Usuario U JOIN Pedido P ON U.id = P.idUsuario JOIN LineaPedido LP ON P.id = LP.idPedido JOIN Producto PR ON LP.idProducto = PR.id WHERE PR.nombre = ?"
            stmt = conn.prepareStatement(sql)
            stmt.setString(1, nombreProducto)
            rs = stmt.executeQuery()
            while (rs.next()) {
                usuarios.add(Usuario(rs.getInt("id"), rs.getString("nombre"), rs.getString("email")))
            }
        } catch (e: SQLException) {
            throw SQLException("Error al obtener usuarios que compraron '$nombreProducto': ${e.message}")
        } catch (e: Exception) {
            throw Exception("Error: ${e.message}")
        } finally {
            rs?.close()
            stmt?.close()
            DatabaseTienda.closeConnection(conn)
        }
        return usuarios
    }

    override fun getById(id: Int) {
        TODO("Not yet implemented")
    }
}