package es.prog2425.ejerciciosBD9_1.data.dao

import es.prog2425.ejerciciosBD9_1.model.LineaPedido

interface ILineaPedidoDAO {
    fun insertarCampo(idPedido: Int, idProducto: Int, cantidad: Int, precio: Double)
    fun getAll(): List<LineaPedido>
    fun getById(id: Int): LineaPedido
    fun getLineasByPedido(idPedido: Int): List<LineaPedido>
    fun updateLinea(precio: Double, id: Int)
    fun deleteLinea(id: Int)
}