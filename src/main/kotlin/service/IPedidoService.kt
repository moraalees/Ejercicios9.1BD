package es.prog2425.ejerciciosBD9_1.service

import es.prog2425.ejerciciosBD9_1.model.Pedido
import java.sql.Connection

interface IPedidoService {
    fun addPedido(idUsuario: Int, precio: Double)
    fun addPedido(pedido: Pedido)
    fun addPedido(conn: Connection, idUsuario: Int, precio: Double)
    fun addPedido(conn: Connection, pedido: Pedido)
    fun obtenerPedidos(): List<Pedido>
    fun obtenerPedidoPorUsuario(nombre: String): Double
    fun eliminarPedidoConLinea(id: Int)
    fun eliminarPedidoConLinea(conn: Connection, id: Int)
}