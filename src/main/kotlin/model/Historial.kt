package es.prog2425.ejerciciosBD9_1.model

import java.time.LocalDateTime

data class Historial (
    val id: Long = 0,
    val mensaje: String,
    val fechaHora: LocalDateTime = LocalDateTime.now()
)