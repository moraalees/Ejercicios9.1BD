package es.prog2425.ejerciciosBD9_1.model

/**
 * Clase que simula ser un [Usuario].
 *
 * Posee varias inyecciones que simulan ser sus atributos.
 */
data class Usuario(
    val id: Int,
    val nombre: String,
    val correo: String
)