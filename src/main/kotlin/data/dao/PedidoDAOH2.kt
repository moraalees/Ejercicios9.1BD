package es.prog2425.ejerciciosBD9_1.data.dao

import es.prog2425.ejerciciosBD9_1.model.Pedido
import javax.sql.DataSource

class PedidoDAOH2(private val ds: DataSource) : IPedidoDAO {

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

    override fun getTotalImporteByNombreUsuario(nombre: String): Double {
        val sql = "SELECT SUM(P.precioTotal) AS total FROM Pedido P JOIN Usuario U ON P.idUsuario = U.id WHERE U.nombre = ?"
        ds.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, nombre)
                stmt.executeQuery().use { rs ->
                    if (rs.next()) {
                        return rs.getDouble("total")
                    }
                }
            }
        }
        return 0.0
    }

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

    override fun getPedidosPorNombreUsuario(nombre: String): List<Pedido> {
        val pedidos = mutableListOf<Pedido>()
        val sql = "SELECT P.id, P.precioTotal, P.idUsuario FROM Pedido P JOIN Usuario U ON P.idUsuario = U.id WHERE U.nombre = ?"
        ds.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, nombre)
                stmt.executeQuery().use { rs ->
                    while (rs.next()) {
                        pedidos.add(Pedido(rs.getInt("id"), rs.getDouble("precioTotal"), rs.getInt("idUsuario")))
                    }
                }
            }
        }
        return pedidos
    }

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