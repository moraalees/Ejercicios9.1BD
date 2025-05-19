package es.prog2425.ejerciciosBD9_1.service

import es.prog2425.ejerciciosBD9_1.model.Pedido

/**
 * Interfaz que dicta todos los métodos del servicio de [Pedido]
 */
interface IPedidoService {
    fun addPedido(idUsuario: Int, precio: Double)
    fun obtenerPedidos(): List<Pedido>
    fun obtenerPorId(id: Int): Pedido?
    fun obtenerPedidosPorNombreUsuario(id: Int): List<Pedido>
    fun obtenerImportePedidosPorUsuario(id: Int): Double
    fun eliminarPedidoConLinea(id: Int): Boolean
    fun actualizarPedido(precioTotal: Double, id: Int): Boolean
}