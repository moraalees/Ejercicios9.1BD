package es.prog2425.ejerciciosBD9_1.model

import java.time.LocalDateTime

data class Historial(
    val mensaje: String,
    val fechaHora: LocalDateTime = LocalDateTime.now()
)