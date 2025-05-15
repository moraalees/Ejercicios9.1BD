package es.prog2425.ejerciciosBD9_1.data.dao

import es.prog2425.ejerciciosBD9_1.model.Usuario
import javax.sql.DataSource

class UsuarioDAOH2(private val ds: DataSource) : IUsuarioDAO {
    override fun insertarCampo(nombre: String, email: String) {
        ds.connection.use { connection ->
            val sql = "INSERT INTO Usuario (nombre, email) VALUES (?, ?)"
            connection.prepareStatement(sql).use { stmt ->
                stmt.setString(1, nombre)
                stmt.setString(2, email)
                stmt.executeUpdate()
            }
        }
    }

    override fun insertarCampo(usuario: Usuario) {
        ds.connection.use { connection ->
            val sql = "INSERT INTO Usuario (nombre, email) VALUES (?, ?)"
            connection.prepareStatement(sql).use { stmt ->
                stmt.setString(1, usuario.nombre)
                stmt.setString(2, usuario.correo)
                stmt.executeUpdate()
            }
        }
    }

    override fun getAll(): List<Usuario> {
        val listaUsuarios = mutableListOf<Usuario>()
        ds.connection.use { conn ->
            val sql = "SELECT * FROM Usuario"
            conn.prepareStatement(sql).use { stmt ->
                stmt.executeQuery().use { rs ->
                    while (rs.next()) {
                        val id = rs.getInt("id")
                        val nombre = rs.getString("nombre")
                        val email = rs.getString("email")
                        listaUsuarios.add(Usuario(id, nombre, email))
                    }
                }
            }
        }
        return listaUsuarios
    }

    override fun getUsuariosByProductoComprado(nombreProducto: String): List<Usuario> {
        val usuarios = mutableListOf<Usuario>()
        val sql = "SELECT DISTINCT U.id, U.nombre, U.email FROM Usuario U JOIN Pedido P ON U.id = P.idUsuario JOIN LineaPedido LP ON P.id = LP.idPedido JOIN Producto PR ON LP.idProducto = PR.id WHERE PR.nombre = ?"

        ds.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, nombreProducto)
                stmt.executeQuery().use { rs ->
                    while (rs.next()) {
                        usuarios.add(
                            Usuario(rs.getInt("id"), rs.getString("nombre"), rs.getString("email"))
                        )
                    }
                }
            }
        }
        return usuarios
    }

    override fun deleteByName(nombre: String) {
        val sql = "DELETE FROM Usuario WHERE nombre = ?"
        ds.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, nombre)
                stmt.executeUpdate()
            }
        }
    }
}