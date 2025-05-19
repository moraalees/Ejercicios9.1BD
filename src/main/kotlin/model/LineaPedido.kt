package es.prog2425.ejerciciosBD9_1.model

data class LineaPedido(
    val id: Int,
    val cantidad: Int,
    val precio: Double,
    val idPedido: Int,
    val idProducto: Int
)