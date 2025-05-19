package es.prog2425.ejerciciosBD9_1.model

/**
 * Clase que simula ser un [Pedido].
 *
 * Posee varias inyecciones que simulan ser sus atributos.
 */
data class Pedido(
    val id: Int,
    val precioTotal: Double,
    val idUsuario: Int
)