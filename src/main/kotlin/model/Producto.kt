package es.prog2425.ejerciciosBD9_1.model

/**
 * Clase que simula ser un [Producto].
 *
 * Posee varias inyecciones que simulan ser sus atributos.
 */
data class Producto(
    val id: Int,
    val nombre: String,
    val precio: Double,
    val stock: Int
)