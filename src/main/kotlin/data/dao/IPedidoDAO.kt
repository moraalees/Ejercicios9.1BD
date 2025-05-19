package es.prog2425.ejerciciosBD9_1.data.dao

import es.prog2425.ejerciciosBD9_1.model.Pedido

interface IPedidoDAO {
    fun insertarCampo(idUsuario: Int, precio: Double)
    fun getAll(): List<Pedido>
    fun getById(id: Int): Pedido?
    fun getPedidosPorNombreUsuario(nombre: String): List<Pedido>
    fun getTotalImporteByNombreUsuario(nombre: String): Double
    fun deletePedidoConLineas(id: Int): Boolean
    fun updatePedido(precioTotal: Double, id: Int): Boolean
}