package es.prog2425.ejerciciosBD9_1.service

import es.prog2425.ejerciciosBD9_1.model.Pedido

interface IPedidoService {
    fun addPedido(idUsuario: Int, precio: Double)
    fun addPedido(pedido: Pedido)
    fun obtenerPedidos(): List<Pedido>
    fun obtenerPedidoPorUsuario(nombre: String): Double
}