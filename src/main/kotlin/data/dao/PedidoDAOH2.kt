package es.prog2425.ejerciciosBD9_1.data.dao

import es.prog2425.ejerciciosBD9_1.model.Pedido
import javax.sql.DataSource

/**
 * Implementación de [IPedidoDAO] que maneja las operaciones de base de datos para la entidad [Pedido].
 *
 * @param ds Fuente de datos que proporciona conexiones a la base de datos.
 */
class PedidoDAOH2(private val ds: DataSource) : IPedidoDAO {
    /**
     * Inserta un nuevo [Pedido] en la base de datos.
     *
     * @param idUsuario ID del usuario que realiza el pedido.
     * @param precio Precio total del [Pedido].
     */
    override fun insertarCampo(idUsuario: Int, precio: Double) {
        val sql = "INSERT INTO Pedido (idusuario, preciototal) VALUES (?, ?)"
        ds.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setInt(1, idUsuario)
                stmt.setDouble(2, precio)
                stmt.executeUpdate()
            }
        }
    }

    /**
     * Recupera un [Pedido] a partir de su ID.
     *
     * @param id ID del [Pedido].
     * @return Objeto [Pedido] si se encuentra o null si no existe.
     */
    override fun getById(id: Int): Pedido? {
        val sql = "SELECT * FROM Pedido WHERE id = ?"
        ds.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setInt(1, id)
                stmt.executeQuery().use { rs ->
                    return if (rs.next()) {
                        Pedido(rs.getInt("id"), rs.getDouble("precioTotal"), rs.getInt("idUsuario"))
                    } else {
                        null
                    }
                }
            }
        }
    }

    /**
     * Recupera todos los pedidos registrados en la base de datos.
     *
     * @return Lista de objetos [Pedido].
     */
    override fun getAll(): List<Pedido> {
        val listaPedidos = mutableListOf<Pedido>()
        val sql = "SELECT * FROM Pedido"
        ds.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.executeQuery().use { rs ->
                    while (rs.next()) {
                        val id = rs.getInt("id")
                        val precio = rs.getDouble("precioTotal")
                        val idUsuario = rs.getInt("idusuario")
                        listaPedidos.add(Pedido(id, precio, idUsuario))
                    }
                }
            }
        }
        return listaPedidos
    }

    /**
     * Obtiene el importe total de todos los pedidos realizados por un usuario específico.
     *
     * @param id ID del usuario.
     * @return Suma total de los precios de los pedidos realizados por el usuario.
     */
    override fun getTotalImporteById(id: Int): Double {
        val sql = "SELECT SUM(P.precioTotal) AS total FROM Pedido P JOIN Usuario U ON P.idUsuario = U.id WHERE U.id = ?"
        ds.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setInt(1, id)
                stmt.executeQuery().use { rs ->
                    if (rs.next()) {
                        return rs.getDouble("total")
                    }
                }
            }
        }
        return 0.0
    }

    /**
     * Elimina un [Pedido] y todas sus líneas asociadas dentro de una transacción.
     *
     * @param id ID del pedido a eliminar.
     * @return Boolean: true si la eliminación fue exitosa o false si no se eliminó ningún registro.
     * @throws Exception Si ocurre un error durante la transacción.
     */
    override fun deletePedidoConLineas(id: Int): Boolean {
        ds.connection.use { conn ->
            conn.autoCommit = false
            try {
                val deleteLineasSql = "DELETE FROM LineaPedido WHERE idPedido = ?"
                conn.prepareStatement(deleteLineasSql).use { stmt ->
                    stmt.setInt(1, id)
                    stmt.executeUpdate()
                }

                val deletePedidoSql = "DELETE FROM Pedido WHERE id = ?"
                val filasAfectadas = conn.prepareStatement(deletePedidoSql).use { stmt ->
                    stmt.setInt(1, id)
                    stmt.executeUpdate()
                }

                conn.commit()
                return filasAfectadas > 0
            } catch (e: Exception) {
                conn.rollback()
                throw e
            }
        }
    }

    /**
     * Recupera todos los pedidos realizados por un usuario específico.
     *
     * @param id ID del usuario.
     * @return Lista de pedidos realizados por el usuario.
     */
    override fun getPedidosPorNombreUsuario(id: Int): List<Pedido> {
        val pedidos = mutableListOf<Pedido>()
        val sql = "SELECT P.id, P.precioTotal, P.idUsuario FROM Pedido P JOIN Usuario U ON P.idUsuario = U.id WHERE U.id = ?"
        ds.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setInt(1, id)
                stmt.executeQuery().use { rs ->
                    while (rs.next()) {
                        pedidos.add(Pedido(rs.getInt("id"), rs.getDouble("precioTotal"), rs.getInt("idUsuario")))
                    }
                }
            }
        }
        return pedidos
    }

    /**
     * Actualiza el precio total de un [Pedido] existente.
     *
     * @param precioTotal Nuevo precio total.
     * @param id ID del pedido a actualizar.
     * @return Boolean: true si la actualización fue exitosa o false si no se modificó ningún registro.
     */
    override fun updatePedido(precioTotal: Double, id: Int): Boolean {
        val sql = "UPDATE Pedido SET precioTotal = ? WHERE id = ?"
        ds.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setDouble(1, precioTotal)
                stmt.setInt(2, id)
                val modificacion = stmt.executeUpdate()
                return modificacion > 0
            }
        }
    }
}