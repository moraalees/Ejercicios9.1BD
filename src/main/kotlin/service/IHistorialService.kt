package es.prog2425.ejerciciosBD9_1.service

import es.prog2425.ejerciciosBD9_1.model.Historial

interface IHistorialService {
    fun agregarCampo(historial: Historial)
    fun getAllCeldas(): List<Historial>
    fun deleteAll()
}