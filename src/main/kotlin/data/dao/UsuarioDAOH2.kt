package es.prog2425.ejerciciosBD9_1.data.dao

import es.prog2425.ejerciciosBD9_1.model.Usuario
import java.sql.SQLException
import javax.sql.DataSource

/**
 * Implementación de [IUsuarioDAO] que gestiona operaciones en la BD.
 *
 * @param ds Fuente de datos para obtener las conexiones a la base de datos.
 */
class UsuarioDAOH2(private val ds: DataSource) : IUsuarioDAO {

    /**
     * Inserta un nuevo [Usuario] en la base de datos.
     *
     * @param nombre Nombre del usuario.
     * @param email Dirección de correo electrónico del usuario.
     */
    override fun insertarCampo(nombre: String, email: String) {
        val sql = "INSERT INTO Usuario (nombre, email) VALUES (?, ?)"
        try {
            ds.connection.use { connection ->
                connection.prepareStatement(sql).use { stmt ->
                    stmt.setString(1, nombre)
                    stmt.setString(2, email)
                    stmt.executeUpdate()
                }
            }
        } catch (e: SQLException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }

    /**
     * Recupera todos los usuarios registrados en la base de datos.
     *
     * @return Lista de objetos [Usuario].
     */
    override fun getAll(): List<Usuario> {
        val listaUsuarios = mutableListOf<Usuario>()
        val sql = "SELECT * FROM Usuario"
        try {
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
        } catch (e: SQLException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
        return listaUsuarios
    }

    /**
     * Recupera un [Usuario] por su ID.
     *
     * @param id ID del usuario.
     * @return Objeto [Usuario] si se encuentra, o `null` si no existe.
     */
    override fun getById(id: Int): Usuario? {
        val sql = "SELECT * FROM Usuario WHERE id = ?"
        try {
            ds.connection.use { conn ->
                conn.prepareStatement(sql).use { stmt ->
                    stmt.setInt(1, id)
                    stmt.executeQuery().use { rs ->
                        return if (rs.next()) {
                            Usuario(rs.getInt("id"), rs.getString("nombre"), rs.getString("email"))
                        } else {
                            null
                        }
                    }
                }
            }
        } catch (e: SQLException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }

    /**
     * Actualiza el nombre de un usuario existente.
     *
     * @param nombre Nuevo nombre del usuario.
     * @param id ID del usuario a actualizar.
     * @return Boolean: true si se modificó al menos un registro o false si no se encontró el usuario.
     */
    override fun updateUsuario(nombre: String, id: Int): Boolean {
        val sql = "UPDATE Usuario SET nombre = ? WHERE id = ?"
        try {
            ds.connection.use { conn ->
                conn.prepareStatement(sql).use { stmt ->
                    stmt.setString(1, nombre)
                    stmt.setInt(2, id)
                    val modificacion = stmt.executeUpdate()
                    return modificacion > 0
                }
            }
        } catch (e: SQLException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }

    /**
     * Elimina un [Usuario] por su ID.
     *
     * @param id ID del usuario a eliminar.
     * @return Boolean: true si se eliminó al menos un registro o false si no se encontró el usuario.
     */
    override fun deleteById(id: Int): Boolean {
        val sql = "DELETE FROM Usuario WHERE id = ?"
        try {
            ds.connection.use { conn ->
                conn.prepareStatement(sql).use { stmt ->
                    stmt.setInt(1, id)
                    val modificacion = stmt.executeUpdate()
                    return modificacion > 0
                }
            }
        } catch (e: SQLException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }
}