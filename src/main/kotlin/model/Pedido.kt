package es.prog2425.ejerciciosBD9_1.model

data class Pedido(
    private val id: Int,
    val precioTotal: Double,
    val idUsuario: Int
)