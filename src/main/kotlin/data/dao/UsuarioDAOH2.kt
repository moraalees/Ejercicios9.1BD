package es.prog2425.ejerciciosBD9_1.data.dao

import es.prog2425.ejerciciosBD9_1.model.Usuario
import javax.sql.DataSource

class UsuarioDAOH2(private val ds: DataSource) : IUsuarioDAO {

    override fun insertarCampo(nombre: String, email: String) {
        val sql = "INSERT INTO Usuario (nombre, email) VALUES (?, ?)"
        ds.connection.use { connection ->
            connection.prepareStatement(sql).use { stmt ->
                stmt.setString(1, nombre)
                stmt.setString(2, email)
                stmt.executeUpdate()
            }
        }
    }

    override fun getAll(): List<Usuario> {
        val listaUsuarios = mutableListOf<Usuario>()
        val sql = "SELECT * FROM Usuario"
        ds.connection.use { conn ->
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

    override fun getById(id: Int): Usuario {
        val sql = "SELECT * FROM Usuario WHERE id = ?"
        ds.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setInt(1, id)
                stmt.executeQuery().use { rs ->
                    return Usuario(rs.getInt("id"), rs.getString("nombre"), rs.getString("email"))
                }
            }
        }
    }

    override fun updateUsuario(nombre: String, id: Int) {
        val sql = "UPDATE Usuario SET nombre = ? WHERE id = ?"
        ds.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, nombre)
                stmt.setInt(2, id)
                stmt.executeUpdate()
            }
        }
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