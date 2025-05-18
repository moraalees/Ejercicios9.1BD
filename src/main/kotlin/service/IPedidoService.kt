package es.prog2425.ejerciciosBD9_1.service

import es.prog2425.ejerciciosBD9_1.model.Pedido

interface IPedidoService {
    fun addPedido(idUsuario: Int, precio: Double)
    fun obtenerPedidos(): List<Pedido>
    fun obtenerPorId(id: Int): Pedido
    fun obtenerPedidosPorNombreUsuario(nombre: String): List<Pedido>
    fun obtenerImportePedidosPorUsuario(nombre: String): Double
    fun eliminarPedidoConLinea(id: Int)
    fun actualizarPedido(precioTotal: Double, id: Int)
}