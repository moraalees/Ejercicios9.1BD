package es.prog2425.ejerciciosBD9_1.data.dao

import es.prog2425.ejerciciosBD9_1.model.LineaPedido
import java.sql.Connection

/**
 * Interfaz que dicta todos los métodos del DAO de [LineaPedido]
 */
interface ILineaPedidoDAO {
    fun insertarCampo(idPedido: Int, idProducto: Int, cantidad: Int, precio: Double)
    fun getAll(): List<LineaPedido>
    fun getById(id: Int): LineaPedido?
    fun getLineasByPedido(idPedido: Int): List<LineaPedido>
    fun updateLinea(precio: Double, id: Int): Boolean
    fun deleteLinea(id: Int): Boolean
}