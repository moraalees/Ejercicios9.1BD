package es.prog2425.ejerciciosBD9_1.model

/**
 * Clase que simula ser una [LineaPedido].
 *
 * Posee varias inyecciones que simulan ser sus atributos.
 */
data class LineaPedido(
    val id: Int,
    val cantidad: Int,
    val precio: Double,
    val idPedido: Int,
    val idProducto: Int
)