package es.prog2425.ejerciciosBD9_1.data.dao

import es.prog2425.ejerciciosBD9_1.model.LineaPedido
import javax.sql.DataSource

class LineaPedidoDAOH2(private val ds: DataSource) : ILineaPedidoDAO{

    override fun insertarCampo(idPedido: Int, idProducto: Int, cantidad: Int, precio: Double) {
        val sql = "INSERT INTO Lineapedido (idpedido, idproducto, cantidad, precio) VALUES (?, ?, ?, ?)"
        ds.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setInt(1, idPedido)
                stmt.setInt(2, idProducto)
                stmt.setInt(3, cantidad)
                stmt.setDouble(4, precio)
                stmt.executeUpdate()
            }
        }
    }

    override fun getAll(): List<LineaPedido> {
        val listaLineasPedido = mutableListOf<LineaPedido>()
        val sql = "SELECT * FROM Lineapedido"
        ds.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.executeQuery().use { rs ->
                    while (rs.next()) {
                        val id = rs.getInt("id")
                        val cantidad = rs.getInt("cantidad")
                        val precio = rs.getDouble("precio")
                        val idPedido = rs.getInt("idPedido")
                        val idProducto = rs.getInt("idProducto")
                        listaLineasPedido.add(LineaPedido(id, cantidad, precio, idPedido, idProducto))
                    }
                }
            }
        }
        return listaLineasPedido
    }

    override fun getById(id: Int): LineaPedido? {
        val sql = "SELECT * FROM LineaPedido WHERE id = ?"
        ds.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setInt(1, id)
                stmt.executeQuery().use { rs ->
                    return if (rs.next()){
                        LineaPedido(rs.getInt("id"), rs.getInt("cantidad"), rs.getDouble("precio"), rs.getInt("idPedido"), rs.getInt("idProducto"))
                    } else {
                        null
                    }
                }
            }
        }
    }

    override fun getLineasByPedido(idPedido: Int): List<LineaPedido> {
        val lineas = mutableListOf<LineaPedido>()
        val sql = "SELECT * FROM LineaPedido WHERE idPedido = ?"
        ds.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setInt(1, idPedido)
                stmt.executeQuery().use { rs ->
                    while (rs.next()) {
                        lineas.add(LineaPedido(rs.getInt("id"), rs.getInt("cantidad"), rs.getDouble("precio"), rs.getInt("idPedido"), rs.getInt("idProducto")))
                    }
                }
            }
        }
        return lineas
    }

    override fun updateLinea(precio: Double, id: Int): Boolean {
        val sql = "UPDATE LineaPedido SET precio = ? WHERE id = ?"
        ds.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setDouble(1, precio)
                stmt.setInt(2, id)
                val modificacion = stmt.executeUpdate()
                return modificacion > 0
            }
        }
    }

    override fun deleteLinea(id: Int): Boolean {
        val sql = "DELETE FROM LineaPedido WHERE id = ?"
        ds.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setInt(1, id)
                val modificacion = stmt.executeUpdate()
                return modificacion > 0
            }
        }
    }
}