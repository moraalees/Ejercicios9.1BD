package es.prog2425.ejerciciosBD9_1.model

data class Producto(
    private val id: Int,
    val nombre: String,
    val precio: Double,
    val stock: Int
)