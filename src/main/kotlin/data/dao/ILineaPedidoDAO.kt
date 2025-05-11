package es.prog2425.ejerciciosBD9_1.data.dao

import es.prog2425.ejerciciosBD9_1.model.LineaPedido

interface ILineaPedidoDAO {
    fun insertarCampo(idPedido: Int, idProducto: Int, cantidad: Int, precio: Double)
    fun insertarCampo(lineaPedido: LineaPedido)
    fun getAll(): List<LineaPedido>
    fun getLineasByPedido(idPedido: Int): List<LineaPedido>
}