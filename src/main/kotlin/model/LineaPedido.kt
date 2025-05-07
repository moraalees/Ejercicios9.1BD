package es.prog2425.ejerciciosBD9_1.model

class LineaPedido(
    private val id: Int,
    private val cantidad: Int,
    private val precio: Double,
    private val idPedido: Int,
    private val idProducto: Int
) {
}