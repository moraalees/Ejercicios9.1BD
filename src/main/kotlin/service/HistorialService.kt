package es.prog2425.ejerciciosBD9_1.service

import es.prog2425.ejerciciosBD9_1.data.dao.HistorialDAOH2
import es.prog2425.ejerciciosBD9_1.model.Historial

class HistorialService(private val dao: HistorialDAOH2) : IHistorialService {
    override fun agregarCampo(historial: Historial) = dao.insertar(historial)

    override fun getAllCeldas(): List<Historial> = dao.obtenerTodos()

    override fun deleteAll() = dao.borrarTodo()
}