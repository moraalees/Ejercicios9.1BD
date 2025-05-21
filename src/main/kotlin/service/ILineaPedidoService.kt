package es.prog2425.ejerciciosBD9_1.service

import es.prog2425.ejerciciosBD9_1.model.LineaPedido
import java.sql.Connection

interface ILineaPedidoService {
    fun addLineaPedido(idPedido: Int, idProducto: Int, cantidad: Int, precio: Double)
    fun addLineaPedido(lineaPedido: LineaPedido)
    fun addLineaPedido(conn: Connection, idPedido: Int, idProducto: Int, cantidad: Int, precio: Double)
    fun addLineaPedido(conn: Connection, lineaPedido: LineaPedido)
    fun obtenerLineasPedido(): List<LineaPedido>
    fun obtenerLineaPedidoByPedidoId(id: Int): List<LineaPedido>
}