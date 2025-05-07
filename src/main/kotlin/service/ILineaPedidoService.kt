package es.prog2425.ejerciciosBD9_1.service

interface ILineaPedidoService {
    fun addLineaPedido(idPedido: Int, idProducto: Int, cantidad: Int, precio: Double)
}