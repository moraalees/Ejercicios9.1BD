package es.prog2425.ejerciciosBD9_1.data.dao

import es.prog2425.ejerciciosBD9_1.model.LineaPedido
import java.sql.SQLException
import javax.sql.DataSource

/**
 * Implementa [ILineaPedidoDAO] que gestiona el acceso a la BD
 * para operaciones utilizando H2.
 *
 * @param ds Fuente de datos para obtener las conexiones a la base de datos.
 */
class LineaPedidoDAOH2(private val ds: DataSource) : ILineaPedidoDAO{

    /**
     * Inserta una nueva [LineaPedido] en la base de datos.
     *
     * @param idPedido ID del pedido al que pertenece la línea.
     * @param idProducto ID del producto incluido en la línea.
     * @param cantidad Cantidad del producto.
     * @param precio Precio unitario del producto.
     */
    override fun insertarCampo(idPedido: Int, idProducto: Int, cantidad: Int, precio: Double) {
        val sql = "INSERT INTO Lineapedido (idpedido, idproducto, cantidad, precio) VALUES (?, ?, ?, ?)"
        try {
            ds.connection.use { conn ->
                conn.prepareStatement(sql).use { stmt ->
                    stmt.setInt(1, idPedido)
                    stmt.setInt(2, idProducto)
                    stmt.setInt(3, cantidad)
                    stmt.setDouble(4, precio)
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
     * Recupera todas las líneas de pedido existentes en la base de datos.
     *
     * @return Lista de objetos [LineaPedido].
     */
    override fun getAll(): List<LineaPedido> {
        val listaLineasPedido = mutableListOf<LineaPedido>()
        val sql = "SELECT * FROM Lineapedido"
        try {
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
        } catch (e: SQLException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }

    /**
     * Recupera una [LineaPedido] por su ID.
     *
     * @param id ID de la línea de pedido.
     * @return Objeto [LineaPedido] si se encuentra o null si no existe.
     */
    override fun getById(id: Int): LineaPedido? {
        val sql = "SELECT * FROM LineaPedido WHERE id = ?"
        try {
            ds.connection.use { conn ->
                conn.prepareStatement(sql).use { stmt ->
                    stmt.setInt(1, id)
                    stmt.executeQuery().use { rs ->
                        return if (rs.next()) {
                            LineaPedido(
                                rs.getInt("id"),
                                rs.getInt("cantidad"),
                                rs.getDouble("precio"),
                                rs.getInt("idPedido"),
                                rs.getInt("idProducto")
                            )
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
     * Recupera todas las líneas de pedido asociadas a un pedido específico.
     *
     * @param idPedido ID del pedido.
     * @return Lista de [LineaPedido] correspondientes al pedido.
     */
    override fun getLineasByPedido(idPedido: Int): List<LineaPedido> {
        val lineas = mutableListOf<LineaPedido>()
        val sql = "SELECT * FROM LineaPedido WHERE idPedido = ?"
        try {
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
        } catch (e: SQLException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }

    /**
     * Actualiza el precio de una [LineaPedido] existente.
     *
     * @param precio Nuevo precio.
     * @param id ID de la línea de pedido a actualizar.
     * @return Boolean: true si la actualización fue exitosa o false si no se modificó ningún registro.
     */
    override fun updateLinea(precio: Double, id: Int): Boolean {
        val sql = "UPDATE LineaPedido SET precio = ? WHERE id = ?"
        try {
            ds.connection.use { conn ->
                conn.prepareStatement(sql).use { stmt ->
                    stmt.setDouble(1, precio)
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
     * Elimina una [LineaPedido] por su ID.
     *
     * @param id ID de la línea de pedido a eliminar.
     * @return Boolean: true si la eliminación fue exitosa o false si no se eliminó ningún registro.
     */
    override fun deleteLinea(id: Int): Boolean {
        val sql = "DELETE FROM LineaPedido WHERE id = ?"
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