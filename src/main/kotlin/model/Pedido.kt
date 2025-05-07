package es.prog2425.ejerciciosBD9_1.model

data class Pedido(
    private val id: Int,
    private val precioTotal: Int,
    private val idUsuario: Int
)