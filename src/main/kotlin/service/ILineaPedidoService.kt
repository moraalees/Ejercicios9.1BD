package es.prog2425.ejerciciosBD9_1.service

import es.prog2425.ejerciciosBD9_1.model.LineaPedido

interface ILineaPedidoService {
    fun addLineaPedido(idPedido: Int, idProducto: Int, cantidad: Int, precio: Double)
    fun obtenerLineasPedido(): List<LineaPedido>
    fun obtenerLineaById(id: Int): LineaPedido
    fun obtenerLineaPedidoByPedidoId(id: Int): List<LineaPedido>
    fun actualizarLinea(precio: Double, id: Int)
    fun eliminarLinea(id: Int)
}