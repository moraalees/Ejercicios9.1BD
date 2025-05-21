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

    /**
     * Obtiene todos los usuarios registrados en la base de datos.
     *
     * @return Una lista de objetos [Usuario] con todos los usuarios encontrados.
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     * @throws Exception Para cualquier otro tipo de error inesperado.
     */
    override fun getAll(): List<Usuario> {
        val conn = try {
            DatabaseTienda.getConnection()
        } catch (e: SQLException) {
            throw SQLException("Error al obtener la conexión: ${e.message}")
            null
        } catch (e: Exception) {
            throw Exception("Error inesperado: ${e.message}")
            null
        }
        val listaUsuarios = mutableListOf<Usuario>()
        println("Conexión exitosa.")
        if (conn != null){
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
                try{
                    DatabaseTienda.closeConnection(conn)
                    println("Se cerró la conexión exitosamente.")
                } catch (e: SQLException) {
                    throw SQLException("Error al cerrar la conexión: ${e.message}")
                } catch (e: Exception) {
                    throw Exception("Error inesperado: ${e.message}")
                }
            }
            return listaUsuarios
        }
        return listaUsuarios
    }

    /**
     * Obtiene los usuarios que hayan comprado un producto específico por nombre.
     *
     * @param nombreProducto El nombre del producto que se desea buscar.
     * @return Una lista de objetos [Usuario] que han comprado el producto especificado.
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     * @throws Exception Para cualquier otro tipo de error inesperado.
     */
    override fun getUsuariosByProductoComprado(nombreProducto: String): List<Usuario> {
        val conn = try {
            DatabaseTienda.getConnection()
        } catch (e: SQLException) {
            throw SQLException("Error al obtener la conexión: ${e.message}")
            null
        } catch (e: Exception) {
            throw Exception("Error inesperado: ${e.message}")
            null
        }
        val usuarios = mutableListOf<Usuario>()
        println("Conexión exitosa.")
        if (conn != null){
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
                try{
                    DatabaseTienda.closeConnection(conn)
                    println("Se cerró la conexión exitosamente.")
                } catch (e: SQLException) {
                    throw SQLException("Error al cerrar la conexión: ${e.message}")
                } catch (e: Exception) {
                    throw Exception("Error inesperado: ${e.message}")
                }
            }
            return usuarios
        }
        return usuarios
    }
}