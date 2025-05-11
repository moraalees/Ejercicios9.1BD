package es.prog2425.ejerciciosBD9_1.data.dao

import es.prog2425.ejerciciosBD9_1.model.Pedido

interface IPedidoDAO {
    fun insertarCampo(idUsuario: Int, precio: Double)
    fun insertarCampo(pedido: Pedido)
    fun getAll(): List<Pedido>
    fun getTotalImporteByNombreUsuario(nombre: String): Double
}