package es.prog2425.ejerciciosBD9_1.data.dao

import es.prog2425.ejerciciosBD9_1.model.LineaPedido
import java.sql.Connection

interface ILineaPedidoDAO {
    fun insertarCampo(idPedido: Int, idProducto: Int, cantidad: Int, precio: Double)
    fun insertarCampo(lineaPedido: LineaPedido)
    fun insertarCampo(conn: Connection, idPedido: Int, idProducto: Int, cantidad: Int, precio: Double)
    fun insertarCampo(conn: Connection, lineaPedido: LineaPedido)
    fun getAll(): List<LineaPedido>
    fun getLineasByPedido(idPedido: Int): List<LineaPedido>
}